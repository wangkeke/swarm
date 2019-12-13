package com.swarm.base.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 商家微信用户钱包
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusWeUserWallet extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -107041950904985963L;
	
	
	/**
	 * 账户余额
	 */
	@Column(scale = 2)
	private BigDecimal balance;
	
	
	/**
	 * 父级用户，即：老用户分享拉入的用户
	 */
	@ManyToOne
	@JoinColumn
	private BusWechatUser busWechatUser;
	
	/**
	 * 商家用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
