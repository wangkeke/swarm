package com.swarm.upload.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.JsonResult;
import com.swarm.upload.service.AttachmentService;
import com.swarm.upload.vo.AttachmentRes;

import lombok.extern.log4j.Log4j2;

/**
 * 上传控制器
 * @author Administrator
 *
 */
@RestController("/{busUserId}")
@Log4j2
public class UploadController{
	
	@Value("${file.upload.dir:./upload/}")
	private String rootDir;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@PostConstruct
	public void init() {
		rootDir += (rootDir.endsWith("/")?"":"/");
	}
	
	@PostMapping("/upload")
	public JsonResult upload(HttpServletRequest request , @PathVariable Integer busUserId, String label , @RequestParam MultipartFile[] file) {
		try {
			if(busUserId==null || busUserId<0) {
				return JsonResult.unauthorized();
			}
			List<AttachmentRes> list = attachmentService.upload(busUserId, label, file);
			return JsonResult.ok(list);			
		} catch (ServiceException e) {
			return JsonResult.fail(e.getMessage());
		}catch (Exception e) {
			log.warn(e);
			return JsonResult.systemFail();
		}
	}
	
	@GetMapping(value = "/image/**/*.", produces = {"image/*"})
	public void image(HttpServletRequest request , HttpServletResponse response , @PathVariable Integer busUserId) {
		try {
			String servletPath = request.getServletPath();
			String imagePath = rootDir+servletPath;
			Files.copy(Paths.get(imagePath), response.getOutputStream());
		} catch (Exception e) {
			log.warn(e);
		}
	}
	
	@GetMapping("/download/{attachmentId}")
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
