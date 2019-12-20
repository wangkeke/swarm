package com.swarm.admin.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.SysSalesRule;
import com.swarm.base.vo.UpdateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSysSalesRuleReq extends UpdateReq<SysSalesRule> {
	
	@NotNull(message = "ID不能为空！")
	private Integer id;
	
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
	public void update(SysSalesRule t) {
		t.setUpdateDate(new Date());
		t.setConfigUrl(this.configUrl);
		t.setContent(this.content);
		t.setEnable(this.enable);
		t.setName(this.name);
		t.setIcon(this.icon);
	}

}
