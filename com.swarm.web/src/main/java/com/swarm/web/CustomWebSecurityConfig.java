package com.swarm.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.swarm.base.dao.BusUserDao;

@Configuration
public class CustomWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
			.antMatchers("/favicon.ico","/login/captcha","/actuator","/actuator/**","/static/**").permitAll()
			.anyRequest().fullyAuthenticated()
//			.hasAnyAuthority(Identity.ADMIN_ID.getName(),
//			Identity.SYSTEM_ID.getName(),Identity.USER_ID.getName())
		.and()
		.exceptionHandling()
			.accessDeniedPage("/login/unauthorized")
		.and()
		.formLogin()
			.loginProcessingUrl("/login")
			.loginPage("/login/unauthorized")
			.failureUrl("/login/failure")
			.defaultSuccessUrl("/login/success")
			.permitAll()
		.and()
		.logout()
			.logoutUrl("/login/logout")
			.logoutSuccessUrl("/login/logout")
			.invalidateHttpSession(true);
	}
	
	@Bean
	public UserDetailsService customUserDetailsService(BusUserDao busUserDao) {
		return new CustomUserDetailsService(busUserDao);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
