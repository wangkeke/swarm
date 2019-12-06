package com.swarm.base.entity;

import java.net.UnknownHostException;
import java.util.UUID;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * 微信用户
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
@Log4j2
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
	private String avatarUrl;
	
	/**
	 * 生成随机昵称
	 * @return
	 */
	public static String randomNickname() {
		return generateRandom();
	}
	
}
