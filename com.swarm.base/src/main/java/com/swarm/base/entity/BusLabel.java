package com.swarm.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 商户自定义标签
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusLabel extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6649107255854138744L;
	
	@Column(length = 4)
	private String label;
	
	/**
	 * 优先级，从小到大
	 */
	private int sort;
	
	/**
	 * 商户用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
