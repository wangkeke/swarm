package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusOrder;
import com.swarm.base.entity.BusRecord;

public interface BusRecordDao extends JpaRepository<BusRecord, Integer> {
	
	List<BusRecord> findByBusOrderAndBusUserId(BusOrder busOrder , Integer busUserId);
	
}
