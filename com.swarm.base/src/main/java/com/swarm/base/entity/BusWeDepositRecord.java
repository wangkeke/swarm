package com.swarm.base.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Setter;

import lombok.Getter;

/**
 * 商家小程序微信用户入金记录
 * @author Administrator
 *
 */
@Getter
@Setter
@Entity
public class BusWeDepositRecord extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5324526493435180068L;
	
	/**
	 * 入金微信用户
	 */
	@ManyToOne
	@JoinColumn
	private BusWechatUser busWechatUser;
	
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
