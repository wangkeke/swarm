package com.swarm.base.entity;

/**
 * 系统字典类型
 * @author Administrator
 *
 */
public enum SysDictType {
	
	BANK_TYPE(SysDictType.PUBLIC,"银行类型"),SHOP_TYPE(SysDictType.PUBLIC,"商城类型"),REGION_TYPE(SysDictType.PUBLIC,"地域类型"),
	WITHDRAWAL_SET(SysDictType.PRIVATE,"提现设置"),ORDER_SET(SysDictType.PRIVATE,"订单设置"),CASHBACK_SET(SysDictType.PRIVATE,"返现配置"),
	POSTER_SET(SysDictType.PRIVATE,"海报设置");
	
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
	
	SysDictType(int id , String name){
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
