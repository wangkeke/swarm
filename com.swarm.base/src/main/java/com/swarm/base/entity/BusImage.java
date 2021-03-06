package com.swarm.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Getter;
import lombok.Setter;

/**
 * 商户图片
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusImage extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4002443471820541207L;
	
	/**
	 * 图片类型
	 */
	@Enumerated(EnumType.ORDINAL)
	private BusImageType busImageType;
	
	/**
	 * 图片路径
	 */
	@Column(length = 100)
	private String path;
	
	/**
	 * 排序
	 */
	private int sort;
	
	/**
	 * 商家用户ID，业务分表分库字段
	 */
	private Integer busUserId;
	
}
