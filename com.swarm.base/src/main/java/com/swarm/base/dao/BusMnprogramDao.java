package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusMnprogram;

public interface BusMnprogramDao extends JpaRepository<BusMnprogram, Integer> {
	
	public BusMnprogram findFirstByBusUserId(Integer busUserId);
	
}
