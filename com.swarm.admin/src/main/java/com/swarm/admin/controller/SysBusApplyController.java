package com.swarm.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.admin.service.SysBusApplyService;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;

@RestController
@RequestMapping("/sysbusapply/")
public class SysBusApplyController {
	
	@Autowired
	private SysBusApplyService service;
	
	@GetMapping("page")
	public JsonResult page(Paging paging) {
		return JsonResult.ok(service.page(paging));
	}
	
	@RequestMapping("confirmed")
	public JsonResult confirmed(Integer id) {
		service.confirmed(id);
		return JsonResult.ok();
	} 
	
}
