package com.swarm.base.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信小程序API接口调用维护信息
 * @author Administrator
 *
 */
@Getter
@Setter
@Entity
public class BusWeApiInfo extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -251184976223927083L;

	/**
	 * 访问微信API token
	 */
	private String access_token;
	
	/**
	 * access_token的过期时长
	 */
	private int expires_in;
	
	/**
	 * access_token最近一次抓取时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date takeTime;
	
	/**
	 * 小程序二维码
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String mnqrcode;
	
	/**
	 * 支付二维码
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String payqrcode;
	
	@OneToOne
	@JoinColumn
	private BusMnprogram busMnprogram;
	
	/**
	 * 商家用户ID，分库分表字段
	 */
	private Integer busUserId;
	
}
