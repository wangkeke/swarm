package com.swarm.base.entity;

/**
 * 系统字典类型
 * @author Administrator
 *
 */
public enum DictType {
	
	BANK_TYPE(DictType.PUBLIC,"银行类型"),SHOP_TYPE(DictType.PUBLIC,"商城类型"),REGION_TYPE(DictType.PUBLIC,"地域类型"),
	WITHDRAWAL_SET(DictType.PRIVATE,"提现设置"),ORDER_SET(DictType.PRIVATE,"订单设置"),CASHBACK_SET(DictType.PRIVATE,"返现配置"),
	POSTER_SET(DictType.PRIVATE,"海报设置");
	
	/**
	 * 公共类型
	 */
	public static final int PUBLIC = 0;
	/**
	 * 商家用户私有类型
	 */
	public static final int PRIVATE = 1;
	
	
	private int id;
	
	private String name;
	
	DictType(int id , String name){
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
