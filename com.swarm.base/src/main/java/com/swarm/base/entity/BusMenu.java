package com.swarm.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 首页菜单管理
 * @author Administrator
 *
 */
@Getter
@Setter
@Entity
public class BusMenu extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6736584864592008432L;
	
	/**
	 * 菜单名称
	 */
	private String name;
	
	/**
	 * 图标路径，100px*100px
	 */
	private String icon;
	
	/**
	 * 是否展示
	 */
	private boolean show;
	
	/**
	 * 排序，从小到大
	 */
	@Column(name = "`sort`")
	private int sort;
	
	/**
	 * 商户用户ID
	 */
	private Integer busUserId;
	
}
