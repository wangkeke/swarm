package com.swarm.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BusMnprogramStat extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 186375498065858706L;
	
	/**
	 * 统计日期，格式：yyyyMMdd
	 */
	@Column(length = 10)
	private String statDate;
	
	/**
	 * 累计用户数
	 */
	private Integer visitTotal;
	
	/**
	 * 转发次数
	 */
	private Integer sharePv;
	
	/**
	 * 转发人数
	 */
	private Integer shareUv;
	
	/**
	 * 新增用户
	 */
	private Integer visitUvNew;
	
	/**
	 * 活跃用户
	 */
	private Integer visitUv;
	
	@OneToOne
	@JoinColumn
	private BusMnprogram mnprogram;
	
	/**
	 * 商家ID，分表分库字段
	 */
	private Integer busUserId;
	
}
