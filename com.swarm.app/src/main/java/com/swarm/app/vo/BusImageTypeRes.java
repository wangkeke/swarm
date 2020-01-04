package com.swarm.app.vo;

import com.swarm.base.entity.BusImageType;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusImageTypeRes extends Res<BusImageType> {
	
	private String name;
	
	private BusImageType busImageType;
	
	@Override 
	public VO apply(BusImageType t) {
		this.name = t.getName();
		this.busImageType = t;
		return this;
	}

}
