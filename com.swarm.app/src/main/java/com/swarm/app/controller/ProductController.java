package com.swarm.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.app.service.ProductService;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;

/**
 * 商品列表，商品详情
 * @author Administrator
 *
 */
@RequestMapping("/product/")
@RestController
public class ProductController extends BaseController {
	
	@Autowired
	private ProductService service;
	
	/**
	 * 商品概况/前言
	 * @return
	 */
	@GetMapping("details")
	public JsonResult details(@PathVariable Integer busUserId , Integer id) {
		return JsonResult.ok(service.details(busUserId, id));
	}
	
	/**
	 * 更新收藏,存在则删除，不存在则添加
	 * @param busUserId
	 * @param productId
	 * @return
	 */
	@RequestMapping("favorite")
	public JsonResult favorite(@PathVariable Integer busUserId , Integer id , Integer userId) {
		service.favorite(busUserId, id, userId);
		return JsonResult.ok();
	}
	
	/**
	 * 商品评论
	 * @param busUserId
	 * @param id
	 * @param paging
	 * @return
	 */
	@GetMapping("comment")
	public JsonResult comment(@PathVariable Integer busUserId , Integer id , Paging paging) {
		return JsonResult.ok(service.comment(busUserId, id, paging));
	}
	
}
