package com.swarm.base.service;

public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -350439688407161691L;
	
	public ServiceException(String msg) {
		super(msg);
	}
	
	public ServiceException(String msg , Throwable cause) {
		super(msg, cause);
	}
	
}
