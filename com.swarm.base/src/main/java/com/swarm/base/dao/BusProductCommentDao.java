package com.swarm.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.BusProduct;
import com.swarm.base.entity.BusProductComment;

public interface BusProductCommentDao extends JpaRepository<BusProductComment, Integer> {
	
	Page<BusProductComment> findByBusProductAndBusUserId(BusProduct busProduct , Integer busUserId , Pageable pageable);
	
}
