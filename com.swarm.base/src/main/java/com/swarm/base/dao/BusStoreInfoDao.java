package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusStoreInfo;

public interface BusStoreInfoDao extends JpaRepository<BusStoreInfo, Integer> {
	
	BusStoreInfo findFirstByBusUserId(Integer busUserId);
	
}
