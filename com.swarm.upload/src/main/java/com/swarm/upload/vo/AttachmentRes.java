package com.swarm.upload.vo;

import com.swarm.base.entity.Attachment;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentRes extends Res<Attachment>{
	
	/**
	 * 用户上传的文件名称
	 */
	private String filename;
	
	/**
	 * 上传文件的大小,KB/MB
	 */
	private String filesize;
	
	/**
	 * 图片宽，仅限图片类型
	 */
	private Integer width;
	
	/**
	 * 图片高，仅限图片类型
	 */
	private Integer height;
	
	/**
	 * 文件扩展名
	 */
	private String ext;
	
	/**
	 * md5校验码
	 */
	private String md5;
	
	/**
	 * 文件类型
	 */
	private String filetype;
	
	/**
	 * 文件下载路径
	 */
	private String path;
	
	/**
	 * 文件下载次数
	 */
	private Integer downloads;
	


	@Override
	public VO apply(Attachment t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.filename = t.getFilename();
		this.filesize = t.getFilesize();
		this.width = t.getWidth();
		this.height = t.getHeight();
		this.ext = t.getExt();
		this.md5 = t.getMd5();
		this.filetype = t.getFiletype();
		this.path = t.getPath();
		this.downloads = t.getDownloads();
		return this;
	}
	
}
