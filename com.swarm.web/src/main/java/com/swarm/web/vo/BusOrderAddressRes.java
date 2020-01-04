package com.swarm.web.vo;

import com.swarm.base.entity.BusOrderAddress;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter
public class BusOrderAddressRes extends Res<BusOrderAddress> {
	
	/**
	 * 地址区域
	 */
	private String address;
	
	/**
	 * 位置/定位
	 */
	private String location;
	
	/**
	 * 联系人
	 */
	private String contact;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 对应的订单
	 */
	private VO busOrder;
	
	@Override
	public VO apply(BusOrderAddress t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.address = t.getAddress();
		this.location = t.getLocation();
		this.contact = t.getContact();
		this.phone = t.getPhone();
		return this;
	}

}
