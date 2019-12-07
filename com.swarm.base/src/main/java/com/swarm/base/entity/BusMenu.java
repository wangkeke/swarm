package com.swarm.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 商家首页菜单管理，这些菜单从sysmenu表中复制而来
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
	 * 菜单key唯一标识例如：menu_shop_rule_1
	 */
	@Column(unique = true)
	private String key;
	
	/**
	 * 菜单描述
	 */
	private String desc;
	
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
