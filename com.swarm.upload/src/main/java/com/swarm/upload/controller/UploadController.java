package com.swarm.upload.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.swarm.base.controller.BaseController;
import com.swarm.base.vo.JsonResult;
import com.swarm.upload.service.AttachmentService;
import com.swarm.upload.vo.AttachmentRes;

import lombok.extern.log4j.Log4j2;

/**
 * 上传控制器
 * @author Administrator
 *
 */
@RestController
@Log4j2
public class UploadController extends BaseController{
	
	@Value("${file.upload.dir:./upload/}")
	private String rootDir;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@PostConstruct
	public void init() {
		rootDir += (rootDir.endsWith("/")?"":"/");
	}
	
	@PostMapping("/upload")
	public JsonResult upload(HttpServletRequest request , Integer userId, String label , @RequestParam MultipartFile[] file) {
		try {
			if(userId==null || userId<0) {
				String userIdString = request.getHeader(HEADER_USERID);
				if(StringUtils.isBlank(userIdString)) {
					return JsonResult.error("请先登录！");
				}
				userId = Integer.parseInt(userIdString);
			}
			List<AttachmentRes> list = attachmentService.upload(userId, label, file);
			return JsonResult.success(list);			
		} catch (Exception e) {
			log.warn(e);
			return JsonResult.error(e.getMessage());
		}
	}
	
	@GetMapping(value = "/image/**/*.", produces = {"image/*"})
	public void image(HttpServletRequest request , HttpServletResponse response) {
		try {
			String servletPath = request.getServletPath();
			String imagePath = servletPath.replaceFirst("/image/", rootDir);
			Files.copy(Paths.get(imagePath), response.getOutputStream());
		} catch (Exception e) {
			log.warn(e);
		}
	}
	
	@GetMapping("/{attachmentId}/download")
	public void download(HttpServletResponse response ,@PathVariable Integer attachmentId) {
		try {
			AttachmentRes res = attachmentService.download(attachmentId);
			String path = rootDir + res.getPath();
			String filename = res.getFilename();
			response.addHeader("Content-disposition", "attachment; filename=" + filename);
			response.addHeader("Content-Type", "application/octet-stream");
			Files.copy(Paths.get(path), response.getOutputStream());
		} catch (Exception e) {
			log.warn(e);
		}
	}
	
}
