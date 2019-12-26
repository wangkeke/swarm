package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusOrder;
import com.swarm.base.entity.BusPickcode;

public interface BusPickcodeDao extends JpaRepository<BusPickcode, Integer> {
	
	BusPickcode findByBusOrder(BusOrder busOrder);
	
}
