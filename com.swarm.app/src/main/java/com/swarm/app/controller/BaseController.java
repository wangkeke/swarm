package com.swarm.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/{busUserId}")
@RestController
public class BaseController {
	
	protected String getServerUrl(HttpServletRequest request) {
		String protocol = request.getProtocol();
		if(protocol.toLowerCase().indexOf("https")>-1) {
			return request.getProtocol()+"://" + request.getServerName()+
					(request.getServerPort()==443?"":(":"+request.getServerPort()))+request.getContextPath();
		}else {
			return request.getProtocol()+"://" + request.getServerName()+
					(request.getServerPort()==80?"":(":"+request.getServerPort()))+request.getContextPath();
		}
	}
	
}
