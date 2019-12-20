package com.swarm.admin.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.swarm.base.entity.SysMenu;
import com.swarm.base.vo.CreateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysMenuReq extends CreateReq<SysMenu> {
	
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
	public SysMenu create() {
		SysMenu sysMenu = new SysMenu();
		sysMenu.setUpdateDate(new Date());
		sysMenu.setCreateDate(new Date());
		sysMenu.setDesc(this.desc);
		sysMenu.setIcon(this.icon);
		sysMenu.setKey(this.key);
		sysMenu.setName(this.name);
		sysMenu.setSort(this.sort);
		sysMenu.setUrl(this.url);
		return sysMenu;
	}

}
