package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusDict;
import com.swarm.base.entity.DictType;

public interface BusDictDao extends JpaRepository<BusDict, Integer> {
	
	List<BusDict> findByTypeAndBusUserId(DictType type , Integer busUserId);
	
}
