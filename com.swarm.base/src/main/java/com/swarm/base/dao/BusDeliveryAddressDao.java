package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusDeliveryAddress;

public interface BusDeliveryAddressDao extends JpaRepository<BusDeliveryAddress, Integer> {

}
