package com.swarm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;
import com.swarm.web.service.BusWeApiInfoService;

@RequestMapping("/weapiinfo/")
@RestController
public class BusWeApiInfoController {
	
	@Autowired
	private BusWeApiInfoService service;
	
	@GetMapping("stat")
	public JsonResult stat(Paging paging) {
		return JsonResult.ok(service.stat(paging));
	}
	
	@GetMapping("getWxaCode")
	public JsonResult getWxaCode() {
		return JsonResult.ok(service.getWxaCode());
	}
	
}
