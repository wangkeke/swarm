package com.swarm.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.vo.JsonResult;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusUserRes;
import com.wf.captcha.utils.CaptchaUtil;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置位数
        CaptchaUtil.out(5, request, response);
    }
	
	@RequestMapping("/unauthorized")
	public JsonResult unauthorized(HttpServletRequest request) {
		AuthenticationException SPRING_SECURITY_LAST_EXCEPTION = (AuthenticationException)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		String reason = null;
		if(SPRING_SECURITY_LAST_EXCEPTION!=null) {
			reason = SPRING_SECURITY_LAST_EXCEPTION.getMessage();
		}
		return JsonResult.unauthorized(reason);
	}
	
	@RequestMapping("/success")
	public JsonResult login() {
		return JsonResult.ok(new BusUserRes().apply(CurrentUser.getBusUser()));
	}
	
	@RequestMapping("/failure")
	public JsonResult failure(HttpServletRequest request) {
		AuthenticationException SPRING_SECURITY_LAST_EXCEPTION = (AuthenticationException)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		String reason = "用户名或密码错误！";
		if(SPRING_SECURITY_LAST_EXCEPTION!=null) {
			reason = SPRING_SECURITY_LAST_EXCEPTION.getMessage();
		}
		return JsonResult.fail(reason);
	}
	
	@RequestMapping("/logout")
	public JsonResult logout() {
		return JsonResult.ok();
	}
	
}

