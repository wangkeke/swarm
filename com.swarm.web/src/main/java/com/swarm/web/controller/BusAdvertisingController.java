package com.swarm.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;
import com.swarm.web.service.BusAdvertisingService;
import com.swarm.web.vo.BusAdvertisingReq;
import com.swarm.web.vo.UpdateBusAdvertisingReq;

/**
 * 小程序首页弹窗广告
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/advertising/")
public class BusAdvertisingController {
	
	@Autowired
	private BusAdvertisingService service;
	
	
	
	@GetMapping("page")
	public JsonResult page(Boolean enable ,Paging paging) {
		return JsonResult.ok(service.page(enable, paging));
	}
	
	@PostMapping("save")
	public JsonResult save(@Valid BusAdvertisingReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		return JsonResult.ok(service.save(req));
	}
	
	@GetMapping("get")
	public JsonResult get(Integer id) {
		return JsonResult.ok(service.get(id));
	}
	
	@PostMapping("update")
	public JsonResult update(@Valid UpdateBusAdvertisingReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		service.update(req);
		return JsonResult.ok();
	}
	
	@PostMapping("enable")
	public JsonResult enable(Integer id , Boolean enable) {
		service.enable(id, enable);
		return JsonResult.ok();
	}
	
}
