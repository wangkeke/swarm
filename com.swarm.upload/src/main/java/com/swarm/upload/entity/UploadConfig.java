package com.swarm.upload.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.swarm.base.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = UploadConfig.TABLE_NAME)
public class UploadConfig extends BaseEntity{
	
	public static final String TABLE_NAME = "sys_upload_config";
	
	public static final String DEFAULT_LABEL = "default";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1702499432798373822L;

	/**
	 * 业务分类标签
	 */
	@Column(unique = true,nullable = false,length = 20)
	private String label;
	
	/**
	 * 包括的文件类型，以'，'分割
	 */
	@Column(length = 100)
	private String includeType;
	
	/**
	 * 排除的文件类型，以','分割
	 */
	@Column(length = 100)
	private String excludeType;
	
	/**
	 * 最大尺寸,MB | KB | B
	 */
	@Column(length = 10)
	private String maxSize;
	
	/**
	 * 最大宽度
	 */
	private Integer maxWidth;
	
	/**
	 * 最大高度
	 */
	private Integer maxHeight;
	
	/**
	 * 缩略图最大宽度
	 */
	private Integer smallMaxWidth;
	
	/**
	 * 缩略图最大高度
	 */
	private Integer smallMaxHeight;
	
}
