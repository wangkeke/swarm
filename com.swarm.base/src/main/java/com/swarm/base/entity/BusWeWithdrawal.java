package com.swarm.base.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.swarm.base.service.ActivityNode;

import lombok.Getter;
import lombok.Setter;

/**
 * 商家微信用户提现/出金
 * @author Administrator
 *
 */
@Getter
@Setter
@Entity
public class BusWeWithdrawal extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8482543046232084969L;
	
	/**
	 * 申请提现的微信用户
	 */
	@ManyToOne
	@JoinColumn
	private BusWechatUser busWechatUser;
	
	/**
	 * 申请的提现的金额
	 */
	@Column(scale = 2)
	private BigDecimal money;
	
	/**
	 * 提现方式
	 */
	private PaymentType paymentType;
	
	/**
	 * 银联支付方式：选择的银行
	 */
	@ManyToOne
	@JoinColumn
	private SysDict bankDict;
	
	/**
	 * 银联支付方式：银行卡号
	 */
	private String bankCardNo;
	
	/**
	 * 银联支付方式：持卡人姓名
	 */
	private String bankCardName;
	
	/**
	 * 银联支付方式：银行预留手机号
	 */
	private String bankPhone;
	
	/**
	 * 微信支付方式：微信号
	 */
	private String wechatId;
	
	/**
	 * 支付宝账号
	 */
	private String alipayId;
	
	/**
	 * 微信支付方式：真实姓名
	 */
	private String realName;
	
	/**
	 * 当前所在流程活动节点
	 */
	@Enumerated(EnumType.ORDINAL)
	private ActivityNode activityNode;
	
	/**
	 * 原因，主要针对失败的流程节点需要阐述的原因
	 */
	private String comment;
	
	/**
	 * 手续费
	 */
	@Column(scale = 2)
	private BigDecimal handlingFee;
	
	/**
	 * 实收金额
	 */
	@Column(scale = 2)
	private BigDecimal receivedMoney;
	
	/**
	 * 商户用户Id，分表分库字段
	 */
	private Integer busUserId;

	
}
