package com.swarm.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统菜单
 * @author Administrator
 *
 */
@Getter
@Setter
@Entity
public class SysMenu extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5058323686133651481L;
	
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
	@Column(name = "menu_key" ,length = 20, unique = true)
	private String key;
	
	/**
	 * 请求URL
	 */
	private String url;
	
	/**
	 * 菜单描述
	 */
	@Column(name = "`desc`",length = 20)
	private String desc;
	
	/**
	 * 排序，从小到大
	 */
	private int sort;
	
	/**
	 * 标识，-1删除，0表示正常
	 */
	private int flag;
	
}
