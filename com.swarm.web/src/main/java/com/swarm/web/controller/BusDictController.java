package com.swarm.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.entity.DictType;
import com.swarm.base.vo.JsonResult;
import com.swarm.web.service.BusDictService;

/**
 * 商家字典
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/busdict/")
public class BusDictController {
	
	@Autowired
	private BusDictService service;
	
	@GetMapping("get")
	public JsonResult get(DictType type) {
		return JsonResult.ok(service.get(type));
	}
	
	@PostMapping("update")
	public JsonResult update(HttpServletRequest request , DictType type) {
		Map<String, String[]> params = request.getParameterMap();
		service.update(type, params);
		return JsonResult.ok();
	}
	
}
