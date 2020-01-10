package com.swarm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AppApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AppApplication.class, args);
//		System.out.println("||---------------------------------------------------------||");
//		System.out.println("||                                                         ||");
//		System.out.println("||---------------------------------------------------------||");
//		for (String name : context.getBeanDefinitionNames()) {
//			System.out.println(name);
//		}
	}
	
}
