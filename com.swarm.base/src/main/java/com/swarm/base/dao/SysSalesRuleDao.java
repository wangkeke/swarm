package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.SalesRuleType;
import com.swarm.base.entity.SysSalesRule;

public interface SysSalesRuleDao extends JpaRepository<SysSalesRule, Integer> {
	
	Page<SysSalesRule> findByNameLike(String name , Pageable pageable);
	
	int countByType(SalesRuleType type);
	
	List<SysSalesRule> findByEnable(boolean enable);
	
}
