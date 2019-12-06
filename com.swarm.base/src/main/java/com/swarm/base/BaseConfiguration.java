package com.swarm.base;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.swarm.base.dao.UserDao;
import com.swarm.base.entity.BaseEntity;

@Configuration
@ConditionalOnClass({com.swarm.base.entity.BaseEntity.class})
@ComponentScan(basePackageClasses = BaseConfiguration.class)
@EnableJpaRepositories(basePackageClasses = UserDao.class)
@EntityScan(basePackageClasses = BaseEntity.class)
public class BaseConfiguration {

}
