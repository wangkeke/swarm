package com.swarm.base.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swarm.base.vo.JsonResult;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class TemplateResourceService {
	
	@Value("${file.template.dir:/swarm/template}")
	private String templateRootDir;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private Configuration configuration;
	
	public String updateTemplateResource(Integer busUserId ,String dir,String fileName, Object data , String templateName) {
		String parentPath = "/" + busUserId + "/" +dir;
		File parent = new File(templateRootDir + parentPath);
		if(!parent.exists()) {
			parent.mkdirs();
		}
		fileName += ".data";
		OutputStreamWriter writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(new File(parent,fileName)), "UTF-8");
			Template template = configuration.getTemplate(templateName+".ftl");
			template.process(JsonResult.ok(objectMapper.writeValueAsString(data)), writer);
			return parentPath + "/" + fileName;
		} catch (Exception e) {
			throw new ServiceException("商品静态数据生成失败！",e);
		}finally {
			try {
				if(writer!=null) {
					writer.close();
				}
			} catch (IOException e2) {
				throw new ServiceException("商品静态数据生成失败！",e2);
			}
		}
	}
	
	public void deleteBusProductResource(Integer busUserId ,String dir,String fileName) {
		fileName += ".data";
		String parentPath = "/" + busUserId + "/" +dir + "/" + fileName;
		File file = new File(templateRootDir + parentPath);
		if(!file.exists()) {
			return;
		}
		file.delete();
	}
	
	
}
