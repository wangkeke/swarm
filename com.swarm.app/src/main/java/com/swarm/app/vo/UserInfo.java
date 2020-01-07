package com.swarm.app.vo;

import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo implements VO {
	
	private VO busWechatUser;
	
	private VO busWeUserWallet;
	
	private Integer coupons;
	
}
