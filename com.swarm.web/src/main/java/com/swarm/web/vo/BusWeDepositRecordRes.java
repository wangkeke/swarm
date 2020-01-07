package com.swarm.web.vo;

import java.math.BigDecimal;

import com.swarm.base.entity.BusWeDepositRecord;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusWeDepositRecordRes extends Res<BusWeDepositRecord> {
	
	
	/**
	 * 订单号
	 */
	private String orderCode;
	
	private VO busWechatUser;
	
	/**
	 * 入金金额
	 */
	private BigDecimal money;

	@Override
	public VO apply(BusWeDepositRecord t) {
		this.busWechatUser = new BusWechatUserRes().apply(t.getBusWechatUser());
		this.money = t.getMoney();
		this.id = t.getId();
		this.orderCode = t.getOrderCode();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		return this;
	}
}
