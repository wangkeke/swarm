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
public class SysShopMenu extends BaseEntity {

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
	 * 请求URL
	 */
	private String url;
	
	/**
	 * 排序，从小到大
	 */
	@Column(name = "`sort`")
	private int sort;
	
	/**
	 * 标识，-1表示禁用/删除，0表示正常
	 */
	private int flag;
	
}
