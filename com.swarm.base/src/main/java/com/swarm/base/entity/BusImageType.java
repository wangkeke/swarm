package com.swarm.base.entity;

public enum BusImageType {
	/**
	 * 线下店铺轮播图(750px*300px)
	 */
//	STORE_REAL_CAROUSEL("商铺实拍轮播图"),
	/**
	 * 线下店铺展示(500px*500px)
	 */
//	STORE_REAL_SHOW("商铺实拍展示"),
	/**
	 * 商城首页轮播图(750*300)
	 */
	SHOP_HOME_CAROUSEL("商城首页轮播"),
	/**
	 * 商城商品主图(100*100)
	 */
	SHOP_PRODUCT_TITLE("商城商品主图");
	
	private String name;
	
	private BusImageType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
