package com.swarm.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 线下店铺信息
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusStoreInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2173798622806698148L;
	
	/**
	 * 商铺类型
	 */
	@ManyToOne(optional = false)
	@JoinColumn
	private SysDict type;
	
	/**
	 * 商铺名称
	 */
	private String storeName;
	
	/**
	 * 上传的logo路径(200px*200px)
	 */
	private String logo;
	
	/**
	 * 联系人称呼
	 */
	private String contact;
	
	/**
	 * 联系人电话
	 */
	private String phone;
	
	/**
	 * 微信号
	 */
	private String wechat;
	
	
	/**
	 * 提供的服务/业务
	 */
	private String business;
	
	/**
	 * 商家店铺实拍轮播图片
	 */
	@Column(length = 500)
	private String carousel;
	
	/**
	 * 商家店铺内景图片
	 */
	@Column(length = 500)
	private String scene;
	
	/**
	 * 商家地址
	 */
	private String address;
	
	/**
	 * 商家描述
	 */
	private String introduce;
	
	/**
	 * 商家用户ID，分库分表字段
	 */
	private Integer busUserId;
	
}
