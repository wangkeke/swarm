package com.swarm.admin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.admin.service.SysDictService;
import com.swarm.admin.vo.SysDictReq;
import com.swarm.admin.vo.UpdateSysDictReq;
import com.swarm.base.entity.DictType;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;

/**
 * 字典
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/sysdict/")
public class SysDictController {
	
	@Autowired
	private SysDictService sysDictService;
	
	@GetMapping("page")
	public JsonResult page(DictType type , Paging paging) {
		return JsonResult.ok(sysDictService.page(type, paging));
	}
	
	@GetMapping("getType")
	public JsonResult getType() {
		return JsonResult.ok(DictType.values());
	}
	
	@GetMapping("getDictsByParent")
	private JsonResult getDictsByParent(Integer parentId) {
		return JsonResult.ok(sysDictService.getDictsByParent(parentId));
	}
	
	@GetMapping("getDictsByType")
	private JsonResult getDictsByType(DictType type) {
		return JsonResult.ok(sysDictService.getDictsByType(type));
	}
	
	@GetMapping("get")
	public JsonResult get(Integer id) {
		return JsonResult.ok(sysDictService.get(id));
	}
	
	@GetMapping("validKey")
	public JsonResult validKey(String key) {
		return JsonResult.ok(sysDictService.validKey(key));
	}
	
	@PostMapping("save")
	public JsonResult save(@Valid SysDictReq req , BindingResult result) {
		if(result.hasErrors())
			return JsonResult.fail(result.getAllErrors());
		return JsonResult.ok(sysDictService.save(req));
	}
	
	@PostMapping("update")
	public JsonResult update(@Valid UpdateSysDictReq req , BindingResult result) {
		if(result.hasErrors())
			return JsonResult.fail(result.getAllErrors());
		sysDictService.update(req);
		return JsonResult.ok();
	}
	
	@RequestMapping("delete")
	public JsonResult delete(Integer id) {
		sysDictService.delete(id);
		return JsonResult.ok();
	}
	
}
