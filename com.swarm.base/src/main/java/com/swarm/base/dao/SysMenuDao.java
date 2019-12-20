package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.SysMenu;

public interface SysMenuDao extends JpaRepository<SysMenu, Integer> {
	
	Page<SysMenu> findByNameLikeAndFlagNot(String name , int notflag , Pageable pageable);
	
	Page<SysMenu> findByFlagNot(int notflag , Pageable pageable);
	
	int countByKeyAndFlagNot(String key , int notflag);
	
	List<SysMenu> findByFlagNot(int notflag);
	
}
