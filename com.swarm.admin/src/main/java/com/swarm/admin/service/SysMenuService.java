package com.swarm.admin.service;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.admin.vo.SysMenuReq;
import com.swarm.admin.vo.SysMenuRes;
import com.swarm.admin.vo.UpdateSysMenuReq;
import com.swarm.base.dao.SysMenuDao;
import com.swarm.base.entity.SysMenu;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;

@Service
@Transactional(readOnly = true)
public class SysMenuService {
	
	@Autowired
	private SysMenuDao sysMenuDao;
	
	
	public Page<VO> page(String name , Paging paging) {
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("createDate")));
		Page<SysMenu> page = null;
		if(StringUtils.isNotBlank(name)) {
			page = sysMenuDao.findByNameLikeAndFlagNot("%"+name+"%", -1, pageable);
		}else {
			page = sysMenuDao.findByFlagNot(-1, pageable);
		}
		return page.map(new SysMenuRes());
	}
	
	private SysMenu getSysMenu(Integer id) {
		if(id==null)
			throw new ServiceException("ID不能为空！");
		Optional<SysMenu> optional = sysMenuDao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("不存在的ID！");
		}
		return optional.get();
	}
	
	public VO get(Integer id) {
		return new SysMenuRes().apply(getSysMenu(id));
	}
	
	public boolean validKey(String key) {
		boolean b = false;
		if(StringUtils.isBlank(key)) {
			return b;
		}
		int count = sysMenuDao.countByKeyAndFlagNot(key, -1);
		if(count==0) {
			b = true;
		}
		return b;
	}
	
	@Transactional
	public Integer save(SysMenuReq req) {
		if(!validKey(req.getKey())) {
			throw new ServiceException("已存在的key！");
		}
		return sysMenuDao.save(req.create()).getId();
	}
	
	@Transactional
	public void update(UpdateSysMenuReq req) {
		SysMenu sysMenu = getSysMenu(req.getId());
		if(!sysMenu.getKey().equals(req.getKey())) {
			if(!validKey(req.getKey())) {
				throw new ServiceException("已存在的key！");
			}
		}
		req.update(sysMenu);
		sysMenuDao.save(sysMenu);
	}
	
	@Transactional
	public void delete(Integer id) {
		SysMenu sysMenu = getSysMenu(id);
		sysMenu.setFlag(-1);
		sysMenu.setUpdateDate(new Date());
		sysMenuDao.save(sysMenu);
	}
	
}
