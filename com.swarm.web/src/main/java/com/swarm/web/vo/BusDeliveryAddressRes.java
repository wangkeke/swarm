package com.swarm.web.vo;

import com.swarm.base.entity.BusDeliveryAddress;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusDeliveryAddressRes extends Res<BusDeliveryAddress> {
	
	/**
	 * 发货点名称
	 */
	private String name;
	
	/**
	 * 门店电话
	 */
	private String phone;
	
	/**
	 * 门店地址
	 */
	private String address;
	
	/**
	 * 是否启用
	 */
	private Boolean enable;
	
	@Override
	public VO apply(BusDeliveryAddress t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.name = t.getName();
		this.phone = t.getPhone();
		this.address = t.getAddress();
		this.enable = t.isEnable();
		return this;
	}

}
