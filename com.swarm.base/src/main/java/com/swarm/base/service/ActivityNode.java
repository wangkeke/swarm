package com.swarm.base.service;

/**
 * 活动节点
 * @author Administrator
 *
 */
public enum ActivityNode {
	PAY_NO("未付款"),PAID("已付款"),APPLY_REFUND("申请退款"),REFUNDED("已退款"),REFUSE_REFUND("拒绝退款申请"),SHIPPED("已发货"),
	CONFIRMED("已确认"),CANCELLED("已取消");
	
	private String name;
	
	private ActivityNode(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
