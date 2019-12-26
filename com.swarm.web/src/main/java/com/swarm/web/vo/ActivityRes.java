package com.swarm.web.vo;

import com.swarm.base.service.Activity;
import com.swarm.base.service.ActivityNode;
import com.swarm.base.service.ActivityStatus;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivityRes extends Res<Activity> {
	
	/**
	 * 当前节点
	 */
	private ActivityNode node;
	
	/**
	 * 节点名称
	 */
	private String nodeName;
	
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
	 * 当前节点下的预状态名称
	 */
	private String statusName;
	
	
	@Override
	public VO apply(Activity t) {
		this.node = t.getNode();
		this.nodeName = this.node.getName();
		this.action = t.getAction();
		this.status = t.getStatus();
		this.statusName = this.status.getName();
		return this;
	}

}
