package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusMnprogram;
import com.swarm.base.entity.BusWeApiInfo;

public interface BusWeApiInfoDao extends JpaRepository<BusWeApiInfo, Integer> {
	
	BusWeApiInfo findByBusMnprogramAndBusUserId(BusMnprogram busMnprogram , Integer busUserId);
	
}
