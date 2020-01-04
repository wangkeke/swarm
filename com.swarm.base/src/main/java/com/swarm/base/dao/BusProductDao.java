package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusCategory;
import com.swarm.base.entity.BusLabel;
import com.swarm.base.entity.BusProduct;

public interface BusProductDao extends JpaRepository<BusProduct, Integer> {
	
	int countByCategoryAndBusUserIdAndFlagNot(BusCategory category , Integer busUserId , int notflag);
	
	int countByLabelAndBusUserIdAndFlagNot(BusLabel label , Integer busUserId , int notflag);
	
	Page<BusProduct> findByBusUserIdAndFlagNot(Integer busUserId , int notflag , Pageable pageable);
	
	Page<BusProduct> findByCategoryInAndTitleLikeAndBusUserIdAndFlagNot(List<BusCategory> categories , String title , Integer busUserId , int notflag , Pageable pageable);
	
	Page<BusProduct> findByCategoryInAndBusUserIdAndFlagNot(List<BusCategory> categories , Integer busUserId , int notflag , Pageable pageable);
	
	Page<BusProduct> findByTitleLikeAndBusUserIdAndFlagNot(String title , Integer busUserId , int notflag , Pageable pageable);
	
	Page<BusProduct> findByTitleLikeAndShowAndBusUserIdAndFlagNot(String title , boolean show , Integer busUserId , int notflag , Pageable pageable);
	
	Page<BusProduct> findByShowAndBusUserIdAndFlagNot(boolean show , Integer busUserId , int notflag , Pageable pageable);
	
	Page<BusProduct> findByCategoryAndShowAndBusUserIdAndFlagNot(BusCategory category , boolean show , Integer busUserId , int notflag , Pageable pageable);
	
	BusProduct findByBusUserIdAndIdAndShowAndFlagNot(Integer busUserId , Integer id , boolean show , int notflag);
	
}
