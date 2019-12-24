package com.swarm.base.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusImage;
import com.swarm.base.entity.BusProduct;

public interface BusImageDao extends JpaRepository<BusImage, Integer> {
	
	BusImage findByIdAndBusUserId(Integer id , Integer busUserId);
	
	List<BusImage> findByIdInAndBusUserId(Collection<Integer> id , Integer busUserId);
	
	List<BusImage> findByProductAndBusUserId(BusProduct product ,  Integer busUserId);
	
}
