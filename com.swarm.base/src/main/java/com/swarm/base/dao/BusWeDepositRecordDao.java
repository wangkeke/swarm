package com.swarm.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.swarm.base.entity.BusWeDepositRecord;

public interface BusWeDepositRecordDao extends JpaRepository<BusWeDepositRecord, Integer> {
	
	@Query(value = "select r from BusWeDepositRecord r inner join r.busWechatUser u where u.nickname like ?1 and u.busUserId = ?2")
	Page<BusWeDepositRecord> queryByNicenameLike(String nicename ,Integer busUserId , Pageable pageable);
	
	Page<BusWeDepositRecord> findByBusUserId(Integer busUserId , Pageable pageable);
	
}
