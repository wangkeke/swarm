package com.swarm.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusWeWithdrawal;

public interface BusWeWithdrawalDao extends JpaRepository<BusWeWithdrawal, Integer> {
	
	Page<BusWeWithdrawal> findByBusUserId(Integer busUserId , Pageable pageable);
	
}
