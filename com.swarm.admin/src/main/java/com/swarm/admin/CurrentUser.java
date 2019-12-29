package com.swarm.admin;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.swarm.base.entity.SysUser;

import lombok.Getter;

@Getter
public class CurrentUser extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5624016053561316553L;
	
	private SysUser currentUser;
	
	public CurrentUser(SysUser sysUser , String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.currentUser = sysUser;
	}
	
	public static SysUser getSysUser() {
		CurrentUser currentUser = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return currentUser.getCurrentUser();
	}
	
}
