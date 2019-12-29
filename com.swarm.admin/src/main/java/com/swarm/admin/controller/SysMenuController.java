package com.swarm.admin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.admin.service.SysMenuService;
import com.swarm.admin.vo.SysMenuReq;
import com.swarm.admin.vo.UpdateSysMenuReq;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;

/**
 * 系统菜单
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/sysmenu/")
public class SysMenuController {
	
	@Autowired
	private SysMenuService sysMenuService;
	
	
	@GetMapping("page")
	public JsonResult page(String name , Paging paging) {
		return JsonResult.ok(sysMenuService.page(name, paging));
	}
	
	@GetMapping("get")
	public JsonResult get(Integer id) {
		return JsonResult.ok(sysMenuService.get(id));
	}
	
	@GetMapping("validKey")
	public JsonResult validKey(String key) {
		return JsonResult.ok(sysMenuService.validKey(key));
	}
	
	@PostMapping("save")
	public JsonResult save(@Valid SysMenuReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		return JsonResult.ok(sysMenuService.save(req));
	}
	
	@PostMapping("update")
	public JsonResult update(@Valid UpdateSysMenuReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		sysMenuService.update(req);
		return JsonResult.ok();
	}
	
	@RequestMapping("delete")
	public JsonResult delete(Integer id) {
		sysMenuService.delete(id);
		return JsonResult.ok();
	}
	
}
