package com.swarm.base.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单中使用的优惠券
 * @author Administrator
 *
 */

@Entity
@Getter
@Setter
public class BusOrderCoupon extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8679166616291765342L;
	
	/**
	 * 关联的订单
	 */
	@ManyToOne
	@JoinColumn
	private BusOrder busOrder;
	
	/**
	 * 关联的商家微信用户已领取的优惠券
	 */
	@ManyToOne
	@JoinColumn
	private BusWeUserCoupon busWeUserCoupon;
	
}
