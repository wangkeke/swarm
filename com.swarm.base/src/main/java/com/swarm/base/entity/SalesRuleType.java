package com.swarm.base.entity;

/**
 * 促销规则类型
 * @author Administrator
 *
 */
public enum SalesRuleType{
	
	CASHBACK("返现规则");
	
	private String name;
	
	SalesRuleType(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
