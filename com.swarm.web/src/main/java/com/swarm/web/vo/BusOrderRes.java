package com.swarm.web.vo;

import java.math.BigDecimal;
import java.util.List;

import com.swarm.base.entity.BusOrder;
import com.swarm.base.service.ActivityNode;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusOrderRes extends Res<BusOrder> {
	
	/**
	 * 订单号
	 */
	private String orderCode;
	
	/**
	 * 微信用户
	 */
	private VO busWechatUser;
	
	/**
	 * 是否自取
	 */
	private Boolean selfpick;
	
	/**
	 * 应收总金额
	 */
	private BigDecimal amount;
		
	
	/**
	 * 实收总金额
	 */
	private BigDecimal realAmount;
	
	/**
	 * 当前订单所在流程节点
	 */
	private ActivityNode activityNode;
	
	/**
	 * 流程操作
	 */
	private List<VO> buttonActivitys;
	
	/**
	 * 原因，主要针对失败的流程节点需要阐述的原因
	 */
	private String comment;
	
	/**
	 * 订单支付中使用的优惠券
	 */
	private List<VO> orderCoupons;
	
	/**
	 * 订单相关产品
	 */
	private List<VO> orderProducts;
	
	/**
	 * 流程处理记录
	 */
	private List<VO> records;
	
	/**
	 * 自提信息
	 */
	private VO pickcode;
	
	
	@Override
	public VO apply(BusOrder t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.orderCode = t.getOrderCode();
		this.busWechatUser = new BusWechatUserRes().apply(t.getBusWechatUser());
		this.selfpick = t.isSelfpick();
		this.amount = t.getAmount();
		this.realAmount = t.getRealAmount();
		this.activityNode = t.getActivityNode();
		this.comment = t.getComment();
		return this;
	}

}
