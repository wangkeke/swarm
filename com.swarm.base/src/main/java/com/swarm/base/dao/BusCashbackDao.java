package com.swarm.base.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.swarm.base.entity.BusCashback;
import com.swarm.base.entity.BusWechatUser;
import com.swarm.base.service.ActivityNode;

public interface BusCashbackDao extends JpaRepository<BusCashback, Integer> {
	
	Page<BusCashback> findByBusUserId(Integer busUserId , Pageable pageable);
	
	BusCashback findFirstByBusWechatUserAndBusUserIdOrderByIdDesc(BusWechatUser busWechatUser , Integer busUserId);
	
	BusCashback findFirstByBusWechatUserAndBusUserIdAndActivityNodeOrderByIdDesc(BusWechatUser busWechatUser , Integer busUserId , ActivityNode activityNode);
	
	@Query("select c from BusCashback c where c.busUserId = ?1 and c.activityNode = ?2 and c.reqCashback>c.hasCashback Order by c.id ASC")
	List<BusCashback> queryFirstByBusUserIdAndActivityNode(Integer busUserId , ActivityNode activityNode);
	
	@Query("select c from BusCashback c inner join c.busWechatUser u where c.busUserId = ?1 and c.activityNode = ?2 and c.reqCashback>c.hasCashback and c.id < ?3 and u.parent IS NULL order by c.id ASC")
	List<BusCashback> queryByBusUserIdAndActivityNodeAndIdLessEqualAndParentNot(Integer busUserId , ActivityNode activityNode , Integer leId);
	
	@Modifying
	@Query("update BusCashback c set c.reqCashback = c.reqCashback + ?3 , c.activityNode = ?6 , c.updateDate = ?5 where c.id = ?1 and c.busUserId = ?2 and c.reqCashback = ?4")
	int updateReqcashbackByIdAndBusUserId(Integer id , Integer busUserId , BigDecimal amount , BigDecimal reqCashback , Date updateDate , ActivityNode activityNode);
	
	@Modifying
	@Query("update BusCashback c set c.hasCashback = c.hasCashback + ?3 , c.activityNode = ?4 , c.updateDate = ?5 where c.id = ?1 and c.busUserId = ?2")
	int updateHascashbackByIdAndBusUserId(Integer id , Integer busUserId , BigDecimal amount , ActivityNode activityNode , Date updateDate);
	
}
