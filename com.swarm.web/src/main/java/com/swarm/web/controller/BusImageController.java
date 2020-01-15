package com.swarm.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.entity.BusImageType;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.service.BusImageService;
import com.swarm.web.vo.BusImageReq;
import com.swarm.web.vo.BusImageTypeRes;
import com.swarm.web.vo.UpdateBusImageReq;

@RequestMapping("/busimage/")
@RestController
public class BusImageController {
	
	@Autowired
	private BusImageService service;
	
	@GetMapping("getImageType")
	public JsonResult getImageType() {
		List<VO> list = new ArrayList<VO>(BusImageType.values().length);
		for (BusImageType type : BusImageType.values()) {
			list.add(new BusImageTypeRes().apply(type));
		}
		return JsonResult.ok(list);
	}
	
	@GetMapping("page")
	public JsonResult page(Paging paging) {
		return JsonResult.ok(service.page(paging));
	}
	
	@PostMapping("save")
	public JsonResult save(@Valid BusImageReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		Integer busUserId = CurrentUser.getBusUserId();
		return JsonResult.ok(service.save(busUserId,req));
	}
	
	@PostMapping("update")
	public JsonResult update(@Valid UpdateBusImageReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		Integer busUserId = CurrentUser.getBusUserId();
		service.update(busUserId,req);
		return JsonResult.ok();
	}
	
	@RequestMapping("delete")
	public JsonResult delete(Integer id) {
		Integer busUserId = CurrentUser.getBusUserId();
		service.delete(busUserId,id);
		return JsonResult.ok();
	}
	
}
