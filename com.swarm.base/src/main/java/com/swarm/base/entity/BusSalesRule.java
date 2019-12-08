package com.swarm.base.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import lombok.Getter;
import lombok.Setter;

/**
 * 促销规则，例如：返现规则，等
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusSalesRule extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3218414865712432620L;
	
	/**
	 * 图标路径，(345px*180px)
	 */
	private String icon;
	
	/**
	 * 菜单key唯一标识例如：menu_shop_rule_1
	 */
	@Column(unique = true)
	private String key;
	
	/**
	 * 该规则配置页url
	 */
	private String configUrl;
	
	/**
	 * 该规则的详情说明页url
	 */
	private String detailUrl;
	
	/**
	 * 详情页内容，商家用户自行编辑
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String content;
	
	/**
	 * 启用/禁用
	 */
	private boolean enable;
	
	/**
	 * 商家用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
