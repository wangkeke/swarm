package com.swarm.app.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.swarm.base.service.RedirectException;
import com.swarm.base.service.ServiceException;
import com.swarm.base.service.UnauthorizedException;
import com.swarm.base.vo.JsonResult;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice(basePackages = "com.swarm.app.controller")
public class WebRestExceptionAdvice {
	
	
	@ExceptionHandler
	public JsonResult webRestExceptionHandle(Exception e) {
		if(e instanceof ServiceException) {
			return JsonResult.fail(e.getMessage());
		}
		if(e instanceof UnauthorizedException) {
			return JsonResult.unauthorized(e.getMessage());
		}
		if(e instanceof RedirectException) {
			return JsonResult.redirect(e.getMessage());
		}
		e.printStackTrace();
		log.warn("服务器内部异常：",e);
		return JsonResult.systemFail();
	}
	
}
