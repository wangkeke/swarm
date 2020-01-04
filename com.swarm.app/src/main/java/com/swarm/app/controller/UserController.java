package com.swarm.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.app.service.UserService;
import com.swarm.base.vo.JsonResult;

@RequestMapping("/user/")
@RestController
public class UserController extends BaseController {
	
	@Autowired
	private UserService service;
	
	public JsonResult void() {
		
	}
	
}
