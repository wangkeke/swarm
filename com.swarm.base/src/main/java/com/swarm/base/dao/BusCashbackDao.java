package com.swarm.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusCashback;

public interface BusCashbackDao extends JpaRepository<BusCashback, Integer> {
	
	Page<BusCashback> findByBusUserId(Integer busUserId , Pageable pageable);
	
}
