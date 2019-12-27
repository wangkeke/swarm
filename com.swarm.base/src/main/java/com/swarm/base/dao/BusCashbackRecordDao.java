package com.swarm.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusCashback;
import com.swarm.base.entity.BusCashbackRecord;

public interface BusCashbackRecordDao extends JpaRepository<BusCashbackRecord, Integer> {
	
	Page<BusCashbackRecord> findByBusCashbackAndBusUserId(BusCashback busCashback , Integer busUserId , Pageable pageable);
	
}
