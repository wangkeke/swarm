package com.swarm.web.vo;

import com.swarm.base.entity.BusSalesRule;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.SalesRuleTypeRes;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusSalesRuleRes extends Res<BusSalesRule> {
	
	/**
	 * 规则名称
	 */
	private String name;
	
	/**
	 * 图标路径，(345px*180px)
	 */
	private String icon;
	
	/**
	 * 类型
	 */
	private SalesRuleTypeRes type;
	
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
	public VO apply(BusSalesRule t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.name = t.getName();
		this.icon = t.getIcon();
		this.type = new SalesRuleTypeRes();
		this.type.apply(t.getType());
		this.configUrl = t.getConfigUrl();
		this.enable = t.isEnable();
		return this;
	}

}
