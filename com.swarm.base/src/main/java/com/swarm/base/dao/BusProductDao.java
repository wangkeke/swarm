package com.swarm.base.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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
	
	List<BusProduct> findByIdInAndBusUserIdAndShowAndFlagNot(Collection<Integer> id , Integer busUserId , boolean show , int notflag);
	
	List<BusProduct> findByIdInAndBusUserId(Collection<Integer> id , Integer busUserId);
	
	BusProduct findByIdAndBusUserId(Integer id , Integer busUserId);
	
	@Modifying
	@Query("update BusProduct p set p.favorite = p.favorite + 1 where p.id = ?1 and p.busUserId = ?2")
	int increaseFavoriteByIdAndBusUserId(Integer id , Integer busUserId);
	
	@Modifying
	@Query("update BusProduct p set p.sales = p.sales + ?2 where p.id = ?1 and p.busUserId = ?3")
	int addSalesByIdAndBusUserId(Integer id ,int sales, Integer busUserId);
	
	@Modifying
	@Query("update BusProduct p set p.sales = p.sales - ?2 where p.id = ?1 and p.busUserId = ?3")
	int minusSalesByIdAndBusUserId(Integer id ,int sales, Integer busUserId);
	
	
}
