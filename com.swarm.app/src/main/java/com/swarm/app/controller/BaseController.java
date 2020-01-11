package com.swarm.app.controller;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swarm.base.service.ServiceException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping("/{busUserId}")
@RestController
public class BaseController {

	@Value("${file.template.dir:/swarm/template}")
	private String templateRootDir;
	
	public static String getServerUrl(HttpServletRequest request) {
		String protocol = request.getProtocol();
		if(protocol.toLowerCase().indexOf("https")>-1) {
			return request.getProtocol()+"://" + request.getServerName()+
					(request.getServerPort()==443?"":(":"+request.getServerPort()))+request.getContextPath();
		}else {
			return request.getProtocol()+"://" + request.getServerName()+
					(request.getServerPort()==80?"":(":"+request.getServerPort()))+request.getContextPath();
		}
	}
	
	@RequestMapping("/**")
	public void templateResource(HttpServletRequest request , HttpServletResponse response , @PathVariable Integer busUserId) {
		try {
			String servletPath = request.getServletPath();
			String templatePath = templateRootDir+servletPath;
			Files.copy(Paths.get(templatePath), response.getOutputStream());
		} catch (Exception e) {
			log.error(e);
			throw new ServiceException(e.getMessage(),e);
		}
	}
	
}
