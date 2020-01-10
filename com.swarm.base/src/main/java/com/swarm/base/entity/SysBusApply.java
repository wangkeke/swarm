package com.swarm.base.entity;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 我也要做小程序
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class SysBusApply extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6726973930657160493L;

	/**
	 * 联系人
	 */
	private String contact;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 所属行业
	 */
	private String business;
	
	/**
	 * 0：申请，1：已处理/已确认
	 */
	private int status;
	
	/**
	 * 商家用户ID
	 */
	private Integer busUserId;
	
}
