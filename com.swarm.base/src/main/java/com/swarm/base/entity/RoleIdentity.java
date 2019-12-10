package com.swarm.base.entity;

/**
 * 角色身份
 * @author Administrator
 *
 */

public enum RoleIdentity {
	
	SYSTEM_ROLE(7,"SYSTEM"),ADMIN_ROLE(3,"ADMIN"),USER_ROLE(1,"USER");
	
	private int role;
	
	private String name;
	
	RoleIdentity(int role , String name){
		this.role = role;
		this.name = name;
	}
	
	public int getRole() {
		return role;
	}
	
	public String getName() {
		return name;
	}
	
	public static RoleIdentity getRoleIdentity(int role) {
		RoleIdentity roleIdentity = null;
		switch (role) {
		case 1:
			roleIdentity = USER_ROLE;
			break;
		case 3:
			roleIdentity = ADMIN_ROLE;
			break;
		case 7:
			roleIdentity = SYSTEM_ROLE;
			break;
		}
		return roleIdentity;
	}
	
}
