package com.swarm.base.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
	private Integer expires_in;
	
	
	/**
	 * 获取access_token错误码
	 * <br>
	 * -1:	系统繁忙，此时请开发者稍候再试<br>
	 * 0:	请求成功<br>
	 * 40001:	AppSecret 错误或者 AppSecret 不属于这个小程序，请开发者确认 AppSecret 的正确性<br>
	 * 40002:	请确保 grant_type 字段值为 client_credential<br>
	 * 40013	不合法的 AppID，请开发者检查 AppID 的正确性，避免异常字符，注意大小写<br>
	 */
	private Integer errcode;
	
	/**
	 * access_token最近一次抓取时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date takeTime;
	
	/**
	 * 小程序二维码路径
	 */
	private String mnqrcode;
	
	/**
	 * 小程序二维码素材路径
	 */
	private String mnqrcodeMater;
	
	/**
	 * 支付二维码路径
	 */
	private String payqrcode;
	
	/**
	 * 支付二维码素材路径
	 */
	private String payqrcodeMater;
	
	@OneToOne
	@JoinColumn
	private BusMnprogram busMnprogram;
	
	/**
	 * 商家用户ID，分库分表字段
	 */
	private Integer busUserId;
	
}
