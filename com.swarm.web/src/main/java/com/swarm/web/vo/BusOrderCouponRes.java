package com.swarm.web.vo;

import java.math.BigDecimal;

import com.swarm.base.entity.BusOrderCoupon;
import com.swarm.base.entity.CouponType;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusOrderCouponRes extends Res<BusOrderCoupon> {
	
	/**
	 * 优惠券类型
	 */
	private CouponType couponType;
	
	/**
	 * 最小消费金额限制
	 */
	private BigDecimal minAmount;
	
	/**
	 * 优惠券面值
	 */
	private BigDecimal parValue;
	
	/**
	 * 是否可以叠加使用，叠加的优惠券中最多只能有一张优惠券不支持叠加
	 */
	private Boolean overlay;

	/**
	 * 启用商品分类限制
	 */
	private Boolean enableCate;
	
	@Override
	public VO apply(BusOrderCoupon t) {
		this.couponType = t.getBusWeUserCoupon().getBusCoupon().getCouponType();
		this.minAmount = t.getBusWeUserCoupon().getBusCoupon().getMinAmount();
		this.parValue = t.getBusWeUserCoupon().getBusCoupon().getParValue();
		this.overlay = t.getBusWeUserCoupon().getBusCoupon().isOverlay();
		this.enableCate = t.getBusWeUserCoupon().getBusCoupon().isEnableCate();
		return this;
	}
	
}
