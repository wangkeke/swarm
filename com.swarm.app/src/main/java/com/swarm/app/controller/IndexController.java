package com.swarm.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.app.service.IndexService;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;

/**
 * 小程序首页模块
 * @author Administrator
 *
 */
@RequestMapping("/{busUserId}/index/")
@RestController
public class IndexController{
	
	@Autowired
	private IndexService service;
	
	/**
	 * 首页商品搜索
	 * @param keyword
	 * @return
	 */
	@GetMapping("search")
	public JsonResult search(@PathVariable Integer busUserId , String keyword , Paging paging) {
		 return JsonResult.ok(service.search(busUserId, keyword, paging));
	}
	
	/**
	 * 首页轮播图
	 * @param busUserId
	 * @return
	 */
	@GetMapping("carousel")
	public JsonResult carousel(@PathVariable Integer busUserId) {
		return JsonResult.ok(service.carousel(busUserId));
	}
	
	/**
	 * 首页菜单
	 * @param busUserId
	 * @return
	 */
	@GetMapping("menu")
	public JsonResult menu(@PathVariable Integer busUserId) {
		return JsonResult.ok(service.menu(busUserId));
	}
	
	/**
	 * 首页弹窗广告或页面广告位置
	 * @param busUserId
	 * @return
	 */
	@GetMapping("advertising")
	public JsonResult advertising(@PathVariable Integer busUserId) {
		return JsonResult.ok(service.advertising(busUserId));
	}
	
	/**
	 * 首页促销规则
	 * @param busUserId
	 * @return
	 */
	@GetMapping("salesRules")
	public JsonResult salesRules(@PathVariable Integer busUserId) {
		return JsonResult.ok(service.salesRules(busUserId));
	}
	
	@GetMapping("salesRules/details")
	public JsonResult details(@PathVariable Integer busUserId , Integer ruleId) {
		return JsonResult.ok(service.details(busUserId, ruleId));
	}
	
	/**
	 * 首页商品展示，分页展示，根据商品标签从大到小排序
	 * @param paging
	 * @return
	 */
	@GetMapping("products")
	public JsonResult products(@PathVariable Integer busUserId, Paging paging) {
		return JsonResult.ok(service.products(busUserId, paging));
	}
	
	/**
	 * 优惠券列表
	 * @param busUserId
	 * @return
	 */
	@GetMapping("coupon")
	public JsonResult coupon(@PathVariable Integer busUserId) {
		return JsonResult.ok(service.coupon(busUserId));
	}
	
}
