package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusRecord;

public interface BusRecordDao extends JpaRepository<BusRecord, Integer> {

}
