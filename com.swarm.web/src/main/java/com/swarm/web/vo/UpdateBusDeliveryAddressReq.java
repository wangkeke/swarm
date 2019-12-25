package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.BusDeliveryAddress;
import com.swarm.base.vo.UpdateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBusDeliveryAddressReq extends UpdateReq<BusDeliveryAddress> {
	
	@NotNull(message = "ID不能为空！")
	private Integer id;
	
	/**
	 * 发货点名称
	 */
	@NotBlank(message = "请输入门店名称！")
	private String name;
	
	/**
	 * 门店电话
	 */
	@NotBlank(message = "请输入门店电话！")
	private String phone;
	
	/**
	 * 门店地址
	 */
	@NotBlank(message = "请输入门店详细地址！")
	private String address;
	
	/**
	 * 是否启用
	 */
	@NotNull(message = "请选择是否启用！")
	private Boolean enable;
	
	@Override
	public void update(BusDeliveryAddress t) {
		t.setUpdateDate(new Date());
		t.setName(this.name);
		t.setPhone(this.phone);
		t.setAddress(this.address);
		t.setEnable(this.enable);
	}

}
