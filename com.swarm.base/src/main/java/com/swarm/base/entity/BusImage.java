package com.swarm.base.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	private String path;
	
	/**
	 * 商家用户ID，业务分表分库字段
	 */
	private Integer busUserId;
	
	/**
	 * 商品ID
	 */
	@ManyToOne
	@JoinColumn
	private BusGoods busGoods;
	
}
