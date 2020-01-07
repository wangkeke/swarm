package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusCoupon;
import com.swarm.base.entity.BusWeUserCoupon;
import com.swarm.base.entity.BusWechatUser;

public interface BusWeUserCouponDao extends JpaRepository<BusWeUserCoupon, Integer> {
	
	int countByBusCouponAndBusUserId(BusCoupon busCoupon , Integer busUserId);
	
	int countByBusWechatUserAndUsedAndBusUserId(BusWechatUser busWechatUser ,boolean used, Integer busUserId);
	
	List<BusWeUserCoupon> findByBusWechatUserAndUsedAndBusUserId(BusWechatUser busWechatUser , boolean used , Integer busUserId);
	
	int countByBusWechatUserAndBusCouponAndBusUserId(BusWechatUser busWechatUser , BusCoupon busCoupon , Integer busUserId);
	
}
