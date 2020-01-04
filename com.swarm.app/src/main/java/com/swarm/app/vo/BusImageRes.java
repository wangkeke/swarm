package com.swarm.app.vo;

import com.swarm.base.entity.BusImage;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusImageRes extends Res<BusImage> {
	
	
	private VO busImageType;
	
	/**
	 * 图片路径
	 */
	private String path;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	@Override
	public VO apply(BusImage t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.path = t.getPath();
		this.sort = t.getSort();
		this.busImageType = new BusImageTypeRes().apply(t.getBusImageType());
		return this;
	}

}
