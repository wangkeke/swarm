package com.swarm.admin.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.admin.vo.Paging;
import com.swarm.admin.vo.SysUserReq;
import com.swarm.admin.vo.SysUserRes;
import com.swarm.base.dao.SysUserDao;
import com.swarm.base.entity.SysUser;
import com.swarm.base.vo.JsonResult;

@RestController
@RequestMapping("/sysuser")
public class SysUserController {
	
	@Autowired
	private SysUserDao sysUserDao;
	
	@GetMapping("/page")
	public JsonResult page(Paging paging , SysUserReq sysUserReq) {
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("createDate")));
		Page<SysUser> page = null;
		if(StringUtils.isNotBlank(sysUserReq.getUsername())) {
			page = sysUserDao.findByUsername(sysUserReq.getUsername(), pageable);
		}else {
			page = sysUserDao.findAll(pageable);
		}
		SysUserRes sysUserRes = new SysUserRes();
		return JsonResult.ok(page.map(sysUserRes));
	}
	
}
