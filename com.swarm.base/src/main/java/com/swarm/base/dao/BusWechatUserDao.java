package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusWechatUser;

public interface BusWechatUserDao extends JpaRepository<BusWechatUser, Integer> {

}
