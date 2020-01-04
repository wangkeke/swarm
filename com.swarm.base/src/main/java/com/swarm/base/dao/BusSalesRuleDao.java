package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusSalesRule;

public interface BusSalesRuleDao extends JpaRepository<BusSalesRule, Integer> {
	
	Page<BusSalesRule> findByBusUserId(Integer busUserId , Pageable pageable);
	
	List<BusSalesRule> findByBusUserIdAndEnable(Integer busUserId , boolean enable);
	
	BusSalesRule findByBusUserIdAndEnableAndId(Integer busUserId , boolean enable , Integer id);
	
}
