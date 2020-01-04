package com.swarm.app.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.swarm.base.entity.BusAdvertising;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusAdvertisingRes extends Res<BusAdvertising> {
	
	/**
	 * 广告图片上传路径
	 */
	private String adPath;
	
	/**
	 * 开始日期，yyyy-MM-dd
	 */
	@JsonFormat(shape = Shape.STRING , pattern = DEFAULT_DATE_PATTERN)
	private Date startDate;
	
	/**
	 * 结束日期，yyyy-MM-dd
	 */
	@JsonFormat(shape = Shape.STRING , pattern = DEFAULT_DATE_PATTERN)
	private Date endDate;
	
	/**
	 * 是否启用
	 */
	private Boolean enable;

	@Override
	public VO apply(BusAdvertising t) {
		this.id = t.getId();
//		this.createDate = t.getCreateDate();
//		this.updateDate = t.getUpdateDate();
		this.adPath = t.getAdPath();
		this.startDate = t.getStartDate();
		this.endDate = t.getEndDate();
//		this.enable = t.isEnable();
		return this;
	}
	
}
