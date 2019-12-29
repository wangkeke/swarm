package com.swarm.upload.service;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import com.swarm.base.dao.AttachmentDao;
import com.swarm.base.dao.BusUserDao;
import com.swarm.base.entity.Attachment;
import com.swarm.base.entity.BaseEntity;
import com.swarm.base.entity.BusUser;
import com.swarm.base.service.ServiceException;
import com.swarm.upload.dao.UploadConfigDao;
import com.swarm.upload.entity.UploadConfig;
import com.swarm.upload.vo.AttachmentRes;



@Service
public class AttachmentService {
	
	@Autowired
	private AttachmentDao attachmentDao;
	
	@Autowired
	private BusUserDao userDao;
	
	@Autowired
	private UploadConfigDao uploadConfigDao;
	
	
	@Transactional
	public List<AttachmentRes> upload(Integer userId , String label , MultipartFile[] files) {
		Optional<BusUser> optional = userDao.findById(userId);
		if(!optional.isPresent()) {
			throw new ServiceException("用户不存在！");
		}
		BusUser user = optional.get();
		if(!user.isEnable()) {
			throw new ServiceException("用户被禁用！");
		}
		if(StringUtils.isBlank(label)) {
			label = UploadConfig.DEFAULT_LABEL;
		}
		UploadConfig config = uploadConfigDao.findFirstByLabel(label);
		try {
			List<AttachmentRes> list = new ArrayList<AttachmentRes>(files.length);
			for (int i = 0; i < files.length; i++) {
				MultipartFile multipartFile = files[i];
				String filename = multipartFile.getOriginalFilename();
				String ext = null;
				if(filename.lastIndexOf(".")>-1) {					
					ext = filename.substring(filename.lastIndexOf(".")+1);
				}
				if(ext==null) {
					throw new ServiceException("不支持的上传文件类型！");
				}
				if(config!=null) {
					if(config.getExcludeType()!=null) {
						if((config.getExcludeType()+",").contains(ext+",")) {
							throw new ServiceException("不支持的上传文件类型！");
						}
					}
					if(config.getIncludeType()!=null) {
						if(!(config.getIncludeType()+",").contains(ext+",")) {
							throw new ServiceException("不支持的上传文件类型！");
						}
					}
					if(config.getMaxSize()!=null) {
						long maxSize = DataSize.parse(config.getMaxSize()).toBytes();
						if(multipartFile.getSize()>maxSize) {
							throw new ServiceException("上传文件超过最大限制！");
						}
					}
				}
				String path = "/" + user.getId() + "/";
				String newFileName = Base64Utils.encodeToString((Integer.toHexString(BaseEntity.getIPAddress().hashCode()) + System.currentTimeMillis() + "-" + Thread.currentThread().getId() + "-" + i).getBytes()); 
				String filetype = multipartFile.getContentType();
				Attachment attachment = new Attachment();
				attachment.setUpdateDate(new Date());
				attachment.setCreateDate(new Date());
				attachment.setCreator(user);
				attachment.setFilename(filename);
				attachment.setFiletype(filetype);
				attachment.setExt(ext);
				long size = multipartFile.getSize();
				String unit = "B";
				if(size>1024*1024) {
					size = size/(1024*1024);
					unit = "MB";
				}else if(size>1024) {
					size = size/(1024);
					unit = "KB";
				} 
				String filesize = size + " " + unit;
				attachment.setFilesize(filesize);
				String md5 = DigestUtils.md5DigestAsHex(multipartFile.getBytes());
				attachment.setMd5(md5);
				
				if(filetype.toLowerCase().contains("image/")) {  //上传文件为图片
					BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
					int width = bufferedImage.getWidth();
					int height = bufferedImage.getHeight();
					int newWidth = width , newHeight = height;
					float wmo = 0 , hmo = 0;
					if(config.getMaxWidth()!=null) {						
						wmo = (width-config.getMaxWidth())*1.0F/config.getMaxWidth();
					}
					if(config.getMaxHeight()!=null) {						
						hmo = (height-config.getMaxHeight())*1.0F/config.getMaxHeight();
					}
					if(wmo>hmo) {
						if(wmo>0) {
							newWidth = config.getMaxWidth();
							newHeight = (int)(height - height*wmo);
						}
					}else {
						if(hmo>0) {
							newWidth = (int)(width-width*hmo);
							newHeight = config.getMaxHeight();
						}
					}
					Image image = bufferedImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
					BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
					Graphics g = outputImage.getGraphics();  
		            g.drawImage(image, 0, 0, null);  
		            g.dispose();  
		            ImageIO.write(outputImage, ext, new File(path + "/image/" + newFileName + "." + ext));
		            
		            
		            int snewWidth = width , snewHeight = height;
					float swmo = 0 , shmo = 0;
					if(config.getSmallMaxWidth()!=null) {						
						swmo = (width-config.getSmallMaxWidth())*1.0F/config.getSmallMaxWidth();
					}
					if(config.getSmallMaxHeight()!=null) {						
						shmo = (height-config.getSmallMaxHeight())*1.0F/config.getSmallMaxHeight();
					}
					if(swmo>shmo) {
						if(swmo>0) {
							snewWidth = config.getSmallMaxWidth();
							snewHeight = (int)(height - height*swmo);
						}
					}else {
						if(shmo>0) {
							snewWidth = (int)(width-width*shmo);
							snewHeight = config.getSmallMaxHeight();
						}
					}
					image = bufferedImage.getScaledInstance(snewWidth, snewHeight, Image.SCALE_DEFAULT);
					outputImage = new BufferedImage(snewWidth, snewHeight, BufferedImage.TYPE_INT_ARGB);
					g = outputImage.getGraphics();  
		            g.drawImage(image, 0, 0, null);  
		            g.dispose();  
		            ImageIO.write(outputImage, ext, new File(path + "/image/" + newFileName + "_s." + ext));
		            attachment.setPath(path + "/image/" + newFileName + "." + ext);
				}else {
					Attachment attachment2 = attachmentDao.findFirstByMd5(md5);
					if(attachment2==null) {
						multipartFile.transferTo(new File(path + newFileName + "." + ext));
						attachment.setPath(path + newFileName + "." + ext);
					}else {
						attachment.setFilesize(attachment2.getFilesize());
						attachment.setPath(attachment2.getPath());
					}
				}
				attachmentDao.save(attachment);
				list.add(AttachmentRes.transform(attachment));
			}
			return list;
		} catch(IOException e) {
			throw new ServiceException("文件读写异常！", e);
		}catch (IllegalStateException e) {
			throw new ServiceException("文件读写异常！", e);
		}catch (Exception e) {
			throw new ServiceException("文件上传错误！", e);
		}
	}
	
	@Transactional
	public AttachmentRes download(Integer attachmentId) {
		try {
			if(attachmentId==null) {
				throw new ServiceException("该文件不存在！");
			}
			Optional<Attachment> optional = attachmentDao.findById(attachmentId);
			if(!optional.isPresent()) {
				throw new ServiceException("该文件不存在！");
			}
			Attachment attachment = optional.get();
			AttachmentRes res = new AttachmentRes();
			res.setPath(attachment.getPath());
			res.setFilename(attachment.getFilename());
			return res;
		} catch (Exception e) {
			throw new ServiceException("下载失败！",e);
		}
	}
	
}
