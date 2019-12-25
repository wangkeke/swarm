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
import com.swarm.web.service.BusDeliveryAddressService;
import com.swarm.web.vo.BusDeliveryAddressReq;
import com.swarm.web.vo.UpdateBusDeliveryAddressReq;

@RestController
@RequestMapping("/deliveryaddress/")
public class BusDeliveryAddressController {
	
	@Autowired
	private BusDeliveryAddressService service;
	
	@GetMapping("page")
	public JsonResult page(Paging paging) {
		return JsonResult.ok(service.page(paging));
	}
	
	@PostMapping("save")
	public JsonResult save(@Valid BusDeliveryAddressReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		return JsonResult.ok(service.save(req));
	}
	
	@PostMapping("update")
	public JsonResult update(@Valid UpdateBusDeliveryAddressReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		service.update(req);
		return JsonResult.ok();
	}
	
	@RequestMapping("enable")
	public JsonResult enable(Integer id , Boolean enable) {
		service.enable(id, enable);
		return JsonResult.ok();
	}
	
	@RequestMapping("delete")
	public JsonResult delete(Integer id) {
		service.delete(id);
		return JsonResult.ok();
	}
	
}
