package com.swarm.app.controller;

import java.util.Map;

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

import com.alibaba.fastjson.JSONObject;
import com.swarm.app.service.UserService;
import com.swarm.app.vo.BusWeWithdrawalReq;
import com.swarm.app.vo.SysBusApplyReq;
import com.swarm.base.vo.JsonResult;
import com.swarm.base.vo.Paging;

@RequestMapping("/user")
@RestController
public class UserController extends BaseController {
	
	@Autowired
	private UserService service;
	
	
	@RequestMapping("/login")
	public JsonResult login(@PathVariable Integer busUserId , String code , Integer userId) {
		return JsonResult.ok(service.login(busUserId, code, userId));
	}
	
	/**
	 * 获取用户信息
	 * @param busUserId
	 * @param userId
	 * @return
	 */
	@PostMapping("/{userId}/setInfo")
	public JsonResult setInfo(@PathVariable Integer busUserId ,@PathVariable Integer userId , @RequestBody String res) {
		service.setInfo(busUserId, userId, res);
		return JsonResult.ok();
	}
	
	/**
	 * 领取优惠券
	 * @param busUserId
	 * @param userId
	 * @param id
	 * @return
	 */
	@RequestMapping("/{userId}/coupon/save")
	public JsonResult saveCoupon(@PathVariable Integer busUserId ,@PathVariable Integer userId , Integer id) {
		service.saveCoupon(busUserId, userId, id);
		return JsonResult.ok();
	}
	
	/**
	 * 优惠券列表
	 * @param busUserId
	 * @param userId
	 * @param id
	 * @return
	 */
	@RequestMapping("/{userId}/coupon/list")
	public JsonResult list(@PathVariable Integer busUserId ,@PathVariable Integer userId) {
		return JsonResult.ok(service.list(busUserId, userId));
	}
	
	/**
	 * 更新收藏,存在则删除，不存在则添加
	 * @param busUserId
	 * @param productId
	 * @return
	 */
	@RequestMapping("/{userId}/favorite/update")
	public JsonResult updatefavorite(@PathVariable Integer busUserId ,@PathVariable Integer userId , Integer id) {
		service.favorite(busUserId, id, userId);
		return JsonResult.ok();
	}
	
	/**
	 * 分页查询收藏商品
	 * @param busUserId
	 * @param productId
	 * @return
	 */
	@RequestMapping("/{userId}/favorite/page")
	public JsonResult pagefavorite(@PathVariable Integer busUserId ,@PathVariable Integer userId , Paging paging) {
		return JsonResult.ok(service.myfavorite(busUserId, userId, paging));
	}
	
	/**
	 * 删除收藏
	 * @param busUserId
	 * @param productId
	 * @return
	 */
	@RequestMapping("/{userId}/favorite/delete")
	public JsonResult delfavorite(@PathVariable Integer busUserId ,@PathVariable Integer userId , Integer[] id) {
		service.delfavorite(busUserId, userId, id);
		return JsonResult.ok();
	}
	
	/**
	 * 微信充值预支付
	 * @param request
	 * @param busUserId
	 * @param userId
	 * @param money
	 * @return
	 */
	@RequestMapping("/{userId}/prepayRecharge")
	public JsonResult prepayRecharge(HttpServletRequest request ,@PathVariable Integer busUserId ,@PathVariable Integer userId , String money) {
		Map<String, Object> resultMap = service.prepayRecharge(busUserId, userId, money, "钱包充值", getServerUrl(request)+"/"+busUserId+"/user/"+userId+"/recharge");
		return JsonResult.ok(resultMap);
	}
	
	/**
	 * 微信充值成功调用接口钱包充值
	 * @param busUserId
	 * @param userId
	 * @param id
	 * @return
	 */
	@RequestMapping("/{userId}/recharge")
	public JSONObject recharge(@PathVariable Integer busUserId ,@PathVariable Integer userId , @RequestBody String body) {
		return service.recharge(busUserId, userId, body);
	}
	
	
	/**
	 * 获取银行列表
	 * @param busUserId
	 * @param userId
	 * @return
	 */
	@GetMapping("/{userId}/bankDicts")
	public JsonResult bankDicts(@PathVariable Integer busUserId ,@PathVariable Integer userId) {
		return JsonResult.ok(service.bankDicts(busUserId, userId));
	}
	
	/**
	 * 获取提现设置
	 * @param busUserId
	 * @param userId
	 * @return
	 */
	@GetMapping("/{userId}/withdrawDicts")
	public JsonResult withdrawDicts(@PathVariable Integer busUserId ,@PathVariable Integer userId) {
		return JsonResult.ok(service.withdrawDicts(busUserId, userId));
	}
	
	/**
	 * 提现记录查询
	 * @param busUserId
	 * @param userId
	 * @return
	 */
	@RequestMapping("/{userId}/withdraw")
	public JsonResult withdraw(@PathVariable Integer busUserId ,@PathVariable Integer userId , Paging paging) {
		return JsonResult.ok(service.withdraw(busUserId, userId, paging));
	}
	
	
	/**
	 * 提现申请
	 * @param busUserId
	 * @param userId
	 * @return
	 */
	@PostMapping("/{userId}/withdraw/save")
	public JsonResult withdrawSave(@PathVariable Integer busUserId ,@PathVariable Integer userId , @Valid BusWeWithdrawalReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}
		return JsonResult.ok(service.withdrawSave(busUserId, userId, req));
	}
	
	/**
	 * 商业申请
	 * @return
	 */
	public JsonResult busApply(@PathVariable Integer busUserId , @Valid SysBusApplyReq req , BindingResult result) {
		if(result.hasErrors()) {
			return JsonResult.fail(result.getAllErrors());
		}		
		return JsonResult.ok(service.busApply(busUserId, req));
	}
	
}
