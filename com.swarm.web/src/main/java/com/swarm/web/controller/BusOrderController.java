package com.swarm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.service.ActivityNode;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;
import com.swarm.web.service.BusOrderService;

@RestController
@RequestMapping("/order/")
public class BusOrderController {
	
	@Autowired
	private BusOrderService service;
	
	@GetMapping("page")
	public JsonResult page(Paging paging) {
		return JsonResult.ok(service.page(paging));
	}
	
	@PostMapping("process")
	private JsonResult process(Integer id , String comment , ActivityNode node) {
	 	service.process(id, comment, node);
		return JsonResult.ok();
	}
	
	@GetMapping("details")
	private JsonResult details(Integer id) {
		return JsonResult.ok(service.details(id));
	}
	
}
