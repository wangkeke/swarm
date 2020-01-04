package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusImage;
import com.swarm.base.entity.BusImageType;

public interface BusImageDao extends JpaRepository<BusImage, Integer> {
	
	BusImage findByIdAndBusUserId(Integer id , Integer busUserId);
	
	Page<BusImage> findByBusUserId(Integer busUserId , Pageable pageable);
	
	List<BusImage> findByBusUserIdAndBusImageTypeOrderBySortDesc(Integer busUserId , BusImageType busImageType);
}
