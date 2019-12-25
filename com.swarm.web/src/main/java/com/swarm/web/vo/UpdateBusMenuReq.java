package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.BusMenu;
import com.swarm.base.vo.UpdateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBusMenuReq extends UpdateReq<BusMenu> {
	
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
	 * 是否展示
	 */
	@NotNull(message = "请选择是否展示！")
	private Boolean show;
	
	/**
	 * 描述
	 */
	private String desc;
	
	/**
	 * 排序，从小到大
	 */
	@NotNull(message = "请输入排序大小！")
	private Integer sort;
	
	@Override
	public void update(BusMenu t) {
		t.setUpdateDate(new Date());
		t.setName(this.name);
		t.setIcon(this.icon);
		t.setShow(this.show);
		t.setSort(this.sort);
		t.setDesc(this.desc);
	}

}
