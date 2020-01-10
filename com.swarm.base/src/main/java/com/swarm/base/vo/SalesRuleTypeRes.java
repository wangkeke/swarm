package com.swarm.base.vo;

import com.swarm.base.entity.SalesRuleType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesRuleTypeRes extends Res<SalesRuleType> {
	
	private SalesRuleType type;
	
	private String name;

	@Override
	public VO apply(SalesRuleType t) {
		this.type = t;
		this.name = t.getName();
		return this;
	}
	
}
