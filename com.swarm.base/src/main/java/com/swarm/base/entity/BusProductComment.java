package com.swarm.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BusProductComment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 412999577929663274L;
	
	/**
	 * 商品
	 */
	@ManyToOne
	@JoinColumn
	private BusProduct busProduct;
	
	/**
	 * 评论内容
	 */
	@Column(length = 500)
	private String content;
	
	/**
	 * 评论图片；多个图片以逗号分割
	 */
	private String images;
	
	/**
	 * 商户用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
