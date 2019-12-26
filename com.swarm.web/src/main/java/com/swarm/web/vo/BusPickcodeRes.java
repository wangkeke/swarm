package com.swarm.web.vo;

import com.swarm.base.entity.BusPickcode;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusPickcodeRes extends Res<BusPickcode> {
	
	
	/**
	 * 提货人姓名
	 */
	private String name;
	
	/**
	 * 提货人手机号
	 */
	private String phone;
	
	/**
	 * 提货码，生成方式（订单ID，+ 随机补齐六位的字符大写字符串）
	 */
	private String pickCode;
	
	/**
	 * 是否已提货
	 */
	private Boolean used;
	
	/**
	 * 备注，提货成功后添加备注，以防后期产生纠纷
	 */
	private String remark;
	
	@Override
	public VO apply(BusPickcode t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.name = t.getName();
		this.phone = t.getPhone();
		this.pickCode = t.getPickCode();
		this.used = t.isUsed();
		this.remark = t.getRemark();
		return this;
	}

}
