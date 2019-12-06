package com.swarm.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.post.LocationRewriteFilter;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.swarm.zuul.dao.UploadConfigDao;
import com.swarm.zuul.entity.UploadConfig;

@EnableJpaRepositories(basePackageClasses = UploadConfigDao.class)
@EntityScan(basePackageClasses = UploadConfig.class)
@EnableFeignClients
@EnableZuulProxy
@EnableHystrix
@SpringBootApplication
public class ZuulApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}
	
	@Bean
	public FallbackProvider defaultFallbackProvider() {
		return new DefaultHystrixFallback();
	}
	
	@Bean
	public LocationRewriteFilter locationRewriteFilter() {
		return new LocationRewriteFilter();
	}
	
}
