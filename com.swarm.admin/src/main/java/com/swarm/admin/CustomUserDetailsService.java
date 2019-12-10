package com.swarm.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.swarm.base.dao.SysUserDao;
import com.swarm.base.entity.RoleIdentity;
import com.swarm.base.entity.SysUser;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	private boolean enableAuthorities = true;
	
	@Autowired
	private SysUserDao sysUserDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = sysUserDao.findFirstByUsername(username);
		if(sysUser==null)
			throw new UsernameNotFoundException("用户名不存在！");
//		JdbcDaoImpl
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		if(enableAuthorities) {
			if((sysUser.getRoles() & RoleIdentity.USER_ROLE.getRole()) > 0) {				
				grantedAuthorities.add(new SimpleGrantedAuthority(RoleIdentity.USER_ROLE.getName()));
			}
			if((sysUser.getRoles() & RoleIdentity.ADMIN_ROLE.getRole()) > 0) {				
				grantedAuthorities.add(new SimpleGrantedAuthority(RoleIdentity.ADMIN_ROLE.getName()));
			}
			if((sysUser.getRoles() & RoleIdentity.SYSTEM_ROLE.getRole()) > 0) {				
				grantedAuthorities.add(new SimpleGrantedAuthority(RoleIdentity.SYSTEM_ROLE.getName()));
			}
		}
		return new CurrentUser(sysUser, sysUser.getUsername(), sysUser.getPassword(),
				sysUser.isEnable(), true, true,true, grantedAuthorities);
	}

}
