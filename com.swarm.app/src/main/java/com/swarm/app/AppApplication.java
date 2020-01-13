package com.swarm.app;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
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

	
	@Bean
	public ScheduledExecutorService scheduledExecutorService() {
		return Executors.newScheduledThreadPool(0);
	}
	
}
