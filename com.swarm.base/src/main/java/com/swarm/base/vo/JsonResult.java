package com.swarm.base.vo;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonResult implements VO{
	
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;
	private Integer code;
	private String reason;
	private Object error;
	private Object data;
	
	private JsonResult(Integer code) {
		this.code = code;
	}
	
	public JsonResult(Integer code , String reason) {
		this.code = code;
		this.reason = reason;
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
	
	public static JsonResult error(String reason) {
		return new JsonResult(FAILURE, reason);
	}
	
	public static JsonResult error(Object errors) {
		return new JsonResult(FAILURE,null,errors);
	}
	
	public static JsonResult success() {
		return new JsonResult(SUCCESS);
	}
	
	public static JsonResult success(Object data) {
		return new JsonResult(SUCCESS, data);
	}
	
}
