package com.swarm.base.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 优惠券使用所限制的商品类型
 * @author Administrator
 *
 */
@Getter
@Setter
@Entity
public class BusCouponCategory extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7276955895351634070L;
	
	/**
	 * 优惠券
	 */
	@ManyToOne
	@JoinColumn
	private BusCoupon busCoupon;
	
	/**
	 * 商品类型
	 */
	@ManyToOne
	@JoinColumn
	private BusCategory busCategory;
	
	/**
	 * 商家用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
