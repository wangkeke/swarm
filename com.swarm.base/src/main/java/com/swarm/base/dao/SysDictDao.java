package com.swarm.base.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.SysDict;
import com.swarm.base.entity.DictType;

public interface SysDictDao extends JpaRepository<SysDict, Integer> {
	
	Page<SysDict> findByType(DictType type , Pageable pageable);
	
	List<SysDict> findByType(DictType type);
	
	List<SysDict> findByParent(SysDict parent);
	
	int countByParent(SysDict parent);
	
	int countByKey(String key);
	
	List<SysDict> findByTypeInAndParent(Collection<DictType> types , SysDict parent);
	
	List<SysDict> findByTypeInAndParentIn(Collection<DictType> types , Collection<SysDict> parents);
}
