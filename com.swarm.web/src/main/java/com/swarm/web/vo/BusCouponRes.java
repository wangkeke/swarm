package com.swarm.web.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.swarm.base.entity.BusCoupon;
import com.swarm.base.entity.CouponType;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusCouponRes extends Res<BusCoupon> {
	
	/**
	 * 优惠券类型
	 */
	private CouponType couponType;
	
	/**
	 * 是否启用
	 */
	private Boolean enable;
	
	/**
	 * 发放开始时间
	 */
	@JsonFormat(shape = Shape.STRING , pattern = DEFAULT_DATE_PATTERN)
	private Date offerStart;
	
	/**
	 * 发放结束时间
	 */
	@JsonFormat(shape = Shape.STRING , pattern = DEFAULT_DATE_PATTERN)
	private Date offerEnd;
	
	/**
	 * 使用开始时间
	 */
	@JsonFormat(shape = Shape.STRING , pattern = DEFAULT_DATE_PATTERN)
	private Date useStart;
	
	/**
	 * 使用结束时间
	 */
	@JsonFormat(shape = Shape.STRING , pattern = DEFAULT_DATE_PATTERN)
	private Date useEnd;
	
	/**
	 * 最小消费金额限制
	 */
	private BigDecimal minAmount;
	
	/**
	 * 优惠券面值
	 */
	private BigDecimal parValue;
	
	/**
	 * 启用商品分类限制
	 */
	private Boolean enableCate;
	
	/**
	 * 是否可以叠加使用，叠加的优惠券中最多只能有一张优惠券不支持叠加
	 */
	private Boolean overlay;
	
	/**
	 * 商品分类
	 */
	private List<Integer> category;
	
	
	@Override
	public VO apply(BusCoupon t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.couponType = t.getCouponType();
		this.enable = t.isEnable();
		this.offerStart = t.getOfferStart();
		this.offerEnd = t.getOfferEnd();
		this.useStart = t.getUseStart();
		this.useEnd = t.getUseEnd();
		this.minAmount = t.getMinAmount();
		this.parValue = t.getParValue();
		this.enableCate = t.isEnableCate();
		this.overlay = t.isOverlay();
		return this;
	}

}
