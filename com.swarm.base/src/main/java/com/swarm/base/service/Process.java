package com.swarm.base.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 活动流程描述
 * @author Administrator
 *
 */
public abstract class Process {
	
	private Map<ActivityNode, Activity> activityMap = new HashMap<ActivityNode, Activity>();
	
	protected Process add(Activity activity) {
		activityMap.put(activity.getNode(), activity);
		return this;
	}
	
	public Activity get(ActivityNode node) {
		return activityMap.get(node);
	}
	
	public abstract void buildProcess();
	
}
