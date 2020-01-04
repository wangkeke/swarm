package com.swarm.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.app.service.OrderService;
import com.swarm.base.vo.VO;

@RequestMapping("/order/")
@RestController
public class OrderController extends BaseController {
	
	@Autowired
	private OrderService service;
	
	
	public Page<VO> page(Integer busUserId){
		
	}
	
}
