package com.swarm.admin.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.swarm.base.entity.RoleIdentity;
import com.swarm.base.entity.SysUser;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUserRes extends Res<SysUser> {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 身份
	 */
	private String role;
	
	/**
	 * 是否启用
	 */
	private Boolean enable;
	
	/**
	 * 更新日期
	 */
	@JsonFormat(shape = Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	/**
	 * 创建日期
	 */
	@JsonFormat(shape = Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createDate;
	

	@Override
	public VO apply(SysUser t) {
		this.id = t.getId();
		this.username = t.getUsername();
		RoleIdentity roleIdentity = RoleIdentity.getRoleIdentity(t.getRoles());
		if(roleIdentity!=null) {			
			this.role = roleIdentity.getName();
		}
		this.enable = t.isEnable();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		return this;
	}

}
