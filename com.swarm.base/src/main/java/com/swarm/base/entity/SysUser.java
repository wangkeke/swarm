package com.swarm.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 管理系统用户
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class SysUser extends BaseEntity {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3375395636814581119L;

	/**
	 * 用户名
	 */
	@Column(length = 20,unique = true,nullable = false)
	private String username;
	
	@Column(length = 20,nullable = false)
	private String password;
	
	/**
	 * 身份
	 */
	private int roles;
	
	/**
	 * 是否启用
	 */
	private boolean enable = true;
	
}
