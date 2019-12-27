package com.swarm.base.service;

/**
 * 活动节点
 * @author Administrator
 *
 */
public enum ActivityStatus {
	PENDING_PAYMENT("待付款"),PENDING_SHIP("待发货"),PENDING_REFUNDING("退款中"),PENDING_CONFIRM("待确认"),PENDING_PICKUP("待提货"),CANCELLED("已取消"),CONFIRMED("已完成"),PICKEDUP("已提货"),REFUNDED("已退款"),
	PENDING_WITHDRAWAL("待出金"),REFUSE_WITHDRAWAL("拒绝提现"),
	CASHBACKING("返现中");
	
	private String name;
	
	private ActivityStatus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
	
}
