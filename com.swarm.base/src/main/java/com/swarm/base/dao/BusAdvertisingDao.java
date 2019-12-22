package com.swarm.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusAdvertising;

public interface BusAdvertisingDao extends JpaRepository<BusAdvertising, Integer>{
	
	Page<BusAdvertising> findByEnableAndBusUserId(boolean enable , Integer busUserId , Pageable pageable);
	
}
