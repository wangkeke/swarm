package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusCoupon;

public interface BusCouponDao extends JpaRepository<BusCoupon, Integer> {

}
