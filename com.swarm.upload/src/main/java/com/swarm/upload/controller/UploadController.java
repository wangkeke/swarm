package com.swarm.upload.controller;

import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@Log4j2
@RestController
public class UploadController{
	
	@Value("${file.upload.dir}")
	private String rootDir;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@PostMapping("/commonUpload")
	public JsonResult commonUpload(HttpServletRequest request , String label , @RequestParam MultipartFile[] file) {
		try {
			return JsonResult.ok(attachmentService.commonUpload(label, file));			
		} catch (ServiceException e) {
			return JsonResult.fail(e.getMessage());
		}catch (Exception e) {
			log.warn(e);
			return JsonResult.systemFail();
		}
	}
	
	@PostMapping("/{busUserId}/upload")
	public JsonResult upload(HttpServletRequest request , @PathVariable Integer busUserId, String label , @RequestParam MultipartFile[] file) {
		try {
			if(busUserId==null) {
				return JsonResult.fail("非法的url！");
			}
			return JsonResult.ok(attachmentService.upload(busUserId, label, file));			
		} catch (ServiceException e) {
			return JsonResult.fail(e.getMessage());
		}catch (Exception e) {
			log.warn(e);
			return JsonResult.systemFail();
		}
	}
	
	/**
	 * support image type : .gif,.jpg,.jpeg,.png
	 */
	@GetMapping(value = {"/{busUserId}/image/**","/image/**"})
	public void image(HttpServletRequest request , HttpServletResponse response) {
		try {
			String servletPath = request.getServletPath();
			String imagePath = rootDir+servletPath;
			if(request.getParameterMap().containsKey("small")) {				
				imagePath = rootDir + servletPath.substring(0, servletPath.lastIndexOf(".")) + "_s" + servletPath.substring(servletPath.lastIndexOf("."));
			}
			Files.copy(Paths.get(imagePath), response.getOutputStream());
		} catch (Exception e) {
			log.warn(e);
		}
	}
	
	/**
	 * support image type : .gif,.jpg,.jpeg,.png
	 */
	@GetMapping(value = {"/{busUserId}/allFiles","/allFiles"})
	public JsonResult allFiles(HttpServletRequest request , HttpServletResponse response , String type , @PathVariable(required = false) Integer busUserId) {
		try {
			return JsonResult.ok(attachmentService.allFiles(type, busUserId));
		} catch (Exception e) {
			log.warn(e);
			return JsonResult.systemFail();
		}
	}
	
	@GetMapping("/download/{attachmentId}")
	public void download(HttpServletResponse response ,@PathVariable Integer attachmentId) {
		try {
			AttachmentRes res = attachmentService.download(attachmentId);
			String path = rootDir + res.getPath();
			String filename = res.getFilename();
			response.addHeader("Content-disposition", "attachment; filename=" + new String(filename.getBytes("gb2312"), "ISO-8859-1"));
			response.addHeader("Content-Type", "application/octet-stream");
			Files.copy(Paths.get(path), response.getOutputStream());
		} catch (Exception e) {
			log.warn(e);
		}
	}
	
}
