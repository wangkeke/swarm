package com.swarm.app.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.swarm.base.entity.SysBusApply;
import com.swarm.base.vo.CreateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysBusApplyReq extends CreateReq<SysBusApply> {
	
	/**
	 * 联系人
	 */
	@NotBlank(message = "请输入联系人！")
	private String contact;
	
	/**
	 * 手机号
	 */
	@NotBlank(message = "请输入手机号！")
	private String phone;
	
	/**
	 * 所属行业
	 */
	@NotBlank(message = "请输入所属行业！")
	private String business;
	
	@Override
	public SysBusApply create() {
		SysBusApply sysBusApply = new SysBusApply();
		sysBusApply.setUpdateDate(new Date());
		sysBusApply.setCreateDate(new Date());
		sysBusApply.setContact(this.contact);
		sysBusApply.setPhone(this.phone);
		sysBusApply.setBusiness(this.business);
		return sysBusApply;
	}
	
}
