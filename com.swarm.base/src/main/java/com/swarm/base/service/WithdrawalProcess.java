package com.swarm.base.service;

import org.springframework.stereotype.Component;

/**
 * 微信用户提现流程
 * @author Administrator
 *
 */
@Component
public class WithdrawalProcess extends Process {

	@Override
	public void buildProcess() {
		Activity confirmed = new Activity(ActivityNode.CONFIRMED, ActivityStatus.CONFIRMED);
		Activity refuse_withdrawal = new Activity(ActivityNode.REFUSE_WITHDRAWAL, ActivityStatus.REFUSE_WITHDRAWAL);
		Activity apply_withdrawal = new Activity(ActivityNode.APPLY_WITHDRAWAL, ActivityStatus.PENDING_WITHDRAWAL, confirmed,refuse_withdrawal);
				
		this
			.add(confirmed)
			.add(refuse_withdrawal)
			.add(apply_withdrawal);
	}

}
