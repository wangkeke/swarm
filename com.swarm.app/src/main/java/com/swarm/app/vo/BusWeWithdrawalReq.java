package com.swarm.app.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.swarm.base.entity.BusWeWithdrawal;
import com.swarm.base.entity.PaymentType;
import com.swarm.base.service.ActivityNode;
import com.swarm.base.vo.CreateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusWeWithdrawalReq extends CreateReq<BusWeWithdrawal> {
	
	/**
	 * 申请的提现的金额
	 */
	@NotNull(message = "金额不能为空！")
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal money;
	
	/**
	 * 提现方式
	 */
	@NotNull(message = "请选择提现方式")
	private PaymentType paymentType;
	
	/**
	 * 银联支付方式：选择的银行
	 */
	private Integer bankDict;
	
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
	
	@Override
	public BusWeWithdrawal create() {
		BusWeWithdrawal busWeWithdrawal = new BusWeWithdrawal();
		busWeWithdrawal.setUpdateDate(new Date());
		busWeWithdrawal.setCreateDate(new Date());
		busWeWithdrawal.setActivityNode(ActivityNode.APPLY_WITHDRAWAL);
		busWeWithdrawal.setAlipayId(this.alipayId);
		busWeWithdrawal.setBankCardName(this.bankCardName);
		busWeWithdrawal.setBankCardNo(this.bankCardNo);
		busWeWithdrawal.setBankPhone(this.bankPhone);
		busWeWithdrawal.setMoney(this.money);
		busWeWithdrawal.setPaymentType(this.paymentType);
		busWeWithdrawal.setRealName(this.realName);
		busWeWithdrawal.setWechatId(this.wechatId);
		return busWeWithdrawal;
	}

}
