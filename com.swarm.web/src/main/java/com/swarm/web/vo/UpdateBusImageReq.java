package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.BusImage;
import com.swarm.base.vo.UpdateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBusImageReq extends UpdateReq<BusImage> {
	
	@NotNull(message = "ID不能为空！")
	private Integer id;
	
	@NotBlank(message = "请上传图片！")
	private String path;
	
	private Integer sort;

	@Override
	public void update(BusImage t) {
		t.setUpdateDate(new Date());
		t.setPath(this.path);
		if(this.sort!=null) {
			t.setSort(this.sort);
		}
	}
	
}
