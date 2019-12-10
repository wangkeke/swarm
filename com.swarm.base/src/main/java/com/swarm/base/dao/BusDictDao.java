package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusDict;

public interface BusDictDao extends JpaRepository<BusDict, Integer> {

}
