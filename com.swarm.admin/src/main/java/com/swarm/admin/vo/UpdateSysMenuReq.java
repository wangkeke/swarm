package com.swarm.admin.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.SysMenu;
import com.swarm.base.vo.UpdateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSysMenuReq extends UpdateReq<SysMenu> {
	
	
	@NotNull(message = "ID不能为空！")
	private Integer id;
	
	/**
	 * 菜单名称
	 */
	@NotBlank(message = "菜单名称不能为空！")
	private String name;
	
	/**
	 * 图标路径，100px*100px
	 */
	@NotBlank(message = "菜单图标不能为空！")
	private String icon;
	
	/**
	 * 菜单key唯一标识例如：menu_shop_rule_1
	 */
	@NotBlank(message = "菜单key不能为空！")
	private String key;
	
	/**
	 * 请求URL
	 */
	@NotBlank(message = "url不能为空！")
	private String url;
	
	/**
	 * 菜单描述
	 */
	private String desc;
	
	/**
	 * 排序，从小到大
	 */
	private Integer sort;

	@Override
	public void update(SysMenu t) {
		t.setUpdateDate(new Date());
		t.setName(this.name);
		t.setIcon(this.icon);
		t.setUrl(this.url);
		t.setKey(this.key);
		t.setDesc(this.desc);
		if(this.sort!=null) {
			t.setSort(this.sort);
		}
	}
	

}
