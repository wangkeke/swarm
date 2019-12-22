package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.BusCategory;
import com.swarm.base.vo.CreateReq;
import com.swarm.web.CurrentUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusCategoryReq extends CreateReq<BusCategory> {

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
	@NotNull(message = "请选择是否显示！")
	private Boolean show;
	
	/**
	 * 父级分类ID
	 */
	private Integer parentId;
	
	@Override
	public BusCategory create() {
		BusCategory busCategory = new BusCategory();
		busCategory.setUpdateDate(new Date());
		busCategory.setCreateDate(new Date());
		busCategory.setName(this.name);
		busCategory.setShow(this.show);
		busCategory.setIcon(this.icon);
		busCategory.setBusUserId(CurrentUser.getBusUserId());
		return busCategory;
	}

}
