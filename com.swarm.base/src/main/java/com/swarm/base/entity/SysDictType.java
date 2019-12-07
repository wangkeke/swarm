package com.swarm.base.entity;

/**
 * 系统字典类型
 * @author Administrator
 *
 */
public enum SysDictType {
	
	BANK_TYPE("银行类型"),SHOP_TYPE("商城类型"),REGION_TYPE("地域类型");
	
	private String name;
	
	SysDictType(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
