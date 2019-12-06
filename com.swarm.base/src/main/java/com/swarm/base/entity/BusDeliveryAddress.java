package com.swarm.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 发货地址
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusDeliveryAddress extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8476146724664578035L;
	
	/**
	 * 发货点名称
	 */
	private String name;
	
	/**
	 * 门店电话
	 */
	private String phone;
	
	/**
	 * 门店地址
	 */
	private String address;
	
	/**
	 * 排序
	 */
	@Column(name = "`sort`")
	private int sort;
	
	/**
	 * 商户用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
