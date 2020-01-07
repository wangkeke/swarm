package com.swarm.app.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.BusWeUserAddress;
import com.swarm.base.vo.UpdateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBusWeUserAddressReq extends UpdateReq<BusWeUserAddress> {
	
	@NotNull(message = "ID不能为空！")
	private Integer id;
	
	/**
	 * 地址区域
	 */
	@NotBlank(message = "请选择区域地址！")
	private String address;
	
	/**
	 * 位置/定位
	 */
	@NotBlank(message = "请输入地址详情！")
	private String location;
	
	/**
	 * 首选地址
	 */
	private Boolean first;
	
	/**
	 * 联系人
	 */
	@NotBlank(message = "请输入联系人姓名！")
	private String contact;
	
	/**
	 * 手机号
	 */
	@NotBlank(message = "请输入手机号！")
	private String phone;

	@Override
	public void update(BusWeUserAddress t) {
		t.setUpdateDate(new Date());
		t.setAddress(this.address);
		t.setLocation(this.location);
		t.setContact(this.contact);
		t.setPhone(this.phone);
	}
	


}
