package com.swarm.web;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class CurrentUser extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5624016053561316553L;
	
	private com.swarm.base.entity.BusUser currentUser;
	
	public CurrentUser(com.swarm.base.entity.BusUser currentUser , String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.currentUser = currentUser;
	}

}
