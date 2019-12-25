package com.swarm.web.vo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.swarm.base.entity.BusStoreInfo;
import com.swarm.base.vo.UpdateReq;
import com.swarm.web.CurrentUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBusStoreInfoReq extends UpdateReq<BusStoreInfo> {
	
	/**
	 * 商铺类型
	 */
	@NotNull(message = "商铺类型不能为空！")
	private Integer typeId;
	
	/**
	 * 商铺名称
	 */
	@NotBlank(message = "商铺名称不能为空！")
	private String storeName;
	
	/**
	 * 上传的logo路径(200px*200px)
	 */
	@NotBlank(message = "logo不能为空！")
	private String logo;
	
	/**
	 * 联系人称呼
	 */
	@NotBlank(message = "联系人不能为空！")
	private String contact;
	
	/**
	 * 联系人电话
	 */
	@NotBlank(message = "电话不能为空！")
	private String phone;
	
	/**
	 * 微信号
	 */
	@NotBlank(message = "微信号不能为空！")
	private String wechat;
	
	
	/**
	 * 提供的服务/业务
	 */
	@NotBlank(message = "请输入提供的服务！")
	private String business;
	
	/**
	 * 商家店铺实拍轮播图片
	 */
	@NotNull(message = "请上传商铺轮播图片！")
	private String[] carousel;
	
	/**
	 * 商家店铺内景图片
	 */
	@NotNull(message = "请上传店铺实拍图片！")
	private String[] scene;
	
	/**
	 * 商家地址
	 */
	@NotBlank(message = "请输入商家地址！")
	private String address;
	
	/**
	 * 商家描述
	 */
	private String introduce;
	
	@Override
	public void update(BusStoreInfo t) {
		t.setUpdateDate(new Date());
		t.setAddress(this.address);
		t.setBusiness(this.business);
		t.setBusUserId(CurrentUser.getBusUserId());
		t.setCarousel(String.join(",", this.carousel));
		t.setContact(this.contact);
		t.setIntroduce(this.introduce);
		t.setLogo(this.logo);
		t.setPhone(this.phone);
		t.setScene(String.join(",", this.scene));
		t.setStoreName(this.storeName);
		t.setWechat(this.wechat);
	}

}
