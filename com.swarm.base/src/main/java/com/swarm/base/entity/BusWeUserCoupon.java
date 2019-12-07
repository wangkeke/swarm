package com.swarm.base.entity;

import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;

/**
 * 商家微信用户领取的优惠券
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusWeUserCoupon extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3974211672466799203L;
	
	/**
	 * 商家微信用户
	 */
	@ManyToOne
	@JoinColumn
	private BusWechatUser busWechatUser;
	
	/**
	 * 商家优惠券
	 */
	@ManyToOne
	@JoinColumn
	private BusCoupon busCoupon;
	
	/**
	 * 是否有效
	 */
	private boolean valid;
	
	
	/**
	 * 商家用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
