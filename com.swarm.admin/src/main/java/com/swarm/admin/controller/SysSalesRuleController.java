package com.swarm.admin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.admin.service.SysSalesRuleService;
import com.swarm.admin.vo.SysSalesRuleReq;
import com.swarm.admin.vo.UpdateSysSalesRuleReq;
import com.swarm.base.entity.SalesRuleType;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;

/**
 * 产品促销规则
 * @param id
 * @return
 */
@RestController
@RequestMapping("/syssalesrule/")
public class SysSalesRuleController {
	
	@Autowired
	private SysSalesRuleService service;
	
	@GetMapping("page")
	public JsonResult page(String name , Paging paging) {
		return JsonResult.ok(service.page(name, paging));
	}
	
	@GetMapping("validType")
	public JsonResult validType(SalesRuleType type) {
		return JsonResult.ok(service.validType(type));
	}
	
	@PostMapping("save")
	public JsonResult save(@Valid SysSalesRuleReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		return JsonResult.ok(service.save(req));
	}
	
	@GetMapping("id")
	public JsonResult get(Integer id) {
		return JsonResult.ok(service.get(id));
	}
	
	@PostMapping("update")
	public JsonResult update(@Valid UpdateSysSalesRuleReq req , BindingResult result) {
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
	
}
