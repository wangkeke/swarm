package com.swarm.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.VO;
import com.swarm.web.service.BusMnprogramService;
import com.swarm.web.vo.BusMnprogramReq;

/**
 * 小程序配置信息
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/mnprogram/")
public class BusMnprogramController {
	
	@Autowired
	private BusMnprogramService service;
	
	@GetMapping("get")
	public JsonResult get() {
		VO vo = service.get();
		if(vo==null)
			return JsonResult.ok();
		return JsonResult.ok(vo);
	}
	
	@PostMapping("save")
	public JsonResult save(@Valid BusMnprogramReq req , BindingResult result) {
		if(result.hasErrors())
			return JsonResult.fail(result.getAllErrors());
		return JsonResult.ok(service.save(req));
	}
	
}
