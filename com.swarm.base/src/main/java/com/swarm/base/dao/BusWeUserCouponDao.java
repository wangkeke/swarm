package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusCoupon;
import com.swarm.base.entity.BusWeUserCoupon;

public interface BusWeUserCouponDao extends JpaRepository<BusWeUserCoupon, Integer> {
	
	int countByBusCouponAndBusUserId(BusCoupon busCoupon , Integer busUserId);
	
}
