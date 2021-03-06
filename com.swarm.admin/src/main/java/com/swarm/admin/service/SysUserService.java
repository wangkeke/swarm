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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.admin.vo.SysUserReq;
import com.swarm.admin.vo.SysUserRes;
import com.swarm.admin.vo.UpdateSysUserReq;
import com.swarm.base.dao.SysUserDao;
import com.swarm.base.entity.SysUser;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;

@Service
@Transactional(readOnly = true)
public class SysUserService {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private SysUserDao sysUserDao;
	
	
	public Page<VO> page(String username , Paging paging){
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("createDate")));
		Page<SysUser> page = null;
		if(StringUtils.isNotBlank(username)) {
			page = sysUserDao.findByUsernameLike("%"+username+"%", pageable);
		}else {
			page = sysUserDao.findAll(pageable);
		}
		return page.map(new SysUserRes());
	}
	
	
	public boolean validUsername(String username) {
		if(StringUtils.isBlank(username)) {
			return false;
		}
		int count  = sysUserDao.countByUsername(username);
		return count>0?false:true;
	}
	
	@Transactional
	public Integer save(SysUserReq req) {
		if(!validUsername(req.getUsername())) {
			throw new ServiceException("用户名已存在！");
		}
		SysUser sysUser = req.create();
		sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
		sysUserDao.save(sysUser);
		return sysUser.getId();
	}
	
	private SysUser getSysUser(Integer id) {
		if(id==null) {
			throw new ServiceException("ID不能为空！");
		}
		Optional<SysUser> optional = sysUserDao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("不存在的ID！");
		}
		return optional.get();
	}
	
	public VO get(Integer id) {
		return new SysUserRes().apply(getSysUser(id));
	}
	
	@Transactional
	public void update(UpdateSysUserReq req) {
		SysUser sysUser = getSysUser(req.getId());
		if(!sysUser.getUsername().equals(req.getUsername())) {
			if(!validUsername(req.getUsername())) {
				throw new ServiceException("该用户名已存在！");
			}
		}
		req.update(sysUser);
		sysUserDao.save(sysUser);
	}
	
	@Transactional
	public String resetPwd(Integer id) {
		SysUser sysUser = getSysUser(id);
		sysUser.setPassword(passwordEncoder.encode(SysUser.DEFAULT_PASSWORD));
		sysUser.setUpdateDate(new Date());
		sysUserDao.save(sysUser);
		return SysUser.DEFAULT_PASSWORD;
	}
	
	@Transactional
	public void enable(Integer id , boolean enable) {
		SysUser sysUser = getSysUser(id);
		if(sysUser.isEnable() != enable) {			
			sysUser.setEnable(enable);
			sysUserDao.save(sysUser);
		}
	}
	
	@Transactional
	public void delete(Integer id) {
		if(id != null)
			sysUserDao.deleteById(id);
	}
	
}
