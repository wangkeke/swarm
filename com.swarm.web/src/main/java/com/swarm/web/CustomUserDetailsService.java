package com.swarm.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	private boolean enableAuthorities = true;
	
	@Autowired
	private com.swarm.base.dao.UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.swarm.base.entity.BusUser user = userDao.findFirstByUsername(username);
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
