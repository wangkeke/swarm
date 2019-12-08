package com.swarm.base.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * 小程序的弹窗广告，只展示最新创建时间的广告，并且满足开始时间和结束时间
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusAdvertising extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1433650996374727699L;
	
	/**
	 * 广告图片上传路径
	 */
	private String adPath;
	
	/**
	 * 开始日期，yyyy-MM-dd
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	/**
	 * 结束日期，yyyy-MM-dd
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	/**
	 * 是否启用，
	 */
	private boolean enable;
	
	/**
	 * 商家用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
