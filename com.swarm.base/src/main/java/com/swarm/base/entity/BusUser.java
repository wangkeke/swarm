package com.swarm.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 商家用户
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusUser extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5450646225225735577L;

	/**
	 * 用户名，默认手机号
	 */
	@Column(length = 20,unique = true,nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	/**
	 * 商家描述
	 */
	@Column(name = "`desc`")
	private String desc;
	
	/**
	 * 是否启用
	 */
	private boolean enable = true;
	
	/**
	 * 密钥key值
	 */
	private String key;
	
}
