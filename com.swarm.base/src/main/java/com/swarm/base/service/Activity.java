package com.swarm.base.service;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class Activity {
	
	public static final int WECHAT_USER = 0;
	
	public static final int BUS_USER = 1;
	
	/**
	 * 当前节点
	 */
	private ActivityNode node;
	
	/**
	 * 操作用户
	 */
	private Integer operator;
	
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
	
	public Activity(int operator , ActivityNode node , ActivityStatus status , Activity... next) {
		this.operator = operator;
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
	
	public Activity(int operator , ActivityNode node , String action , ActivityStatus status , Activity... next) {
		this.operator = operator;
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
	
	public void addNext(Activity next) {
		if(this.nexts == null) {
			this.nexts = new ArrayList<Activity>();
		}
		this.nexts.add(next);
	}
	
}
