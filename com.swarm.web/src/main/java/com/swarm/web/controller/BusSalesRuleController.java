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
import com.swarm.web.service.BusSalesRuleService;
import com.swarm.web.vo.UpdateBusSalesRuleReq;

@RestController
@RequestMapping("/salesrule/")
public class BusSalesRuleController {
	
	@Autowired
	private BusSalesRuleService service;
	
	@GetMapping("page")
	public JsonResult page(Paging paging) {
		return JsonResult.ok(service.page(paging));
	}
	
	@GetMapping("getContent")
	public JsonResult getContent(Integer id) {
		return JsonResult.ok(service.getContent(id));
	}
	
	@PostMapping("updateContent")
	public JsonResult updateContent(Integer id , String content) {
		service.updateContent(id, content);
		return JsonResult.ok();
	}
	
	@PostMapping("update")
	public JsonResult update(@Valid UpdateBusSalesRuleReq req , BindingResult result) {
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
