package com.swarm.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.admin.vo.SysDictReq;
import com.swarm.admin.vo.SysDictRes;
import com.swarm.admin.vo.UpdateSysDictReq;
import com.swarm.base.dao.SysDictDao;
import com.swarm.base.entity.SysDict;
import com.swarm.base.entity.DictType;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;

@Service
@Transactional(readOnly = true)
public class SysDictService {
	
	private SysDictDao sysDictDao;
	
	public Page<VO> page(DictType type , Paging paging){
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("createDate")));
		Page<SysDict> page = null;
		if(type!=null) {
			page =  sysDictDao.findByType(type, pageable);
		}else {
			page = sysDictDao.findAll(pageable);
		}
		return page.map(new SysDictRes());
	}
	
	
	private SysDict getSysDict(Integer id) {
		if(id == null) {
			throw new ServiceException("ID不能为空！");
		}
		Optional<SysDict> optional = sysDictDao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		return optional.get();
	}
	
	
	public List<VO> getDictsByParent(Integer parentId){
		List<VO> list = new ArrayList<VO>();
		if(parentId==null)
			return list;
		Optional<SysDict> optional = sysDictDao.findById(parentId);
		if(!optional.isPresent())
			return list;
		SysDict parent = optional.get();
		List<SysDict> sysDicts = sysDictDao.findByParent(parent);
		for (SysDict dict : sysDicts) {
			list.add(new SysDictRes().apply(dict));
		}
		return list;
	}
	
	public List<VO> getDictsByType(DictType type){
		List<VO> list = new ArrayList<VO>();
		if(type==null)
			return list;
		List<SysDict> sysDicts = sysDictDao.findByType(type);
		for (SysDict dict : sysDicts) {
			list.add(new SysDictRes().apply(dict));
		}
		return list;
	}
	
	public VO get(Integer id) {
		return new SysDictRes().apply(getSysDict(id));
	}
	
	
	public boolean validKey(String key) {
		boolean b = false;
		if(StringUtils.isBlank(key)) {
			return b;
		}
		int count = sysDictDao .countByKey(key);
		if(count==0) {
			b = true;
		}
		return b;
	}
	
	
	@Transactional
	public Integer save(SysDictReq req) {
		SysDict sysDict = req.create();
		if(!validKey(sysDict.getKey())) {
			throw new ServiceException("Key已存在！");
		}
		if(req.getParentId()!=null) {
			try {				
				SysDict parent = getSysDict(req.getParentId());
				sysDict.setParent(parent);
			} catch (ServiceException e) {
				throw new ServiceException("父ID不存在！");
			}
		}
		sysDictDao.save(sysDict);
		return sysDict.getId();
	}
	
	@Transactional
	public void update(UpdateSysDictReq req) {
		SysDict sysDict = getSysDict(req.getId());
		if(!sysDict.getKey().equals(req.getKey())) {
			if(!validKey(req.getKey())) {
				throw new ServiceException("Key已存在！");
			}
		}
		if(req.getParentId()==null) {
			sysDict.setParent(null);
		}else {
			if(sysDict.getParent()==null || sysDict.getParent().getId()!=req.getParentId()) {				
				try {				
					SysDict parent = getSysDict(req.getParentId());
					sysDict.setParent(parent);
				} catch (ServiceException e) {
					throw new ServiceException("父ID不存在！");
				}
			}
		}
		req.update(sysDict);
		sysDictDao.save(sysDict);
	}
	
	@Transactional
	public void delete(Integer id) {
		SysDict sysDict = getSysDict(id);
		int count = sysDictDao.countByParent(sysDict);
		if(count>0) {
			throw new ServiceException("请先删除子级关系！");
		}
		sysDictDao.delete(sysDict);
	}
	
}
