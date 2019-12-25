package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.swarm.base.entity.BusAdvertising;
import com.swarm.base.vo.CreateReq;
import com.swarm.web.CurrentUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusAdvertisingReq extends CreateReq<BusAdvertising> {
	
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
	public BusAdvertising create() {
		BusAdvertising busAdvertising = new BusAdvertising();
		busAdvertising.setUpdateDate(new Date());
		busAdvertising.setCreateDate(new Date());
		busAdvertising.setAdPath(this.adPath);
		busAdvertising.setBusUserId(CurrentUser.getBusUser().getId());
		busAdvertising.setEnable(this.enable);
		busAdvertising.setEndDate(this.endDate);
		busAdvertising.setStartDate(this.startDate);
		return busAdvertising;
	}

}
