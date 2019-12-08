package com.swarm.base.entity;

/**
 * 系统字典类型
 * @author Administrator
 *
 */
public enum SysDictType {
	
	BANK_TYPE("银行类型"),SHOP_TYPE("商城类型"),REGION_TYPE("地域类型"),
	WITHDRAWAL_SET("提现设置"),ORDER_SET("订单设置"),CASHBACK_SET("返现配置"),
	POSTER_SET("海报设置");
	
	private String name;
	
	SysDictType(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
