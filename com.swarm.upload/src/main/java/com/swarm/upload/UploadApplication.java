package com.swarm.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.swarm.upload.dao.UploadConfigDao;
import com.swarm.upload.entity.UploadConfig; 

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UploadConfigDao.class)
@EntityScan(basePackageClasses = UploadConfig.class)
public class UploadApplication {

	public static void main(String[] args) { 
		ConfigurableApplicationContext context = SpringApplication.run(UploadApplication.class, args);
//		System.out.println("||---------------------------------------------------------||");
//		System.out.println("||                                                         ||");
//		System.out.println("||---------------------------------------------------------||");
//		for (String name : context.getBeanDefinitionNames()) {
//			System.out.println(name);
//		}
	}

}
