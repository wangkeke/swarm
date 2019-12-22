package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.swarm.base.entity.BusLabel;
import com.swarm.base.vo.CreateReq;
import com.swarm.web.CurrentUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusLabelReq extends CreateReq<BusLabel> {
	
	@NotBlank(message = "标签名称不能为空！")
	private String label;
	
	@NotBlank(message = "优先级不能为空！")
	private Integer sort;
	
	@Override
	public BusLabel create() {
		BusLabel busLabel = new BusLabel();
		busLabel.setUpdateDate(new Date());
		busLabel.setCreateDate(new Date());
		busLabel.setBusUserId(CurrentUser.getBusUserId());
		busLabel.setLabel(this.label);
		busLabel.setSort(this.sort);
		return busLabel;
	}

}
