package com.swarm.base.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * 商家优惠券
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusCoupon extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7179891931243365684L;
	
	/**
	 * 是否启用
	 */
	private boolean enable;
	
	/**
	 * 发放开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date offerStart;
	
	/**
	 * 发放结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date offerEnd;
	
	/**
	 * 使用开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date useStart;
	
	/**
	 * 使用结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date useEnd;
	
	/**
	 * 最小消费金额限制
	 */
	@Column(scale = 2)
	private BigDecimal minAmount;
	
	/**
	 * 优惠券面值
	 */
	@Column(scale = 2)
	private BigDecimal parValue;
	
	/**
	 * 启用商品分类限制
	 */
	private boolean enableCate;
	
	/**
	 * 是否可以叠加使用，叠加的优惠券中最多只能有一张优惠券不支持叠加
	 */
	private boolean overlay;
	
	/**
	 * 商家用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
