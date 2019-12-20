package com.swarm.admin.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.swarm.base.entity.SysMenu;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysMenuRes extends Res<SysMenu> {
	
	
	private Integer id;
	/**
	 * 菜单名称
	 */
	private String name;
	
	/**
	 * 图标路径，100px*100px
	 */
	private String icon;
	
	/**
	 * 菜单key唯一标识例如：menu_shop_rule_1
	 */
	private String key;
	
	/**
	 * 请求URL
	 */
	private String url;
	
	/**
	 * 菜单描述
	 */
	private String desc;
	
	/**
	 * 排序，从小到大
	 */
	private Integer sort;
	
	@JsonFormat(shape = Shape.STRING,pattern = DEFAULT_DATETIME_PATTERN)
	private Date updateDate;
	
	@JsonFormat(shape = Shape.STRING , pattern = DEFAULT_DATETIME_PATTERN)
	private Date createDate;
	
	@Override
	public VO apply(SysMenu t) {
		this.id = t.getId();
		this.name = t.getName();
		this.icon = t.getIcon();
		this.key = t.getKey();
		this.url = t.getUrl();
		this.desc = t.getDesc();
		this.sort = t.getSort();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		return this;
	}

}
