package com.swarm.admin.vo;

import com.swarm.base.entity.SysBusApply;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysBusApplyRes extends Res<SysBusApply> {
	
	/**
	 * 联系人
	 */
	private String contact;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 所属行业
	 */
	private String business;
	
	/**
	 * 0：申请，1已处理
	 */
	private Integer status;

	@Override
	public VO apply(SysBusApply t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.contact = t.getContact();
		this.phone = t.getPhone();
		this.business = t.getBusiness();
		this.status = t.getStatus();
		return this;
	}
	
	
	
}
