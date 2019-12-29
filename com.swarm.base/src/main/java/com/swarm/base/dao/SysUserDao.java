package com.swarm.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.SysUser;

public interface SysUserDao extends JpaRepository<SysUser, Integer> {
	
	Page<SysUser> findByUsernameLike(String username , Pageable pageable);
	
	SysUser findByUsername(String username);
	
	int countByUsername(String username);
	
}
