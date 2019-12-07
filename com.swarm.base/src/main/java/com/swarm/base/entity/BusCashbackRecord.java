package com.swarm.base.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信用户返现记录
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusCashbackRecord extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5601504752006870762L;
	
	/**
	 * 返现来源的订单号
	 */
	@ManyToOne
	@JoinColumn
	private BusOrder busOrder;
	
	/**
	 * 商家微信用户
	 */
	@ManyToOne
	@JoinColumn
	private BusWechatUser busWechatUser;
	
	/**
	 * 返现的金额
	 */
	@Column(scale = 2)
	private BigDecimal cashbackMoney;
	
	/**
	 * 商家用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
