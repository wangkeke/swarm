package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.swarm.base.entity.BusWeUserAddress;
import com.swarm.base.entity.BusWechatUser;

public interface BusWeUserAddressDao extends JpaRepository<BusWeUserAddress, Integer> {
	
	List<BusWeUserAddress> findByBusWechatUserAndBusUserIdAndFlagNot(BusWechatUser busWechatUser , Integer busUserId , int notflag);
	
	BusWeUserAddress findByIdAndBusUserId(Integer id , Integer busUserId);
	
	@Modifying
	@Query("update BusWeUserAddress a set a.first = false where a.busWechatUser = ?2 and a.busUserId = ?1")
	void clearBusWeUserAddressFirst(Integer busUserId , BusWechatUser busWechatUser);
	
}
