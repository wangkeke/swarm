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
import com.swarm.web.service.BusMenuService;
import com.swarm.web.vo.UpdateBusMenuReq;

@RestController
@RequestMapping("/menu/")
public class BusMenuController {
	
	@Autowired
	private BusMenuService service;
	
	@GetMapping("page")
	public JsonResult page(Paging paging) {
		return JsonResult.ok(service.page(paging));
	}
	
	@PostMapping("update")
	public JsonResult update(@Valid UpdateBusMenuReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
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
	
}
