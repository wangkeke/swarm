package com.swarm.admin.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.admin.CurrentUser;
import com.swarm.admin.vo.SysUserRes;
import com.swarm.base.entity.SysUser;
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
	public JsonResult login(HttpServletResponse response) {
		SysUser sysUser = CurrentUser.getSysUser();
		response.setHeader("userId", sysUser.getId()+"");
		response.setHeader("username", sysUser.getUsername());
		Cookie cookie = new Cookie("userId", sysUser.getId()+"");
		cookie.setPath("/");
		response.addCookie(cookie);
		Cookie cookie1 = new Cookie("username", sysUser.getUsername()+"");
		cookie1.setPath("/");
		response.addCookie(cookie1);
		return JsonResult.ok(new SysUserRes().apply(CurrentUser.getSysUser()));
	}
	
	@RequestMapping("/failure")
	public JsonResult failure(HttpServletRequest request) {
		AuthenticationException SPRING_SECURITY_LAST_EXCEPTION = (AuthenticationException)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
		return JsonResult.fail(SPRING_SECURITY_LAST_EXCEPTION.getMessage());
	}
	
	@RequestMapping("/logout")
	public JsonResult logout() {
		return JsonResult.ok();
	}
	
}

