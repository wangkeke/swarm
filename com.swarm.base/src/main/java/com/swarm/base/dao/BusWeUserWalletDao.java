package com.swarm.base.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.swarm.base.entity.BusWeUserWallet;
import com.swarm.base.entity.BusWechatUser;

public interface BusWeUserWalletDao extends JpaRepository<BusWeUserWallet, Integer> {
	
	BusWeUserWallet findByBusWechatUserAndBusUserId(BusWechatUser busWechatUser , Integer busUserId);
	
	/**
	 * 充值
	 * @param id
	 * @param busUserId
	 * @param amount
	 * @param balance
	 * @return
	 */
	@Modifying
	@Query("update BusWeUserWallet w set w.balance = w.balance + ?3 where w.id = ?1 and w.busUserId = ?2 and w.balance = ?4")
	int rechargeBusWeUserWallet(Integer id , Integer busUserId , BigDecimal amount , BigDecimal balance);
	
	/**
	 * 扣款
	 * @param id
	 * @param busUserId
	 * @param amount
	 * @param balance
	 * @return
	 */
	@Modifying
	@Query("update BusWeUserWallet w set w.balance = w.balance - ?3 where w.id = ?1 and w.busUserId = ?2 and w.balance = ?4")
	int chargeBusWeUserWallet(Integer id , Integer busUserId , BigDecimal amount , BigDecimal balance);
	
}
