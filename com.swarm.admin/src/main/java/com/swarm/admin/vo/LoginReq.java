package com.swarm.admin.vo;

import javax.validation.constraints.NotBlank;

import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq implements VO {
	
	@NotBlank(message = "用户名不能为空！")
	private String username;
	
	@NotBlank(message = "密码不能为空！")
	private String password;
	
}
