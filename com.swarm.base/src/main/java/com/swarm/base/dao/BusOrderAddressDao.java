package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusOrder;
import com.swarm.base.entity.BusOrderAddress;

public interface BusOrderAddressDao extends JpaRepository<BusOrderAddress, Integer> {
	
	BusOrderAddress findByBusOrderAndBusUserId(BusOrder busOrder , Integer busUserId);
	
}
