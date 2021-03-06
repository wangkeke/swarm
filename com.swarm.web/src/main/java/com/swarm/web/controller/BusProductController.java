package com.swarm.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;
import com.swarm.web.CurrentUser;
import com.swarm.web.service.BusProductService;
import com.swarm.web.vo.BusProductReq;
import com.swarm.web.vo.UpdateBusProductReq;

@RestController
@RequestMapping("/product/")
public class BusProductController {
	
	@Autowired
	private BusProductService service;
	
	public JsonResult page(Integer categroyId , String title , Paging paging) {
		return JsonResult.ok(service.page(categroyId, title, paging));
	}
	
	public JsonResult save(@Valid BusProductReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		Integer busUserId = CurrentUser.getBusUserId();
		return JsonResult.ok(service.save(busUserId,req));
	}
	
	public JsonResult get(Integer id) {
		return JsonResult.ok(service.get(id));
	}
	
	public JsonResult update(@Valid UpdateBusProductReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		Integer busUserId = CurrentUser.getBusUserId();
		service.update(busUserId,req);
		return JsonResult.ok();
	}
	
	public JsonResult show(Integer id , Boolean show) {
		Integer busUserId = CurrentUser.getBusUserId();
		service.show(busUserId,id, show);
		return JsonResult.ok();
	}
	
	public JsonResult delete(Integer id) {
		Integer busUserId = CurrentUser.getBusUserId();
		service.delete(busUserId,id);
		return JsonResult.ok();
	}
	
}
