package com.swarm.base.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.swarm.base.service.ActivityNode;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单流程节点记录
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusRecord extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4762361647979065553L;
	
	/**
	 * 订单
	 */
	@ManyToOne
	@JoinColumn
	private BusOrder busOrder;
	
	/**
	 * 记录的订单流程节点
	 */
	@Enumerated(EnumType.ORDINAL)
	private ActivityNode activityNode;
	
	/**
	 * 备注说明/原因，主要针对失败的流程节点需要阐述的原因
	 */
	private String comment;
	
	/**
	 * 商家操作执行该节点（与属性busWechatUser二者择一，不可能同时出现）
	 */
	@ManyToOne
	@JoinColumn(name = "bususer_id")
	private BusUser busUser;
	
	
	/**
	 * 微信用户操作执行该节点（与属性busUser二者择一，不可能同时出现）
	 */
	@ManyToOne
	@JoinColumn
	private BusWechatUser busWechatUser;
	
	/**
	 * 商家用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
