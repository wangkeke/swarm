package com.swarm.web.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.swarm.base.entity.BusCoupon;
import com.swarm.base.entity.CouponType;
import com.swarm.base.vo.UpdateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBusCouponReq extends UpdateReq<BusCoupon> {
	
	@NotNull(message = "ID不能为空！")
	private Integer id;
	
	/**
	 * 优惠券类型
	 */
	@NotNull(message = "请选择优惠券类型！")
	private CouponType couponType;
	
	/**
	 * 是否启用
	 */
	@NotNull(message = "是否启用不能为空！")
	private Boolean enable;
	
	/**
	 * 发放开始时间
	 */
	@NotNull(message = "发放开始时间不能为空！")
	@DateTimeFormat(pattern = DEFAULT_DATE_PATTERN)
	private Date offerStart;
	
	/**
	 * 发放结束时间
	 */
	@DateTimeFormat(pattern = DEFAULT_DATE_PATTERN)
	private Date offerEnd;
	
	/**
	 * 使用开始时间
	 */
	@NotNull(message = "使用开始时间不能为空！")
	@DateTimeFormat(pattern = DEFAULT_DATE_PATTERN)
	private Date useStart;
	
	/**
	 * 使用结束时间
	 */
	@DateTimeFormat(pattern = DEFAULT_DATE_PATTERN)
	private Date useEnd;
	
	/**
	 * 最小消费金额限制
	 */
	@NotNull(message = "可使用的最小消费金额不能为空！")
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal minAmount;
	
	/**
	 * 优惠券面值
	 */
	@NotNull(message = "优惠券面值不能为空！")
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal parValue;
	
	/**
	 * 启用商品分类限制
	 */
	@NotNull(message = "是否启用商品分类限制不能为空！")
	private Boolean enableCate;
	
	/**
	 * 是否可以叠加使用，叠加的优惠券中最多只能有一张优惠券不支持叠加
	 */
	@NotNull(message = "是否可以叠加使用不能为空！")
	private Boolean overlay;
	
	/**
	 * 商品分类
	 */
	private Integer[] category; 
	
	
	@Override
	public void update(BusCoupon busCoupon) {
		busCoupon.setUpdateDate(new Date());
		busCoupon.setCouponType(this.couponType);
		busCoupon.setEnable(this.enable);
		busCoupon.setOfferStart(this.offerStart);
		busCoupon.setOfferEnd(this.offerEnd);
		busCoupon.setUseStart(this.useStart);
		busCoupon.setUseEnd(this.useEnd);
		busCoupon.setMinAmount(this.minAmount);
		busCoupon.setParValue(this.parValue);
		busCoupon.setEnableCate(this.enableCate);
		busCoupon.setOverlay(this.overlay);
	}

}
