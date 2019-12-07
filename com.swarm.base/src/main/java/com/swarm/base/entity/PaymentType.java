package com.swarm.base.entity;

/**
 * 支付渠道类型
 * @author Administrator
 *
 */
public enum PaymentType {
	
	UNIONPAY("银联"),WECHAT_PAY("微信支付"),ALIPAY("支付宝");
	
	private String name;
	
	PaymentType(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
