package com.swarm.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.vo.JsonResult;
import com.swarm.web.service.BusLabelService;
import com.swarm.web.vo.BusLabelReq;
import com.swarm.web.vo.UpdateBusLabelReq;

@RestController
@RequestMapping("/label/")
public class BusLabelController {
	
	@Autowired
	private BusLabelService service;
	
	@GetMapping("list")
	public JsonResult list() {
		return JsonResult.ok(service.list());
	}
	
	@GetMapping("validLabel")
	public JsonResult validLabel(String label) {
		return JsonResult.ok(service.validLabel(label));
	}
	
	@PostMapping("save")
	public JsonResult save(@Valid BusLabelReq req ,BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		return JsonResult.ok(service.save(req));
	}
	
	@PostMapping("update")
	public JsonResult update(@Valid UpdateBusLabelReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		service.update(req);
		return JsonResult.ok();
	}
	
	@RequestMapping("delete")
	public JsonResult delete(Integer id) {
		service.delete(id);
		return JsonResult.ok();
	}
	
}
