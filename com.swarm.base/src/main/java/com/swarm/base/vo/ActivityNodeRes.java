package com.swarm.base.vo;

import com.swarm.base.service.ActivityNode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityNodeRes extends Res<ActivityNode> {
	
	private ActivityNode activityNode;
	
	private String name;

	@Override
	public VO apply(ActivityNode t) {
		this.activityNode = t;
		this.name = t.getName();
		return this;
	};
	
}
