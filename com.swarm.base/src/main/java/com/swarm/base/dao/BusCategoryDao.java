package com.swarm.base.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusCategory;

public interface BusCategoryDao extends JpaRepository<BusCategory, Integer> {
	
	List<BusCategory> findByBusUserId(Integer busUserId);
	
	BusCategory findFirstByIdAndBusUserId(Integer id , Integer busUserId);
	
	int countByParentAndBusUserId(BusCategory parent , Integer busUserId);
	
	List<BusCategory> findByParentInAndBusUserId(List<BusCategory> parents , Integer busUserId);
	
	List<BusCategory> findByIdInAndBusUserId(Collection<Integer> id , Integer busUserId);
	
}
