package com.swarm.base.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.swarm.base.entity.BusProduct;
import com.swarm.base.entity.BusWeUserFavorite;
import com.swarm.base.entity.BusWechatUser;

public interface BusWeUserFavoriteDao extends JpaRepository<BusWeUserFavorite, Integer> {
	
	List<BusWeUserFavorite> findByBusProductAndBusWechatUserAndBusUserId(BusProduct busProduct , BusWechatUser busWechatUser , Integer busUserId);
	
	Page<BusWeUserFavorite> findByBusWechatUserAndBusUserId(BusWechatUser busWechatUser , Integer busUserId , Pageable pageable);
	
	@Modifying
	@Query("delete from BusWeUserFavorite f where f.id in ?1 and f.busWechatUser.id = ?2 and f.busUserId = ?3")
	void deleteByIdInAndBusWechatUserAndBusUserId(Collection<Integer> id , Integer userId , Integer busUserId);
	
}
