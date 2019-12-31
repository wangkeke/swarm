package com.swarm.web;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

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
	public ScheduledExecutorService scheduledExecutorService() {
		return Executors.newScheduledThreadPool(0);
	}
	
}
