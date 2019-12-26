package com.swarm.web.vo;

import com.swarm.base.entity.BusUser;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusUserRes extends Res<BusUser> {
	
	/**
	 * 用户名，默认手机号
	 */
	private String username;
	
	/**
	 * 商家描述
	 */
	private String desc;
	
	/**
	 * 是否启用
	 */
	private Boolean enable;
	
	@Override
	public VO apply(BusUser t) {
		this.id = t.getId();
		this.username = t.getUsername();
		this.desc = t.getDesc();
		this.enable = t.isEnable();
		this.createDate = t.getCreateDate();
		this.updateDate = t.getUpdateDate();
		return this;
	}

}
