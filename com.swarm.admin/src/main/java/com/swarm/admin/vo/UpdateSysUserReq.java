package com.swarm.admin.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.Identity;
import com.swarm.base.entity.SysUser;
import com.swarm.base.vo.UpdateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSysUserReq extends UpdateReq<SysUser> {
	
	@NotNull(message = "id不能为空！")
	private Integer id;
	
	@NotBlank(message = "用户名不能为空！")
	private String username;
	
	@NotNull(message = "角色身份不能为空！")
	private Identity identity;
	
	@NotNull(message = "是否启用不能为空！")
	private Boolean enable;	

	@Override
	public void update(SysUser t) {
		t.setUsername(this.username);
		t.setIdentity(this.identity);
		t.setEnable(this.enable);
		t.setUpdateDate(new Date());
	}

	
}
