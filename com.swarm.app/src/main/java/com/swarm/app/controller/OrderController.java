package com.swarm.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.app.service.OrderService;
import com.swarm.app.vo.BusOrderReq;
import com.swarm.app.vo.BusProductCommentReq;
import com.swarm.base.service.ActivityNode;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;

@RequestMapping("/user/{userId}/order")
@RestController
public class OrderController extends BaseController {
	
	@Autowired
	private OrderService service;
	
	
	@GetMapping("/products")
	public JsonResult products(@PathVariable Integer busUserId , Integer[] id) {
		return JsonResult.ok(service.products(busUserId, id));
	}
	
	@PostMapping("/save")
	public JsonResult save(HttpServletRequest request , @PathVariable Integer busUserId , @PathVariable Integer userId , @Valid BusOrderReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.ok(result.getAllErrors());
		}
		return JsonResult.ok(service.save(busUserId, userId, req , getServerUrl(request)+"/"+busUserId+"/user/"+userId+"/order/paid"));
	}
	
	/**
	 * 微信支付成功后调用
	 * @param busUserId
	 * @param userId
	 * @param body
	 * @return
	 */
	@PostMapping("/paid")
	public JsonResult paid(@PathVariable Integer busUserId ,@PathVariable Integer userId , @RequestBody String body) {
		return JsonResult.ok(service.paid(busUserId, userId, body));
	}
	
	/**
	 * 全部订单,
	 * @param busUserId
	 * @param userId
	 * @param node 待付款：PAY_NO ， 待收货：SHIPPED , 退款/售后：APPLY_REFUND|REFUSE_REFUND|REFUNDED  , 待评价：CONFIRMED
	 * @return
	 */
	@GetMapping("/page")
	public JsonResult page(@PathVariable Integer busUserId ,@PathVariable Integer userId , ActivityNode[] node , Paging paging){
		return JsonResult.ok(service.page(busUserId, userId, node, paging));
	}
	
	/**
	 * 用户处理订单状态
	 * @param id
	 * @param comment
	 * @param node
	 * @return
	 */
	@PostMapping("/process")
	private JsonResult process(@PathVariable Integer busUserId ,@PathVariable Integer userId , Integer id , ActivityNode node) {
	 	service.process(busUserId , userId , id, node);
		return JsonResult.ok();
	}
	
	/**
	 * 评价
	 * @param busUserId
	 * @param userId
	 * @param id
	 * @param req
	 * @param result
	 * @return
	 */
	@PostMapping("/comment")
	private JsonResult comment(@PathVariable Integer busUserId ,@PathVariable Integer userId , Integer id , @Valid BusProductCommentReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.ok(result.getAllErrors());
		}
		service.comment(busUserId, userId, id, req);
		return JsonResult.ok();
	}
	
}
