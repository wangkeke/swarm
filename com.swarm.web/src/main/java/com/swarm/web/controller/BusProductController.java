package com.swarm.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;
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
		return JsonResult.ok(service.save(req));
	}
	
	public JsonResult get(Integer id) {
		return JsonResult.ok(service.get(id));
	}
	
	public JsonResult update(@Valid UpdateBusProductReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		service.update(req);
		return JsonResult.ok();
	}
	
	public JsonResult show(Integer id , Boolean show) {
		service.show(id, show);
		return JsonResult.ok();
	}
	
	public JsonResult delete(Integer id) {
		service.delete(id);
		return JsonResult.ok();
	}
	
}
