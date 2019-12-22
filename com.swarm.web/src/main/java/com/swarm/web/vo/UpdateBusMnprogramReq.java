package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.BusMnprogram;
import com.swarm.base.vo.UpdateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBusMnprogramReq extends UpdateReq<BusMnprogram> {
	
	@NotNull(message = "ID不能为空！")
	private Integer id;
	
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
	public void update(BusMnprogram m) {
		m.setUpdateDate(new Date());
		m.setMpname(this.mpname);
		m.setApiclientcertPath(this.apiclientcertPath);
		m.setApiclientkeyPath(this.apiclientkeyPath);
		m.setAppID(this.AppID);
		m.setAppSecret(this.AppSecret);
		m.setPaySecretKey(this.paySecretKey);
	}
	
}
