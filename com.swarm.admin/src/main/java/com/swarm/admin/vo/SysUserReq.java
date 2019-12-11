package com.swarm.admin.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.swarm.base.entity.RoleIdentity;
import com.swarm.base.entity.SysUser;
import com.swarm.base.vo.Req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUserReq extends Req<SysUser> {
	
	private Integer id;
	
	@NotBlank
	private String username;
	
	@NotBlank
	@Size(min = 6,max = 20)
	private String password;
	
	@NotNull
	private RoleIdentity roleIdentity;

	@Override
	public SysUser build() {
		SysUser sysUser = new SysUser();
		sysUser.setCreateDate(new Date());
		sysUser.setUpdateDate(new Date());
		sysUser.setEnable(true);
		sysUser.setPassword(this.password);
		sysUser.setRoleIdentity(roleIdentity);
		sysUser.setUsername(this.username);
		return sysUser;
	}
	
}
