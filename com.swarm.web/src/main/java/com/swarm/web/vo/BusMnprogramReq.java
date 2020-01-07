package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.swarm.base.entity.BusMnprogram;
import com.swarm.base.vo.CreateReq;
import com.swarm.web.CurrentUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusMnprogramReq extends CreateReq<BusMnprogram> {
	
	/**
	 * 小程序名称
	 */
	@NotBlank(message = "小程序名称不能为空！")
	private String mpname;
	
	/**
	 * 小程序申请ID
	 */
	@NotBlank(message = "AppID不能为空！")
	private String AppID;
	
	/**
	 * 小程序密钥
	 */
	@NotBlank(message = "AppSecret不能为空！")
	private String AppSecret;
	
	/**
	 * 支付商户号
	 */
	@NotBlank(message = "支付商户号不能为空！")
	private String payBusNumber;
	
	/**
	 * 支付密钥
	 */
	@NotBlank(message = "支付密钥不能为空！")
	private String paySecretKey;
	
	/**
	 * 证书上传路径(apiclient_cert.pem)
	 */
	@NotBlank(message = "请上传证书！")
	private String apiclientcertPath;
	
	/**
	 * 上传证书(apiclient_key.pem)
	 */
	@NotBlank(message = "请上传证书Key！")
	private String apiclientkeyPath;
	
	@Override
	public BusMnprogram create() {
		BusMnprogram mnprogram = new BusMnprogram();
		mnprogram.setUpdateDate(new Date());
		mnprogram.setCreateDate(new Date());
		mnprogram.setMpname(this.mpname);
		mnprogram.setAppID(this.AppID);
		mnprogram.setAppSecret(this.AppSecret);
		mnprogram.setPayBusNumber(this.payBusNumber);
		mnprogram.setPaySecretKey(this.paySecretKey);
		mnprogram.setApiclientcertPath(this.apiclientcertPath);
		mnprogram.setApiclientkeyPath(this.apiclientkeyPath);
		mnprogram.setBusUserId(CurrentUser.getBusUserId());
		return mnprogram;
	}

}
