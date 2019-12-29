package com.swarm.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.swarm.base.dao.BusUserDao;

public class CustomUserDetailsService implements UserDetailsService {
	
	private boolean enableAuthorities = true;
	
	private BusUserDao userDao;
	
	public BusUserDao getUserDao() {
		return userDao;
	}
	
	public CustomUserDetailsService(BusUserDao busUserDao) {
		this.userDao = busUserDao;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.swarm.base.entity.BusUser user = userDao.findByUsername(username);
		if(user==null)
			throw new UsernameNotFoundException("用户名不存在！");
//		JdbcDaoImpl
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		if(enableAuthorities) {
			grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
		}
		return new CurrentUser(user, user.getUsername(), user.getPassword(),
				user.isEnable(), true, true,true, grantedAuthorities);
	}

}
