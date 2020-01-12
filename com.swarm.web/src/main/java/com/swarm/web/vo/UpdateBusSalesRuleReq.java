package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.BusSalesRule;
import com.swarm.base.vo.UpdateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBusSalesRuleReq extends UpdateReq<BusSalesRule> {
	
	
	@NotNull(message = "ID不能为空！")
	private Integer id;
	
	/**
	 * 规则名称
	 */
	@NotBlank(message = "规则名称不能为空！")
	private String name;
	
	/**
	 * 图标路径，(345px*180px)
	 */
	@NotBlank(message = "图标不能为空！")
	private String icon;
	
	/**
	 * 启用/禁用
	 */
	@NotNull(message = "是否启用不能为空！")
	private Boolean enable;
	
	@NotBlank(message = "内容不能为空！")
	private String content;
	
	@Override
	public void update(BusSalesRule t) {
		t.setUpdateDate(new Date());
		t.setName(this.name);
		t.setIcon(this.icon);
		t.setEnable(this.enable);
		t.setContent(this.content);
	}

}
