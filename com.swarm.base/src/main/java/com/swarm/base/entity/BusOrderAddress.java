package com.swarm.base.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单收货地址
 * @author Administrator
 *
 */
@Getter
@Setter
@Entity
public class BusOrderAddress extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1623251461348226214L;
	
	/**
	 * 地址区域
	 */
	private String address;
	
	/**
	 * 位置/定位
	 */
	private String location;
	
	/**
	 * 联系人
	 */
	private String contact;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 对应的订单
	 */
	@OneToOne
	@JoinColumn
	private BusOrder busOrder;
	
	/**
	 * 商家用户ID，分库分表字段
	 */
	private Integer busUserId;
	
}
