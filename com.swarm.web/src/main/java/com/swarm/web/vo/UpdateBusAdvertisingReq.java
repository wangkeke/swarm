package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.swarm.base.entity.BusAdvertising;
import com.swarm.base.vo.UpdateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBusAdvertisingReq extends UpdateReq<BusAdvertising> {
	
	@NotNull(message = "ID不能为空！")
	private Integer id;
	
	/**
	 * 广告图片上传路径
	 */
	@NotBlank(message = "请上传广告图片！")
	private String adPath;
	
	/**
	 * 开始日期，yyyy-MM-dd
	 */
	@NotNull(message = "开始日期不能为空！")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	
	/**
	 * 结束日期，yyyy-MM-dd
	 */
	@NotNull(message = "结束日期不能为空！")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	
	/**
	 * 是否启用
	 */
	@NotNull(message = "是否启用不能为空！")
	private Boolean enable;
	
	
	@Override
	public void update(BusAdvertising t) {
		t.setAdPath(this.adPath);
		t.setStartDate(this.startDate);
		t.setEnable(this.enable);
		t.setUpdateDate(new Date());
		t.setEndDate(this.endDate);
	}

}
