package com.swarm.base.entity;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import lombok.Getter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.swarm.base.service.ActivityNode;

/**
 * 订单
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
@Log4j2
public class BusOrder extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3491729769437318411L;
	
	/**
	 * 订单号
	 */
	private String orderCode;
	
	/**
	 * 微信用户
	 */
	@ManyToOne
	@JoinColumn
	private BusWechatUser busWechatUser;
	
	/**
	 * 总金额
	 */
	@Column(scale = 2)
	private BigDecimal amount;
		
	/**
	 * 当前订单所在流程节点
	 */
	@Enumerated(EnumType.ORDINAL)
	private ActivityNode activityNode;
	
	/**
	 * 原因，主要针对失败的流程节点需要阐述的原因
	 */
	private String reason;
	
	/**
	 * 商户用户ID，分表分库字段
	 */
	private Integer busUserId;
	
	public static String generateOrderCode(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateStr = sdf.format(new Date());
		String ipStr = getIPAddress();
		ipStr = ipStr.substring(ipStr.lastIndexOf(".")+1);
		return dateStr+(Integer.parseInt(ipStr)+100) + (1000 + Thread.currentThread().getId());
	}
	
}
