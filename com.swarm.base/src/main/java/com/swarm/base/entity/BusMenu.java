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
	@Column(name = "menu_key",length = 20)
	private String key;
	
	/**
	 * 请求URL
	 */
	@Column(length = 100)
	private String url;
	
	/**
	 * 菜单描述
	 */
	@Column(name = "`desc`",length = 20)
	private String desc;
	
	/**
	 * 是否展示
	 */
	@Column(name = "`show`")
	private boolean show;
	
	/**
	 * 排序，从小到大
	 */
	private int sort;
	
	/**
	 * 商户用户ID
	 */
	private Integer busUserId;
	
}
