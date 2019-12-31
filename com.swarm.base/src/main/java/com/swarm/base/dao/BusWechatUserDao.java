package com.swarm.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusWechatUser;

public interface BusWechatUserDao extends JpaRepository<BusWechatUser, Integer> {
	
	Page<BusWechatUser> findByNicknameLikeAndBusUserId(String nicename , Integer busUserId , Pageable pageable);
	
	Page<BusWechatUser> findByBusUserId(Integer busUserId , Pageable pageable);
	
	BusWechatUser findByOpenIdAndBusUserId(String openId , Integer busUserId);
	
	BusWechatUser findByIdAndBusUserId(Integer id , Integer busUserId);
	
}
