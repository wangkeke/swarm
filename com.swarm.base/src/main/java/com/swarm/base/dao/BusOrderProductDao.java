package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusOrder;
import com.swarm.base.entity.BusOrderProduct;

public interface BusOrderProductDao extends JpaRepository<BusOrderProduct, Integer> {
	
	List<BusOrderProduct> findByBusOrder(BusOrder busOrder);
	
}
