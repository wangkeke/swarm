package com.swarm.admin.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.swarm.base.service.RedirectException;
import com.swarm.base.service.ServiceException;
import com.swarm.base.service.UnauthorizedException;
import com.swarm.base.vo.JsonResult;

@RestControllerAdvice(basePackages = "com.swarm.admin.controller")
public class WebRestExceptionAdvice {
	
	
	@ExceptionHandler
	public JsonResult webRestExceptionHandle(Exception e) {
		if(e instanceof ServiceException) {
			return JsonResult.fail(e.getMessage());
		}
		if(e instanceof UnauthorizedException) {
			return JsonResult.unauthorized();
		}
		if(e instanceof RedirectException) {
			return JsonResult.redirect(e.getMessage());
		}
		return JsonResult.systemFail();
	}
	
}
