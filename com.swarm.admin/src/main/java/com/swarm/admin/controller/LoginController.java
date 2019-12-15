package com.swarm.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.admin.vo.LoginReq;
import com.swarm.base.vo.JsonResult;
import com.wf.captcha.utils.CaptchaUtil;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置位数
        CaptchaUtil.out(5, request, response);
    }
	
	@GetMapping("/unauthorized")
	public JsonResult unauthorized() {
		return JsonResult.unauthorized();
	}
	
	@PostMapping
	public JsonResult login(@Valid LoginReq req , BindingResult result) {
		return null;
	}
	
	@GetMapping("failure")
	public JsonResult failure() {
		return JsonResult.fail("用户名或密码错误！");
	}
	
	@GetMapping("/logout")
	public JsonResult logout() {
		return JsonResult.ok();
	}
	
}

