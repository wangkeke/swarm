package com.swarm.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.swarm.base.entity.BusDeliveryAddress;

public interface BusDeliveryAddressDao extends JpaRepository<BusDeliveryAddress, Integer> {
	
	Page<BusDeliveryAddress> findByBusUserIdAndFlagNot(Integer busUserId , int notflag , Pageable pageable);
	
	@Modifying
	@Query("update BusDeliveryAddress a set a.flag=-1 , updateDate=new Date() where a.id = ?1 and a.busUserId = ?2")
	void deleteById(Integer id , Integer busUserId);
	
}
