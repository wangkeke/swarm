package com.swarm.base.service;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Activity {
	
	/**
	 * 当前节点
	 */
	private ActivityNode node;
	
	/**
	 * 流转到下个节点的行为名称
	 */
	private String action;
	
	/**
	 * 当前节点下的预状态
	 */
	private ActivityStatus status;
	
	/**
	 * 能流转到哪些节点
	 */
	private List<Activity> nexts;
	
	public Activity(ActivityNode node , ActivityStatus status , Activity... next) {
		this.node = node;
		this.action = node.getName();
		this.status = status;
		if(next.length>0) {			
			for (Activity activity : next) {
				if(activity==null)
					continue;
				if(this.nexts == null) {
					this.nexts = new ArrayList<Activity>();
				}
				this.nexts.add(activity);
			}
		}
	}
	
	public Activity(ActivityNode node , String action , ActivityStatus status , Activity... next) {
		this.node = node;
		this.action = action;
		this.status = status;
		if(next.length>0) {			
			for (Activity activity : next) {
				if(activity==null)
					continue;
				if(this.nexts == null) {
					this.nexts = new ArrayList<Activity>();
				}
				this.nexts.add(activity);
			}
		}
	}
	
}
