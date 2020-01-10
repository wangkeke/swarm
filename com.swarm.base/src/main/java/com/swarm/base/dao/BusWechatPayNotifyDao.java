package com.swarm.base.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.swarm.base.entity.BusWechatPayNotify;
import com.swarm.base.entity.BusWechatUser;

public interface BusWechatPayNotifyDao extends JpaRepository<BusWechatPayNotify, Integer> {
	
	BusWechatPayNotify findByOrderCodeAndBusWechatUserAndBusUserId(String orderCode , BusWechatUser busWechatUser , Integer busUserId);
	
	@Modifying
	@Query("update BusWechatPayNotify n set n.status = ?1 , n.updateDate = ?2 where n.id = ?3 and n.status = ?4 and n.busUserId = ?5")
	int updateStatusByIdAndStatusAndBusUserId(int newStatus ,Date updateDate , Integer id ,int oldStatus , Integer busUserId);
}
