package com.swarm.base.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单包含的商品列表
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusOrderProduct extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5586923690509398218L;
	
	/**
	 * 对应的订单
	 */
	@ManyToOne
	@JoinColumn
	private BusOrder busOrder;
	
	/**
	 * 购买的商品
	 */
	@ManyToOne
	@JoinColumn
	private BusProduct busProduct;
	
	/**
	 * 商品名称
	 */
	private String title;
	
	/**
	 * 商品图片
	 */
	private String image;
	
	/**
	 * 商品颜色
	 */
	private String color;
	
	/**
	 * 商品尺寸
	 */
	private String size;
	
	/**
	 * 购买数量
	 */
	private Integer number;
	
	/**
	 * 商品对应的单价
	 */
	@Column(scale = 2)
	private BigDecimal price;
	
	/**
	 * 商家ID,分表分库字段
	 */
	private Integer busUserId;
	
}
