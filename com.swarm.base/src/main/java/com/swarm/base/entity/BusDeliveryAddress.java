package com.swarm.base.entity;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 发货地址/自提点地址
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
	 * 是否启用
	 */
	private boolean enable;
	
	/**
	 * 删除标识，-1表示删除
	 */
	private int flag;
	
	/**
	 * 商户用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
