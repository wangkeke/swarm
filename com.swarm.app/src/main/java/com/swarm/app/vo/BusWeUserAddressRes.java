package com.swarm.app.vo;

import com.swarm.base.entity.BusWeUserAddress;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusWeUserAddressRes extends Res<BusWeUserAddress> {
	
	/**
	 * 地址区域
	 */
	private String address;
	
	/**
	 * 位置/定位
	 */
	private String location;
	
	/**
	 * 首选地址
	 */
	private Boolean first;
	
	/**
	 * 联系人
	 */
	private String contact;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	@Override
	public VO apply(BusWeUserAddress t) {
		this.id = t.getId();
//		this.updateDate = t.getUpdateDate();
//		this.createDate = t.getCreateDate();
		this.address = t.getAddress();
		this.location = t.getLocation();
		this.first = t.isFirst();
		this.contact = t.getContact();
		this.phone = t.getPhone();
		return this;
	}

}
