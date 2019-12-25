package com.swarm.web.vo;

import com.swarm.base.entity.BusMenu;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusMenuRes extends Res<BusMenu> {
	
	/**
	 * 菜单名称
	 */
	private String name;
	
	/**
	 * 图标路径，100px*100px
	 */
	private String icon;
	
	/**
	 * 菜单描述
	 */
	private String desc;
	
	/**
	 * 是否展示
	 */
	private Boolean show;
	
	/**
	 * 排序，从小到大
	 */
	private Integer sort;
	
	@Override
	public VO apply(BusMenu t) {
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.id = t.getId();
		this.name = t.getName();
		this.icon = t.getIcon();
		this.desc = t.getDesc();
		this.show = t.isShow();
		this.sort = t.getSort();
		return this;
	}

}
