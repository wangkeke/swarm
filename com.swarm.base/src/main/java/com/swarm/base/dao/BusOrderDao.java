package com.swarm.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusOrder;

public interface BusOrderDao extends JpaRepository<BusOrder, Integer> {
	
	Page<BusOrder> findByBusUserId(Integer busUserId , Pageable pageable);
	
}
