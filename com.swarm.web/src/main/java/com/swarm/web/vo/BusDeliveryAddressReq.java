package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.BusDeliveryAddress;
import com.swarm.base.vo.CreateReq;
import com.swarm.web.CurrentUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusDeliveryAddressReq extends CreateReq<BusDeliveryAddress> {
	
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
	public BusDeliveryAddress create() {
		BusDeliveryAddress address = new BusDeliveryAddress();
		address.setUpdateDate(new Date());
		address.setCreateDate(new Date());
		address.setName(this.name);
		address.setPhone(this.phone);
		address.setAddress(this.address);
		address.setEnable(this.enable);
		address.setBusUserId(CurrentUser.getBusUserId());
		return address;
	}

}
