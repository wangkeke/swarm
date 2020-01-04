package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.swarm.base.entity.BusImage;
import com.swarm.base.entity.BusImageType;
import com.swarm.base.vo.CreateReq;
import com.swarm.web.CurrentUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusImageReq extends CreateReq<BusImage> {
	
	/**
	 * 图片类型
	 */
	@NotBlank(message = "图片类型不能为空！")
	private BusImageType busImageType;
	
	/**
	 * 图片路径
	 */
	@NotBlank(message = "请上传图片！")
	private String path;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	@Override
	public BusImage create() {
		BusImage busImage = new BusImage();
		busImage.setBusUserId(CurrentUser.getBusUserId());
		busImage.setCreateDate(new Date());
		busImage.setUpdateDate(new Date());
		busImage.setBusImageType(this.busImageType);
		busImage.setPath(this.path);
		if(this.sort!=null) {
			busImage.setSort(this.sort);
		}
		return busImage;
	}

}
