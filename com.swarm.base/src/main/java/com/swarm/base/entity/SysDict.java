package com.swarm.base.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 系统字典表
 * @author Administrator
 *
 */
public class SysDict extends BaseEntity {
	
	/**
	 * 字典名称
	 */
	private String name;
	
	/**
	 * 字典类型，如：bank_type(银行类型:中国银行，建设银行，工商银行等),shop_type(商城类型：建材，家居，电器)，region_type(地区类型：河南省，郑州市，洛阳市，开封市)等
	 */
	@Enumerated(EnumType.ORDINAL)
	private SysDictType type;
	
	/**
	 * 同一类型下key唯一
	 */
	@Column(unique = true)
	private String key;
	
	
	private String value;
	
	private String value2;
	
	/**
	 * 关联的父级，如地域类型region_type，需要关联上下级
	 */
	@ManyToOne
	@JoinColumn
	private SysDict parent;
	
}
