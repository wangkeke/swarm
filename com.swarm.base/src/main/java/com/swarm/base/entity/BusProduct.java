package com.swarm.base.entity;

import java.math.BigDecimal;

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
public class BusProduct extends BaseEntity {

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
	private int stocks;
	
	/**
	 * 销量
	 */
	private int sales;
	
	/**
	 * 人气收藏
	 */
	private int favorite;
	
	/**
	 * 首图路径
	 */
	@Column(length = 100)
	private String image;
	
	/**
	 * 商品相册
	 */
	@Basic(fetch = FetchType.LAZY)
	@Column(length = 500)
	private String images;
	
	/**
	 * 商品颜色
	 */
	private String colors;
	
	/**
	 * 商品尺寸
	 */
	private String sizes;
	
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
	@Column(name = "`show`")
	private boolean show;
	
	/**
	 * 标签，如热销，折扣，爆款等
	 */
	@ManyToOne
	@JoinColumn
	private BusLabel label;
	
	/**
	 * 删除标识，-1标识删除
	 */
	private int flag;
	
	/**
	 * 商户用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
