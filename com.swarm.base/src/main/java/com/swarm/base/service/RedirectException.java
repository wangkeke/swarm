package com.swarm.base.service;

public class RedirectException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5473390857095339575L;
	
	public RedirectException(){
		super();
	}
	
	public RedirectException(String url) {
		super(url);
	}
	
}
