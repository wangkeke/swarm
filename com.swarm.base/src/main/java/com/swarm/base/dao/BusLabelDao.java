package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusLabel;

public interface BusLabelDao extends JpaRepository<BusLabel, Integer> {
	
	List<BusLabel> findByBusUserId(Integer busUserId);
	
	int countByLabelAndBusUserId(String label , Integer busUserId);
	
	BusLabel findByIdAndBusUserId(Integer id , Integer busUserId);
	
}
