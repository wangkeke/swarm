package com.swarm.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.app.service.AddressService;
import com.swarm.base.vo.JsonResult;

@RequestMapping("/address/")
@RestController
public class AddressController extends BaseController {
	
	@Autowired
	private AddressService service;
	
	/**
	 * 获取用户收货地址列表
	 * @return
	 */
	@GetMapping("list")
	public JsonResult list(@PathVariable Integer busUserId , Integer userId) {
		
	}
	
	/**
	 * 添加收货地址
	 * @return
	 */
	@PostMapping("save")
	public JsonResult save(@PathVariable Integer busUserId , Integer userId) {
		
	}
	
	/**
	 * 编辑收货地址
	 * @return
	 */
	@PostMapping("update")
	public JsonResult update(@PathVariable Integer busUserId , Integer userId) {
		
	}
	
	/**
	 * 设置首选收货地址
	 * @return
	 */
	@RequestMapping("first")
	public JsonResult first(@PathVariable Integer busUserId , Integer userId) {
		
	}
	
	/**
	 * 删除收货地址
	 * @return
	 */
	@RequestMapping("delete")
	public JsonResult delete(@PathVariable Integer busUserId , Integer userId) {
		
	}
	
}
