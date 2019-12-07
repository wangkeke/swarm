package com.swarm.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 附件，包括文件，图片
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class Attachment extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7403961606532900643L;

	/**
	 * 用户上传的文件名称
	 */
	private String filename;
	
	/**
	 * 上传文件的大小,KB/MB
	 */
	private String filesize;
	
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
	@Column(nullable = false , length = 100)
	private String path;
	
	/**
	 * 文件下载次数
	 */
	private int downloads;
	
	
	/**
	 * 上传该附件的用户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private BusUser creator;
	
	/**
	 * 商家用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
