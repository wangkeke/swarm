package com.swarm.app.vo;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.swarm.base.entity.BusOrder;
import com.swarm.base.vo.CreateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusOrderReq extends CreateReq<BusOrder> {
	
	/**
	 * 购买的商品
	 */
	private Integer[] productId;
	
	/**
	 * 商品颜色
	 */
	private String[] color;
	
	/**
	 * 商品尺寸
	 */
	private String[] size;
	
	/**
	 * 购买数量
	 */
	private Integer[] number;
	
	/**
	 * 总付款
	 */
	@NotNull(message = "总金额不正确！")
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal amount;
	
	/**
	 * 是否钱包支付
	 */
	private Boolean walletPay;
	
	/**
	 * 使用的优惠券ID
	 */
	private Integer[] userCouponId;
	
	/**
	 * 是否自提
	 */
	private Boolean selfpick;
	
	/**
	 * 提货人姓名
	 */
	private String name;
	
	/**
	 * 提货人手机号
	 */
	private String phone;
	
	/**
	 * 用户地址ID
	 */
	private Integer addressId;
	
	@Override
	public BusOrder create() {
		// TODO Auto-generated method stub
		return null;
	}

}
