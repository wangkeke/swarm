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
import com.swarm.web.service.BusCategoryService;
import com.swarm.web.vo.BusCategoryReq;
import com.swarm.web.vo.UpdateBusCategoryReq;

@RestController
@RequestMapping("/category/")
public class BusCategoryController {
	
	@Autowired
	private BusCategoryService service;
	
	@GetMapping("list")
	public JsonResult list() {
		return JsonResult.ok(service.list());
	}
	
	@PostMapping("save")
	public JsonResult save(@Valid BusCategoryReq req , BindingResult result) {
		if(result.hasErrors())
			return JsonResult.fail(result.getAllErrors());
		Integer busUserId = CurrentUser.getBusUserId();
		return JsonResult.ok(service.save(busUserId,req));
	}
	
	@PostMapping("update")
	public JsonResult update(@Valid UpdateBusCategoryReq req , BindingResult result) {
		if(result.hasErrors())
			return JsonResult.fail(result.getAllErrors());
		Integer busUserId = CurrentUser.getBusUserId();
		service.update(busUserId,req);
		return JsonResult.ok();
	}
	
	@RequestMapping("show")
	public JsonResult show(Integer id , Boolean show) {
		Integer busUserId = CurrentUser.getBusUserId();
		service.show(busUserId,id, show);
		return JsonResult.ok();
	}
	
	@RequestMapping("delete")
	public JsonResult delete(Integer id) {
		Integer busUserId = CurrentUser.getBusUserId();
		service.delete(busUserId,id);
		return JsonResult.ok();
	}
	
}
