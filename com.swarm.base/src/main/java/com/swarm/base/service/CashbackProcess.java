package com.swarm.base.service;

import org.springframework.stereotype.Component;

/**
 * 返现流程
 * @author Administrator
 *
 */
@Component
public class CashbackProcess extends Process {

	@Override
	public void buildProcess() {
		Activity confirmed = new Activity(ActivityNode.CONFIRMED, ActivityStatus.CONFIRMED);
		Activity cancelled = new Activity(ActivityNode.CANCELLED,"取消返现", ActivityStatus.CANCELLED);
		Activity cashbacking = new Activity(ActivityNode.CASHBACKING, ActivityStatus.CASHBACKING,cancelled,confirmed);
				
		this
			.add(confirmed)
			.add(cancelled)
			.add(cashbacking);
	}

}
