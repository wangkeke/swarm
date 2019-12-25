package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusOrderProduct;

public interface BusOrderGoodsDao extends JpaRepository<BusOrderProduct, Integer> {

}
