package com.swarm.base.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 商家小程序微信用户收货地址管理
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusWeUserAddress extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8012280069333468273L;
	
	/**
	 * 商家小程序微信用户
	 */
	@ManyToOne
	@JoinColumn
	private BusWechatUser busWechatUser;
	
	/**
	 * 地址区域
	 */
	private String address;
	
	/**
	 * 位置/定位
	 */
	private String location;
	
	/**
	 * 首选地址
	 */
	private boolean first;
	
	/**
	 * 联系人
	 */
	private String contact;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 删除标识
	 */
	private int flag;
	
	/**
	 * 商家用户ID，分库分表字段
	 */
	private Integer busUserId;
	
}
