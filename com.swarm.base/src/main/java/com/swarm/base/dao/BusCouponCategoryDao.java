package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.swarm.base.entity.BusCoupon;
import com.swarm.base.entity.BusCouponCategory;

public interface BusCouponCategoryDao extends JpaRepository<BusCouponCategory, Integer> {
	
	List<BusCouponCategory> findByBusCouponAndBusUserId(BusCoupon busCoupon , Integer busUserId);
	
	@Modifying
	@Query("delete from BusCouponCategory t where t.busCoupon = ?1 and t.busUserId = ?2")
	void deleteByBusCouponAndBusUserId(BusCoupon busCoupon , Integer busUserId);
	
}
