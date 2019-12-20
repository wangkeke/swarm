package com.swarm.admin.vo;

import com.swarm.base.entity.SysSalesRule;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysSalesRuleRes extends Res<SysSalesRule> {
	
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
	private String key;
	
	/**
	 * 该规则配置页url
	 */
	private String configUrl;
		
	/**
	 * 详情页内容，商家用户自行编辑
	 */
	private String content;
	
	/**
	 * 启用/禁用
	 */
	private Boolean enable;
	
	
	@Override
	public VO apply(SysSalesRule t) {
		this.id = t.getId();
		this.name = t.getName();
		this.icon = t.getIcon();
		this.key = t.getKey();
		this.configUrl = t.getConfigUrl();
		this.content = t.getContent();
		this.enable = t.isEnable();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		return this;
	}

}
