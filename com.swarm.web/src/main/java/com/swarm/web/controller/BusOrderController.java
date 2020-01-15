package com.swarm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.service.ActivityNode;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;
import com.swarm.web.CurrentUser;
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
	
	@GetMapping("cashbackPointcuts")
	public JsonResult cashbackPointcuts() {
		return JsonResult.ok(service.cashbackPointcuts());
	}
	
	@PostMapping("process")
	public JsonResult process(Integer id ,Integer userId, String comment , ActivityNode node) {
		Integer busUserId = CurrentUser.getBusUserId();
		service.process(busUserId,userId,id, comment, node);
		return JsonResult.ok();
	}
	
	@GetMapping("details")
	public JsonResult details(Integer id) {
		return JsonResult.ok(service.details(id));
	}
	
}
