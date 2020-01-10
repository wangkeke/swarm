package com.swarm.base.dao;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.swarm.base.entity.BusOrder;
import com.swarm.base.entity.BusWechatUser;
import com.swarm.base.service.ActivityNode;

public interface BusOrderDao extends JpaRepository<BusOrder, Integer> {
	
	Page<BusOrder> findByBusUserId(Integer busUserId , Pageable pageable);
	
	Page<BusOrder> findByBusWechatUserAndBusUserId(BusWechatUser busWechatUser , Integer busUserId , Pageable pageable);
	
	Page<BusOrder> findByBusWechatUserAndActivityNodeInAndBusUserId(BusWechatUser busWechatUser , Collection<ActivityNode> nodes , Integer busUserId , Pageable pageable);
	
	BusOrder findFirstByOrderCodeAndBusWechatUserAndBusUserId(String orderCode ,BusWechatUser busWechatUser, Integer busUserId);
	
	BusOrder findByIdAndBusWechatUserAndBusUserId(Integer id , BusWechatUser busWechatUser , Integer busUserId);
	
	@Modifying
	@Query("update BusOrder set activityNode = ?1 , updateDate = ?2 where id = ?3 and busUserId = ?4 and activityNode = ?5")
	int updateActivityByIdAndBusUserId(ActivityNode newNode , Date updateDate , Integer id , Integer busUserId , ActivityNode oldNode);
	
}
