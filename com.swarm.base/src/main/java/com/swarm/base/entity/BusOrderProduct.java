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
	 * 购买数量
	 */
	private int number;
	
	/**
	 * 商品对应的单价
	 */
	@Column(scale = 2)
	private BigDecimal price;
	
}
