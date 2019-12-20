package com.swarm.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusUser;

public interface BusUserDao extends JpaRepository<com.swarm.base.entity.BusUser, Integer>{
	
	BusUser findFirstByUsername(String username);
	
	Page<BusUser> findByUsernameLikeOrDescLike(String username , String desc , Pageable pageable);
	
	int countByUsername(String username);
	
	
}
