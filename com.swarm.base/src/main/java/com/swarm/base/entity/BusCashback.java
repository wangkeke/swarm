package com.swarm.base.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.swarm.base.service.ActivityNode;

import lombok.Getter;
import lombok.Setter;

/**
 * 返现
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusCashback extends BaseEntity {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2396127738610870866L;

	/**
	 * 商家微信用户
	 */
	@ManyToOne
	@JoinColumn
	private BusWechatUser busWechatUser;
	
	/**
	 * 需要的返现金额
	 */
	@Column(scale = 2)
	private BigDecimal reqCashback;
	
	/**
	 * 已返的返现金额
	 */
	@Column(scale = 2)
	private BigDecimal hasCashback;
	
	/**
	 * 当前返现流程状态节点
	 */
	private ActivityNode activityNode;
	
	/**
	 * 商家用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
