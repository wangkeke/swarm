package com.swarm.web.vo;

import com.swarm.base.entity.BusRecord;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusRecordRes extends Res<BusRecord> {
	
	/**
	 * 订单
	 */
	private VO busOrder;
	
	/**
	 * 记录的订单流程节点
	 */
	private VO activity;
	
	/**
	 * 备注说明/原因，主要针对失败的流程节点需要阐述的原因
	 */
	private String comment;
	
	/**
	 * 商家操作执行该节点（与属性busWechatUser二者择一，不可能同时出现）
	 */
	private BusUserRes busUser;
	
	
	/**
	 * 微信用户操作执行该节点（与属性busUser二者择一，不可能同时出现）
	 */
	private BusWechatUserRes busWechatUser;


	@Override
	public VO apply(BusRecord t) {
		if(t.getBusUser()!=null) {
			this.busUser = new BusUserRes();
			this.busUser.setUsername(t.getBusUser().getUsername());
		}
		if(t.getBusWechatUser()!=null) {
			this.busWechatUser = new BusWechatUserRes();
			this.busWechatUser.setOpenId(t.getBusWechatUser().getOpenId());
			this.busWechatUser.setNickname(t.getBusWechatUser().getNickname());
		}
		this.comment = t.getComment();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.id = t.getId();
		return this;
	}
	
}
