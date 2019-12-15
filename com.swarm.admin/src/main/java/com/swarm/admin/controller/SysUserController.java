package com.swarm.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.admin.CurrentUser;
import com.swarm.admin.service.SysUserService;
import com.swarm.admin.vo.SysUserReq;
import com.swarm.admin.vo.SysUserRes;
import com.swarm.admin.vo.UpdateSysUserReq;
import com.swarm.base.entity.Identity;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;

@RestController
@RequestMapping("/sysuser/")
public class SysUserController {
	
	@Autowired
	private SysUserService sysUserService;
	
	@GetMapping("page")
	public JsonResult page(Paging paging , String username) {
		return JsonResult.ok(sysUserService.page(username, paging));
	}
	
	@GetMapping("get")
	public JsonResult get(Integer id) {
		SysUserRes res = new SysUserRes();
		return JsonResult.ok(res.apply(sysUserService.getSysUser(id)));
	}
	
	@GetMapping("getIdentity")
	public JsonResult getIdentity() {
		CurrentUser currentUser = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Identity identity = currentUser.getSysUser().getIdentity();
		List<Identity> list = new ArrayList<Identity>();
		for (Identity identity2 : Identity.values()) {
			if((identity.getId() & identity2.getId())>0 && identity!=identity2) {
				list.add(identity2);
			}
		};
		return JsonResult.ok(list);
	}
	
	@GetMapping("validUsername")
	public JsonResult validUsername(String username) {
		return JsonResult.ok(sysUserService.validUsername(username));
	}
	
	@PostMapping("save")
	public JsonResult save(@Valid SysUserReq req, BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		return JsonResult.ok(sysUserService.saveSysUser(req));
	}
	
	@PutMapping("update")
	public JsonResult update(@Valid UpdateSysUserReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		sysUserService.updateSysUser(req);
		return JsonResult.ok();
	}
	
	@PutMapping("resetpwd")
	public JsonResult resetpwd(Integer id) {
		sysUserService.resetPassword(id);
		return JsonResult.ok();
	}
	
	@PutMapping("updateEnable")
	public JsonResult updateEnable(Integer id , Boolean enable) {
		if(enable==null) {
			return JsonResult.fail("是否启用不能为空！");
		}
		sysUserService.updateEnable(id, enable);
		return JsonResult.ok();
	}
	
	@DeleteMapping("delete")
	public JsonResult delete(Integer id) {
		sysUserService.delete(id);
		return JsonResult.ok();
	}
	
}
