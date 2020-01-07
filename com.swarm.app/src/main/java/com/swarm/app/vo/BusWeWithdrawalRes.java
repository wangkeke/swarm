package com.swarm.app.vo;

import java.math.BigDecimal;
import java.util.List;

import com.swarm.base.entity.BusWeWithdrawal;
import com.swarm.base.entity.PaymentType;
import com.swarm.base.service.ActivityNode;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusWeWithdrawalRes extends Res<BusWeWithdrawal> {
	
	/**
	 * 申请提现的微信用户
	 */
	private BusWechatUserRes busWechatUser;
	
	/**
	 * 申请的提现的金额
	 */
	private BigDecimal money;
	
	/**
	 * 提现方式
	 */
	private PaymentType paymentType;
	
	/**
	 * 银联支付方式：选择的银行
	 */
	private SysDictRes bankDict;
	
	/**
	 * 银联支付方式：银行卡号
	 */
	private String bankCardNo;
	
	/**
	 * 银联支付方式：持卡人姓名
	 */
	private String bankCardName;
	
	/**
	 * 银联支付方式：银行预留手机号
	 */
	private String bankPhone;
	
	/**
	 * 微信支付方式：微信号
	 */
	private String wechatId;
	
	/**
	 * 支付宝账号
	 */
	private String alipayId;
	
	/**
	 * 微信支付方式：真实姓名
	 */
	private String realName;
	
	/**
	 * 当前订单所在流程节点
	 */
	private ActivityNode activityNode;
	
	/**
	 * 当前订单所在流程节点名称
	 */
	private String activityNodeName;
	
	/**
	 * 流程操作
	 */
	private List<VO> buttonActivitys;
	
	/**
	 * 原因，主要针对失败的流程节点需要阐述的原因
	 */
	private String comment;
	
	/**
	 * 手续费
	 */
	private BigDecimal handlingFee;
	
	/**
	 * 实收金额
	 */
	private BigDecimal receivedMoney;
	
	@Override
	public VO apply(BusWeWithdrawal t) {
		this.id = t.getId();
		this.createDate = t.getCreateDate();
		this.updateDate = t.getUpdateDate();
//		this.busWechatUser = new BusWechatUserRes();
//		this.busWechatUser.apply(t.getBusWechatUser());
		this.money = t.getMoney();
		this.paymentType = t.getPaymentType();
		if(this.paymentType==PaymentType.UNIONPAY) {			
			this.bankDict = new SysDictRes();
			this.bankDict.setValue(t.getBankDict().getValue());
		}
//		this.bankDict.apply(t.getBankDict());
		this.bankCardNo = t.getBankCardNo();
		this.bankCardName = t.getBankCardName();
		this.bankPhone = t.getBankPhone();
		this.wechatId = t.getWechatId();
		this.alipayId = t.getAlipayId();
		this.realName = t.getRealName();
		this.comment = t.getComment();
		this.handlingFee = t.getHandlingFee();
		this.receivedMoney = t.getReceivedMoney();
		this.activityNode = t.getActivityNode();
		this.activityNodeName = t.getActivityNode().getName();
		return this;
	}

}
