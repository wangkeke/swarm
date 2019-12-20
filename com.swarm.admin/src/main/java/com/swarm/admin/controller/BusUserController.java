package com.swarm.admin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.admin.service.BusUserService;
import com.swarm.admin.vo.BusUserReq;
import com.swarm.admin.vo.UpdateBusUserReq;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;

/**
 * 商家用户
 * @author Administrator
 *
 */
@RequestMapping("/bususer/")
@RestController
public class BusUserController {
	
	@Autowired
	private BusUserService busUserService;
	
	
	@GetMapping("page")
	public JsonResult page(String keyword , Paging paging) {
		return JsonResult.ok(busUserService.page(keyword, paging));
	}
	
	@GetMapping("validUsername")
	public JsonResult validUsername(String username) {
		return JsonResult.ok(busUserService.validUsername(username));
	}
	
	@PostMapping("save")
	public JsonResult save(@Valid BusUserReq req , BindingResult result) {
		if(result.hasErrors())
			return JsonResult.fail(result.getAllErrors());
		return JsonResult.ok(busUserService.save(req));
	}
	
	@GetMapping("get")
	public JsonResult get(Integer id) {
		return JsonResult.ok(busUserService.get(id));
	}
	
	@PostMapping("update")
	public JsonResult update(@Valid UpdateBusUserReq req , BindingResult result) {
		if(result.hasErrors())
			return JsonResult.fail(result.getAllErrors());
		busUserService.update(req);
		return JsonResult.ok();
	}
	
	@RequestMapping("resetPwd")
	public JsonResult resetPwd(Integer id) {
		return JsonResult.ok(busUserService.resetPwd(id));
	}
	
	@RequestMapping("enable")
	public JsonResult enable(Integer id , Boolean enable) {
		busUserService.enable(id, enable);
		return JsonResult.ok();
	}
	
}
