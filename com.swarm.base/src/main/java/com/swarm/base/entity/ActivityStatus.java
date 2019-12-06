package com.swarm.base.entity;

/**
 * 活动节点
 * @author Administrator
 *
 */
public enum ActivityStatus {
	PENDING_PAYMENT("待付款"),PENDING_Ship("待发货"),REFUNDING("退款中"),PENDING_CONFIRM("待确认");
	
	private String name;
	
	private ActivityStatus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
	
}
