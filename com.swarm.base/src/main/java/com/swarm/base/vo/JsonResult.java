package com.swarm.base.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonResult implements VO{
	

	private Integer code;
	private String reason;
	private String url;
	private Object error;
	private Object data;
	
	private JsonResult(Integer code) {
		this.code = code;
	}
	
	public JsonResult(Integer code , String reason) {
		this.code = code;
		this.reason = reason;
	};
	
	public JsonResult(Integer code ,String reason, String url) {
		this.code = code;
		this.reason = reason;
		this.url = url;
	};
	
	public JsonResult(Integer code , String reason , Object errors) {
		this.code = code;
		this.reason = reason;
		this.error = errors;
	};
	
	public JsonResult(Integer code , Object data) {
		this.code = code;
		this.data = data;
	}
	
	public static JsonResult ok() {
		return new JsonResult(JsonResultCode.OK_CODE);
	}
	
	public static JsonResult ok(Object data) {
		return new JsonResult(JsonResultCode.OK_CODE, data);
	}
	
	public static JsonResult fail(String reason) {
		return new JsonResult(JsonResultCode.FAIL_CODE, reason);
	}
	
	public static JsonResult fail(Object errors) {
		return new JsonResult(JsonResultCode.FAIL_CODE,null,errors);
	}
	
	public static JsonResult unauthorized() {
		return new JsonResult(JsonResultCode.UNAUTHORIZED_CODE);
	}
	
	public static JsonResult systemFail() {
		return new JsonResult(JsonResultCode.INTERNAL_SERVER_ERROR);
	}
	
	public static JsonResult systemFail(String reason) {
		return new JsonResult(JsonResultCode.INTERNAL_SERVER_ERROR, reason);
	}
	
	public static JsonResult redirect(String url) {
		return new JsonResult(JsonResultCode.REDIRECT_CODE, null, url);
	}
	
}
