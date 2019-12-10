package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusCategory;

public interface BusCategoryDao extends JpaRepository<BusCategory, Integer> {

}
