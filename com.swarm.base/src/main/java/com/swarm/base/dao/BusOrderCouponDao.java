package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusOrder;
import com.swarm.base.entity.BusOrderCoupon;


public interface BusOrderCouponDao extends JpaRepository<BusOrderCoupon, Integer> {
	
	List<BusOrderCoupon> findByBusOrder(BusOrder busOrder);
	
}
