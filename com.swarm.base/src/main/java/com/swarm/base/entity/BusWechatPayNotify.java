package com.swarm.base.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BusWechatPayNotify extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1492998491777062379L;
	
	/**
	 * 订单号
	 */
	private String orderCode;
	
	/**
	 * 入金微信用户
	 */
	@ManyToOne
	@JoinColumn
	private BusWechatUser busWechatUser;
	
	/**
	 * 状态，0：预支付，1:支付完成
	 */
	private int status;
	
	/**
	 * 预支付body字段
	 */
	private String body;
	
	/**
	 * 预支付notify_url字段
	 */
	private String notifyUrl;
	
	/**
	 * 入金金额
	 */
	@Column(scale = 2)
	private BigDecimal money;
	
	/**
	 * 商户用户ID，
	 */
	private Integer busUserId;
	
}
