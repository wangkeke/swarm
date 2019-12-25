package com.swarm.base.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信用户
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusWechatUser extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4817281064212487634L;
	
	/**
	 * 微信用户openId
	 */
	private String openId;
	
	/**
	 * 微信昵称
	 */
	private String nickname;
	

	private String unionId;
	
	private String sessionKey;
	
	/**
	 * 微信用户性别，0：未知、1：男、2：女
	 */
	private int gender;
	
	/**
	 *  微信用户所在省份
	 */
	private String province;
	
	/**
	 * 微信用户所在城市
	 */
	private String city;
	
	/**
	 * 微信用户所在国家
	 */
	private String country;
	
	/**
	 * 微信用户头像地址
	 */
	private String portrait;
	
	
	/**
	 * 父级用户，即：老用户分享拉入的用户
	 */
	@ManyToOne
	@JoinColumn
	private BusWechatUser parentUser;
	
	/**
	 * 商家用户ID，分表分库字段
	 */
	private Integer busUserId;
	
	/**
	 * 生成随机昵称
	 * @return
	 */
	public static String randomNickname() {
		return "用户_" + generateRandom();
	}
	
}
