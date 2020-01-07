package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusWechatPayNotify;
import com.swarm.base.entity.BusWechatUser;

public interface BusWechatPayNotifyDao extends JpaRepository<BusWechatPayNotify, Integer> {
	
	BusWechatPayNotify findByOrderCodeAndBusWechatUserAndBusUserId(String orderCode , BusWechatUser busWechatUser , Integer busUserId);
}
