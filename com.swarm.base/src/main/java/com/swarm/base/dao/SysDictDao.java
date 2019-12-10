package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.SysDict;

public interface SysDictDao extends JpaRepository<SysDict, Integer> {

}
