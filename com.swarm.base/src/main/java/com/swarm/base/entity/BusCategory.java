package com.swarm.base.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品分类
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusCategory extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3497700294109208551L;
	
	/**
	 * 分类名称
	 */
	private String name;
	
	/**
	 * 分类图标(100px*100px)
	 */
	private String icon;
	
	/**
	 * 是否显示，默认为true
	 */
	private boolean show;
	
	/**
	 * 父级分类
	 */
	@ManyToOne
	@JoinColumn
	private BusCategory parent;
	
	/**
	 * 商户用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
