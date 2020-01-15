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
import com.swarm.web.CurrentUser;
import com.swarm.web.service.BusCouponService;
import com.swarm.web.vo.BusCouponReq;
import com.swarm.web.vo.UpdateBusCouponReq;

@RequestMapping("/coupon/")
@RestController
public class BusCouponController {
	
	@Autowired
	private BusCouponService service;
	
	
	@GetMapping("page")
	public JsonResult page(Paging paging) {
		return JsonResult.ok(service.page(paging));
	}
	
	@PostMapping("save")
	public JsonResult save(@Valid BusCouponReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		Integer busUserId = CurrentUser.getBusUserId();
		return JsonResult.ok(service.save(busUserId,req));
	}
	
	@GetMapping("get")
	public JsonResult get(Integer id) {
		return JsonResult.ok(service.get(id));
	}
	
	@PostMapping("update")
	public JsonResult update(@Valid UpdateBusCouponReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		Integer busUserId = CurrentUser.getBusUserId();
		service.update(busUserId,req);
		return JsonResult.ok();
	}
	
	@RequestMapping("enable")
	public JsonResult enable(Integer id , Boolean enable) {
		Integer busUserId = CurrentUser.getBusUserId();
		service.enable(busUserId,id, enable);
		return JsonResult.ok();
	}
	
	@RequestMapping("delete")
	public JsonResult delete(Integer id) {
		Integer busUserId = CurrentUser.getBusUserId();
		service.delete(busUserId,id);
		return JsonResult.ok();
	}
	
}
