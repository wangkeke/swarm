package com.swarm.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * 小程序配置信息
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusMnprogram extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4618698208148174898L;

	/**
	 * 小程序名称
	 */
	@Column(length = 20,nullable = false)
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
	 * 上传证书上传路径(apiclient_cert.pem)
	 */
	private String apiclientcertPath;
	
	/**
	 * 上传证书(apiclient_key.pem)
	 */
	private String apiclientkeyPath;
	
	/**
	 * 商家用户ID，分库分表字段
	 */
	private Integer busUserId;
	
}
