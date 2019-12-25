package com.swarm.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;
import com.swarm.web.service.BusWeDepositRecordService;

@RestController
@RequestMapping("/wedepositrecord/")
public class BusWeDepositRecordController {
	
	@Autowired
	private BusWeDepositRecordService service;
	
	@GetMapping("page")
	public JsonResult page(String name , Paging paging) {
		return JsonResult.ok(service.page(name, paging));
	}
	
}
