package com.swarm.base.vo;

import org.springframework.http.HttpStatus;

public class JsonResultCode {
	
	/**
	 * 请求成功码
	 */
	public static int OK_CODE = 0;
	
	/**
	 * 用户业务逻辑错误码
	 */
	public static int FAIL_CODE = 1;
	
	/**
	 * 有代码抛出的用户未知的异常码
	 */
	public static int INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();
	
	/**
	 * 用户未登录的异常码
	 */
	public static int UNAUTHORIZED_CODE   = HttpStatus.UNAUTHORIZED.value();
	
	/**
	 * 重定向码
	 */
	public static int REDIRECT_CODE = HttpStatus.TEMPORARY_REDIRECT.value();
	
}
