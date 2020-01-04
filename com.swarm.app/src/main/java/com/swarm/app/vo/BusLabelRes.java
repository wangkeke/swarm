package com.swarm.app.vo;

import com.swarm.base.entity.BusLabel;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusLabelRes extends Res<BusLabel> {
	
	private String label;
	
	private Integer sort;
	
	@Override
	public VO apply(BusLabel t) {
		this.label = t.getLabel();
		this.sort = t.getSort();
		return this;
	}

}
