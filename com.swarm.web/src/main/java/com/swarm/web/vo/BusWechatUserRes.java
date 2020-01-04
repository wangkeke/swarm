package com.swarm.web.vo;

import com.swarm.base.entity.BusWechatUser;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusWechatUserRes extends Res<BusWechatUser> {
	
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
	private Integer gender;
	
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
	 * 该用户自己分享的小程序二维码路径
	 */
	private String usermnqrcode;
	
	/**
	 * 父级用户
	 */
	private BusWechatUserRes parent;
	
	
	@Override
	public VO apply(BusWechatUser t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.openId = t.getOpenId();
		this.nickname = t.getNickname();
		this.unionId = t.getUnionId();
//		this.sessionKey = t.getSessionKey();
		this.gender = t.getGender();
		this.province = t.getProvince();
		this.city = t.getCity();
		this.country = t.getCountry();
		this.portrait = t.getPortrait();
		this.usermnqrcode = t.getUsermnqrcode();
		return this;
	}

}
