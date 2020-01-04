package com.swarm.app.vo;

import java.util.List;

import com.swarm.base.entity.BusCategory;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusCategoryRes extends Res<BusCategory> {
	
	/**
	 * 分类名称
	 */
	private String name;
	
	/**
	 * 分类图标(100px*100px)
	 */
	private String icon;
	
	/**
	 * 父ID
	 */
	private Integer parentId;
	
	/**
	 * 父级分类ID
	 */
	private List<BusCategoryRes> sublist;
	

	@Override
	public VO apply(BusCategory t) {
		this.id = t.getId();
//		this.updateDate = t.getUpdateDate();
//		this.createDate = t.getCreateDate();
		this.name = t.getName();
		this.icon = t.getIcon();
		if(t.getParent()!=null) {			
			this.parentId = t.getParent().getId();
		}
//		this.show = t.isShow();
		return this;
	}

}
