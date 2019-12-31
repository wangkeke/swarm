package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusMnprogramStat;

public interface BusMnprogramStatDao extends JpaRepository<BusMnprogramStat, Integer> {
	
	BusMnprogramStat findByStatDateAndBusUserId(String statDate , Integer busUserId);
	
}
