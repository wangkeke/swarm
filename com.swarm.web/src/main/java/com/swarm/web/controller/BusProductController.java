package com.swarm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;
import com.swarm.web.service.BusProductService;

@RestController
@RequestMapping("/product/")
public class BusProductController {
	
	@Autowired
	private BusProductService service;
	
	public JsonResult page(String title , Paging paging) {
		
	}
	
	public JsonResult save() {
		
	}
	
	public JsonResult get(Integer id) {
		
	}
	
	public JsonResult update() {
		
	}
	
	public JsonResult show(Integer id , Boolean show) {
		
	}
	
	public JsonResult delete(Integer id) {
		
	}
	
}
