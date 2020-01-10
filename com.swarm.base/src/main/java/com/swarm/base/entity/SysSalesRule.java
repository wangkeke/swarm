package com.swarm.base.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统促销规则
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class SysSalesRule extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7033620187013334061L;
	
	/**
	 * 规则名称
	 */
	private String name;
	
	/**
	 * 图标路径，(345px*180px)
	 */
	private String icon;
	
	/**
	 * 菜单key唯一标识例如：menu_shop_rule_1
	 */
	@Column(length = 20,unique = true)
	private SalesRuleType type;
	
	/**
	 * 该规则配置页url
	 */
	@Column(length = 100)
	private String configUrl;
		
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
	
}
