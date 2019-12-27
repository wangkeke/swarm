package com.swarm.web.vo;

import java.math.BigDecimal;

import com.swarm.base.entity.BusCashbackRecord;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusCashbackRecordRes extends Res<BusCashbackRecord> {
	
	
	private BusCashbackRes busCashback;
	
	/**
	 * 返现来源的订单号
	 */
	private BusOrderRes busOrder;
	
	/**
	 * 商家微信用户
	 */
	private BusWechatUserRes busWechatUser;
	
	/**
	 * 返现的金额
	 */
	private BigDecimal cashbackMoney;

	@Override
	public VO apply(BusCashbackRecord t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.busOrder = new BusOrderRes();
		this.busOrder.setOrderCode(t.getBusOrder().getOrderCode());
		this.busWechatUser = new BusWechatUserRes();
		this.busWechatUser.apply(t.getBusWechatUser());
		this.cashbackMoney = t.getCashbackMoney();
		return this;
	}
	
}
