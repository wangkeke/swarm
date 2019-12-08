package com.swarm.base.entity;

/**
 * 优惠券类型
 * @author Administrator
 *
 */
public enum CouponType {
	
	COUPON_MONEY_TYPE("现金优惠券"),COUPON_DISCOUNT_TYPE("折扣优惠券");
	
	private String name;
	
	CouponType(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
