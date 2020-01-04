package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusProduct;
import com.swarm.base.entity.BusWeUserFavorite;
import com.swarm.base.entity.BusWechatUser;

public interface BusWeUserFavoriteDao extends JpaRepository<BusWeUserFavorite, Integer> {
	
	List<BusWeUserFavorite> findByBusProductAndBusWechatUserAndBusUserId(BusProduct busProduct , BusWechatUser busWechatUser , Integer busUserId);
	
}
