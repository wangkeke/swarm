package com.swarm.web.vo;

import com.swarm.base.entity.BusMnprogram;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusMnprogramRes extends Res<BusMnprogram> {
	
	/**
	 * 小程序名称
	 */
	private String mpname;
	
	/**
	 * 小程序申请ID
	 */
	private String AppID;
	
	/**
	 * 小程序密钥
	 */
	private String AppSecret;
	
	/**
	 * 支付密钥
	 */
	private String paySecretKey;
	
	/**
	 * 证书上传路径(apiclient_cert.pem)
	 */
	private String apiclientcertPath;
	
	/**
	 * 上传证书(apiclient_key.pem)
	 */
	private String apiclientkeyPath;
	
	@Override
	public VO apply(BusMnprogram t) {
		this.mpname = t.getMpname();
		this.AppID = t.getAppID();
		this.AppSecret = t.getAppSecret();
		this.paySecretKey = t.getPaySecretKey();
		this.apiclientcertPath = t.getApiclientcertPath();
		this.apiclientkeyPath = t.getApiclientkeyPath();
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		return this;
	}

}
