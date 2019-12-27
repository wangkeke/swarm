package com.swarm.web.vo;

import java.math.BigDecimal;
import java.util.List;

import com.swarm.base.entity.BusCashback;
import com.swarm.base.service.ActivityNode;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusCashbackRes extends Res<BusCashback> {
	
	/**
	 * 商家微信用户
	 */
	private BusWechatUserRes busWechatUser;
	
	/**
	 * 需要的返现金额
	 */
	private BigDecimal reqCashback;
	
	/**
	 * 已返的返现金额
	 */
	private BigDecimal hasCashback;
	
	/**
	 * 当前返现流程状态节点
	 */
	private ActivityNode activityNode;
	
	/**
	 * 流程操作
	 */
	private List<VO> buttonActivitys;
	
	
	@Override
	public VO apply(BusCashback t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.busWechatUser = new BusWechatUserRes();
		this.busWechatUser.apply(t.getBusWechatUser());
		this.reqCashback = t.getReqCashback();
		this.hasCashback = t.getHasCashback();
		this.activityNode = t.getActivityNode();
		return this;
	}

}
