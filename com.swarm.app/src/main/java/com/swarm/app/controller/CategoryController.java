package com.swarm.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.app.service.CategoryService;
import com.swarm.base.vo.JsonResult;

@RequestMapping("/category/")
@RestController
public class CategoryController extends BaseController {
	
	@Autowired
	private CategoryService service;
	
	@GetMapping("category")
	public JsonResult category(@PathVariable Integer busUserId) {
		return JsonResult.ok(service.category(busUserId));
	}
	
	@GetMapping("products")
	public JsonResult products(@PathVariable Integer busUserId , Integer categoryId , Pageable pageable) {
		return JsonResult.ok(service.products(busUserId, categoryId, pageable));
	}
	
}
