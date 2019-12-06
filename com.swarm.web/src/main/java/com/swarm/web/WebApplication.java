package com.swarm.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.wf.captcha.servlet.CaptchaServlet;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WebApplication.class, args);
//		System.out.println("||---------------------------------------------------------||");
//		System.out.println("||                                                         ||");
//		System.out.println("||---------------------------------------------------------||");
//		for (String name : context.getBeanDefinitionNames()) {
//			System.out.println(name);
//		}
	}
	
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("exception","message");
		return messageSource;
	}  
	
	@Bean
	public ServletRegistrationBean<CaptchaServlet> captchaServlet(){
		ServletRegistrationBean<CaptchaServlet> servletRegistrationBean = new ServletRegistrationBean<CaptchaServlet>(new CaptchaServlet(), "/captcha");
		return servletRegistrationBean;
	}
	
}
