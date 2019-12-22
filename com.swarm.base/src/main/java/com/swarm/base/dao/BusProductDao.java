package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusCategory;
import com.swarm.base.entity.BusLabel;
import com.swarm.base.entity.BusProduct;

public interface BusProductDao extends JpaRepository<BusProduct, Integer> {
	
	int countByCategoryAndBusUserIdAndFlagNot(BusCategory category , Integer busUserId , int notflag);
	
	int countByLabelAndBusUserIdAndFlagNot(BusLabel label , Integer busUserId , int notflag);
	
}
