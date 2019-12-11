package com.swarm.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.admin.CurrentUser;
import com.swarm.admin.vo.SysUserReq;
import com.swarm.admin.vo.SysUserRes;
import com.swarm.base.dao.SysUserDao;
import com.swarm.base.entity.RoleIdentity;
import com.swarm.base.entity.SysUser;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;

@RestController
@RequestMapping("/sysuser/")
public class SysUserController {
	
	@Autowired
	private SysUserDao sysUserDao;
	
	@GetMapping("page")
	public JsonResult page(Paging paging , String username) {
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("createDate")));
		Page<SysUser> page = null;
		if(StringUtils.isNotBlank(username)) {
			page = sysUserDao.findByUsernameLike(username, pageable);
		}else {
			page = sysUserDao.findAll(pageable);
		}
		return JsonResult.ok(page.map(new SysUserRes()));
	}
	
	@GetMapping("getRoleIdentity")
	public JsonResult getRoleIdentity() {
		CurrentUser currentUser = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RoleIdentity roleIdentity = currentUser.getSysUser().getRoleIdentity();
		List<RoleIdentity> list = new ArrayList<RoleIdentity>();
		for (RoleIdentity roleIdentity2 : RoleIdentity.values()) {
			if(roleIdentity2.getRole()<roleIdentity.getRole()) {
				list.add(roleIdentity2);
			}
		};
		return JsonResult.ok(list);
	}
	
	@GetMapping("verifyUsername")
	public JsonResult verifyUsername(String username) {
		if(StringUtils.isBlank(username)) {
			return JsonResult.ok(false);
		}
		long count = sysUserDao.countByUsername(username);
		return JsonResult.ok(count>0?false:true);
	}
	
	@PostMapping("addSysUser")
	public JsonResult addSysUser(@Valid SysUserReq req, BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		SysUser sysUser = req.build();
		sysUserDao.save(sysUser);
		return JsonResult.ok(sysUser.getId());
	}
	
	@GetMapping("getSysUser")
	public JsonResult getSysUser(Integer id) {
		if(id==null)
			return JsonResult.ok();
		Optional<SysUser> optional = sysUserDao.findById(id);
		if(!optional.isPresent()) {
			return JsonResult.ok();
		}
		SysUserRes res = new SysUserRes();
		return JsonResult.ok(res.apply(optional.get()));
	}
	
	
	
}
