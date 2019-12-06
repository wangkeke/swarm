package com.swarm.base.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 商铺商品
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusGoods extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2500540515015549070L;
	
	/**
	 * 商品分类
	 */
	@ManyToOne
	@JoinColumn
	private BusCategory category;
	
	/**
	 * 商品标题
	 */
	@Column(length = 20)
	private String title;
	
	/**
	 * 价格
	 */
	@Column(scale = 2)
	private BigDecimal price;
	
	/**
	 * 总数/库存数
	 */
	private int amount;
	
	/**
	 * 销量
	 */
	private int sales;
	
	/**
	 * 首图
	 */
	@ManyToOne
	@JoinColumn
	private BusImage image;
	
	/**
	 * 商品相册
	 */
	private transient List<BusImage> images;
	
	/**
	 * 商品内容
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column
	private String content;
	
	/**
	 * 是否上架
	 */
	private boolean show = true;
	
	/**
	 * 排序
	 */
	@Column(name = "`sort`")
	private int sort;
	
	/**
	 * 标签，如热销，折扣，爆款等
	 */
	@ManyToOne
	@JoinColumn
	private BusLabel label;
	
	/**
	 * 商户用户ID，分表分库字段
	 */
	private Integer busUserId;
}
