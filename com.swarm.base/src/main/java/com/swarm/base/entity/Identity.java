package com.swarm.base.entity;

/**
 * 角色身份
 * @author Administrator
 *
 */

public enum Identity {
	
	SYSTEM_ID(7,"SYSTEM"),ADMIN_ID(3,"ADMIN"),USER_ID(1,"USER");
	
	private int id;
	
	private String name;
	
	Identity(int role , String name){
		this.id = role;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
}
