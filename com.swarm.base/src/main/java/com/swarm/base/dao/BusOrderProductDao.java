package com.swarm.base.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusOrder;
import com.swarm.base.entity.BusOrderProduct;

public interface BusOrderProductDao extends JpaRepository<BusOrderProduct, Integer> {

	List<BusOrderProduct> findByBusOrderAndBusUserId(BusOrder busOrder , Integer busUserId);
	
	List<BusOrderProduct> findByBusOrderInAndBusUserId(Collection<BusOrder> busOrders , Integer busUserId);
	
}
