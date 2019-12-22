package com.swarm.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusCoupon;

public interface BusCouponDao extends JpaRepository<BusCoupon, Integer> {
	
	Page<BusCoupon> findByBusUserId(Integer busUserId , Pageable pageable);
	
	BusCoupon findByIdAndBusUserId(Integer id , Integer busUserId);
	
}
