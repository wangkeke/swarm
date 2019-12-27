package com.swarm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.service.ActivityNode;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;
import com.swarm.web.service.BusCashbackService;

@RestController
@RequestMapping("/cashback/")
public class BusCashbackController {
	
	@Autowired
	private BusCashbackService service;
	
	@GetMapping("page")
	public JsonResult page(Paging paging) {
		return JsonResult.ok(service.page(paging));
	}
	
	@PostMapping("process")
	public JsonResult process(Integer id , ActivityNode node) {
		service.process(id, node);
		return JsonResult.ok();
	}
	
	@GetMapping("details")
	public JsonResult details(Integer id , Paging paging) {
		return JsonResult.ok(service.details(id, paging));
	}
	
}
