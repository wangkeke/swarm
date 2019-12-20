package com.swarm.admin.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.swarm.base.entity.BusUser;
import com.swarm.base.vo.CreateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusUserReq extends CreateReq<BusUser> {
	
	/**
	 * 用户名，默认手机号
	 */
	@NotBlank(message = "用户名不能为空！")
	private String username;
	
	@NotBlank(message = "密码不能为空！")
	@Size(min = 6,max = 20,message = "请确认密码长度在6~20位之间！")
	private String password;
	
	/**
	 * 商家描述
	 */
	private String desc;
	
	/**
	 * 是否启用
	 */
	@NotNull(message = "请选择启用状态！")
	private Boolean enable;
	
	@Override
	public BusUser create() {
		BusUser busUser = new BusUser();
		busUser.setCreateDate(new Date());
		busUser.setUpdateDate(new Date());
		busUser.setUsername(this.username);
		busUser.setPassword(this.password);
		busUser.setDesc(this.desc);
		busUser.setEnable(this.enable);
		return busUser;
	}

}
