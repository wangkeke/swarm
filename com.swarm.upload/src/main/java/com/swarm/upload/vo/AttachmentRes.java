package com.swarm.upload.vo;

import javax.persistence.Column;

import com.swarm.base.entity.Attachment;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentRes implements VO{
	
	
	private Integer userId;
	
	/**
	 * 用户上传的文件名称
	 */
	private String filename;
	
	/**
	 * 上传文件的大小,KB/MB
	 */
	private String filesize;
	
	/**
	 * 文件类型
	 */
	private String filetype;
	
	/**
	 * 文件扩展名
	 */
	private String ext;
	
	/**
	 * 文件下载路径
	 */
	@Column(nullable = false , length = 100)
	private String path;
	
	/**
	 * 文件下载次数
	 */
	private int download;
	
	public static final AttachmentRes transform(Attachment attachment) {
		AttachmentRes res = new AttachmentRes();
		res.setDownload(attachment.getDownloads());
		res.setFilename(attachment.getFilename());
		res.setFilesize(attachment.getFilesize());
		res.setFiletype(attachment.getFiletype());
		res.setPath(attachment.getPath());
		res.setUserId(attachment.getCreator().getId());
		return res;
	}
	
}
