package com.swarm.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.app.service.AddressService;
import com.swarm.app.vo.BusWeUserAddressReq;
import com.swarm.app.vo.UpdateBusWeUserAddressReq;
import com.swarm.base.vo.JsonResult;

@RequestMapping("/{busUserId}/user/{userId}/address/")
@RestController
public class AddressController{
	
	@Autowired
	private AddressService service;
	
	/**
	 * 获取用户收货地址列表
	 * @return
	 */
	@GetMapping("list")
	public JsonResult list(@PathVariable Integer busUserId , @PathVariable Integer userId) {
		return JsonResult.ok(service.list(busUserId, userId));
	}
	
	/**
	 * 添加收货地址
	 * @return
	 */
	@PostMapping("save")
	public JsonResult save(@PathVariable Integer busUserId , @PathVariable Integer userId , @Valid BusWeUserAddressReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		return JsonResult.ok(service.save(busUserId, userId, req));
	}
	
	/**
	 * 编辑收货地址
	 * @return
	 */
	@PostMapping("update")
	public JsonResult update(@PathVariable Integer busUserId , @PathVariable Integer userId , @Valid UpdateBusWeUserAddressReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		service.update(busUserId, userId, req);
		return JsonResult.ok();
	}
	
	/**
	 * 设置首选收货地址
	 * @return
	 */
	@RequestMapping("first")
	public JsonResult first(@PathVariable Integer busUserId , @PathVariable Integer userId , Integer id) {
		service.first(busUserId, userId, id);
		return JsonResult.ok();
	}
	
	/**
	 * 删除收货地址
	 * @return
	 */
	@RequestMapping("delete")
	public JsonResult delete(@PathVariable Integer busUserId , @PathVariable Integer userId , Integer id) {
		service.delete(busUserId, userId, id);
		return JsonResult.ok();
	}
	
}
