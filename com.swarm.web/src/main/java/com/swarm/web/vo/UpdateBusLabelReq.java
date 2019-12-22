package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.BusLabel;
import com.swarm.base.vo.UpdateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBusLabelReq extends UpdateReq<BusLabel> {
	
	@NotNull(message = "ID不能为空！")
	private Integer id;
	
	@NotBlank(message = "标签名称不能为空！")
	private String label;
	
	@NotBlank(message = "优先级不能为空！")
	private Integer sort;
	
	@Override
	public void update(BusLabel t) {
		t.setUpdateDate(new Date());
		t.setLabel(this.label);
		t.setSort(this.sort);
	}

}
