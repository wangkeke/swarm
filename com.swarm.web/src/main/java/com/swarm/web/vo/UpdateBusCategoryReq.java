package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.BusCategory;
import com.swarm.base.vo.UpdateReq;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class UpdateBusCategoryReq extends UpdateReq<BusCategory> {
	
	@NotNull(message = "ID不能为空！")
	private Integer id;
	
	/**
	 * 分类名称
	 */
	@NotBlank(message = "名称不能为空！")
	private String name;
	
	/**
	 * 分类图标(100px*100px)
	 */
	private String icon;
	
	/**
	 * 是否显示，默认为true
	 */
	@NotBlank(message = "请选择是否显示！")
	private Boolean show;
	
	@Override
	public void update(BusCategory t) {
		t.setUpdateDate(new Date());
		t.setName(this.name);
		t.setIcon(this.icon);
		t.setShow(this.show);
	}

}
