package com.swarm.admin.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.swarm.base.entity.Identity;
import com.swarm.base.entity.SysUser;
import com.swarm.base.vo.CreateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUserReq extends CreateReq<SysUser> {
	
	private Integer id;
	
	@NotBlank(message = "用户名不能为空！")
	private String username;
	
	@NotBlank(message = "密码不能为空！")
	@Size(min = 6,max = 20,message = "请确认密码长度在6~20位之间！")
	private String password;
	
	@NotNull(message = "角色身份不能为空！")
	private Identity identity;
	
	@NotNull(message = "是否启用不能为空！")
	private Boolean enable;

	@Override
	public SysUser create() {
		SysUser sysUser = new SysUser();
		sysUser.setCreateDate(new Date());
		sysUser.setUpdateDate(new Date());
		sysUser.setEnable(this.enable==null?true:this.enable);
		sysUser.setPassword(this.password);
		sysUser.setIdentity(identity);
		sysUser.setUsername(this.username);
		return sysUser;
	}
	
}
