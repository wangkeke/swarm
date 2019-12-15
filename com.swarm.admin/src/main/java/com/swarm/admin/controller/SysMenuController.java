package com.swarm.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.admin.service.SysMenuService;
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
		
	}
	
	@GetMapping("get")
	public JsonResult get(Integer id) {
		
	}
	
	@PostMapping("save")
	public JsonResult save() {
		
	}
	
	@PutMapping("update")
	public JsonResult update() {
		
	}
	
	@DeleteMapping("delete")
	public JsonResult delete(Integer id) {
		
	}
	
}
