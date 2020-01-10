package com.swarm.admin.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.SalesRuleType;
import com.swarm.base.entity.SysSalesRule;
import com.swarm.base.vo.CreateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysSalesRuleReq extends CreateReq<SysSalesRule> {
	
	/**
	 * 规则名称
	 */
	@NotBlank(message = "请输入规则名称！")
	private String name;
	
	/**
	 * 图标路径，(345px*180px)
	 */
	@NotBlank(message = "请上传图标路径！")
	private String icon;
	
	/**
	 * type唯一标识例如：menu_shop_rule_1
	 */
	@NotBlank(message = "类型不能为空！")
	private SalesRuleType type;
	
	/**
	 * 该规则配置页url
	 */
	@NotBlank(message = "请输入配置页url！")
	private String configUrl;
		
	/**
	 * 详情页内容，商家用户自行编辑
	 */
	@NotBlank(message = "详情页内容不能为空！")
	private String content;
	
	/**
	 * 启用/禁用
	 */
	@NotNull(message = "请选择是否启用！")
	private Boolean enable;
	
	@Override
	public SysSalesRule create() {
		SysSalesRule rule = new SysSalesRule();
		rule.setCreateDate(new Date());
		rule.setUpdateDate(new Date());
		rule.setConfigUrl(this.configUrl);
		rule.setContent(this.content);
		rule.setEnable(this.enable);
		rule.setIcon(this.icon);
		rule.setType(this.type);
		rule.setName(this.name);
		return rule;
	}

}
