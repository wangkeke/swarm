package com.swarm.app.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.swarm.base.entity.BusWeUserAddress;
import com.swarm.base.vo.CreateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusWeUserAddressReq extends CreateReq<BusWeUserAddress> {
	
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
	public BusWeUserAddress create() {
		BusWeUserAddress address = new BusWeUserAddress();
		address.setUpdateDate(new Date());
		address.setCreateDate(new Date());
		address.setAddress(this.address);
		address.setLocation(this.location);
		if(this.first==null) {
			this.first = false;
		}
		address.setFirst(this.first);
		address.setContact(this.contact);
		address.setPhone(this.phone);
		return address;
	}

}
