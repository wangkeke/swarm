package com.swarm.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class CustomWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/login/captcha").permitAll()
				.anyRequest().authenticated()
//				.hasAnyAuthority(Identity.ADMIN_ID.getName(),
//				Identity.SYSTEM_ID.getName(),Identity.USER_ID.getName())
			.and()
			.exceptionHandling()
				.accessDeniedPage("/login/unauthorized")
			.and()
			.formLogin()
				.loginProcessingUrl("/login")
				.loginPage("/login/unauthorized")
				.failureUrl("/login/failure")
				.permitAll()
			.and()
			.logout()
				.logoutUrl("/login/logout")
				.logoutSuccessUrl("/login/logout")
				.invalidateHttpSession(true)
			.and()
			.httpBasic();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
