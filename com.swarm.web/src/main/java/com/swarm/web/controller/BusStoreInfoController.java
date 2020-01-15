package com.swarm.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.vo.JsonResult;
import com.swarm.web.CurrentUser;
import com.swarm.web.service.BusStoreInfoService;
import com.swarm.web.vo.UpdateBusStoreInfoReq;

@RestController
@RequestMapping("/storeinfo/")
public class BusStoreInfoController {
	
	@Autowired
	private BusStoreInfoService service;
	
	@GetMapping("getType")
	public JsonResult getType() {
		return JsonResult.ok(service.getType());
	}
	
	@GetMapping("get")
	public JsonResult get() {
		return JsonResult.ok(service.get());
	}
	
	@PostMapping("update")
	public JsonResult update(@Valid UpdateBusStoreInfoReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		Integer busUserId = CurrentUser.getBusUserId();
		service.update(busUserId,req);
		return JsonResult.ok();
	}
	
}
