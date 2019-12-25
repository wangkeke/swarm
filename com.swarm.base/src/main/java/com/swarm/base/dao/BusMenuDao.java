package com.swarm.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusMenu;

public interface BusMenuDao extends JpaRepository<BusMenu, Integer> {
	
	Page<BusMenu> findByBusUserId(Integer busUserId , Pageable pageable);
	
}
