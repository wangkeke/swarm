package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusUser;

public interface BusUserDao extends JpaRepository<com.swarm.base.entity.BusUser, Integer>{
	
	BusUser findFirstByUsername(String username);
	
}