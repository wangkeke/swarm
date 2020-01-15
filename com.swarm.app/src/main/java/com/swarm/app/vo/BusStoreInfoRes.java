package com.swarm.app.vo;

import java.util.List;

import com.swarm.base.entity.BusStoreInfo;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusStoreInfoRes extends Res<BusStoreInfo> {
	
	
	private List<VO> types;
	
	/**
	 * 商铺类型
	 */
	private SysDictRes type;
	
	/**
	 * 商铺名称
	 */
	private String storeName;
	
	/**
	 * 上传的logo路径(200px*200px)
	 */
	private String logo;
	
	/**
	 * 联系人称呼
	 */
	private String contact;
	
	/**
	 * 联系人电话
	 */
	private String phone;
	
	/**
	 * 微信号
	 */
	private String wechat;
	
	
	/**
	 * 提供的服务/业务
	 */
	private String business;
	
	/**
	 * 商家店铺实拍轮播图片
	 */
	private String carousel;
	
	/**
	 * 商家店铺内景图片
	 */
	private String scene;
	
	/**
	 * 商家地址
	 */
	private String address;
	
	/**
	 * 商家描述
	 */
	private String introduce;
	
	@Override
	public VO apply(BusStoreInfo t) {
		this.type = (SysDictRes)new SysDictRes().apply(t.getType());
		this.storeName = t.getStoreName();
		this.logo = t.getLogo();
		this.contact = t.getContact();
		this.phone = t.getPhone();
		this.wechat = t.getWechat();
		this.business = t.getBusiness();
		this.carousel = t.getCarousel();
		this.scene = t.getScene();
		this.address = t.getAddress();
		this.introduce = t.getIntroduce();
		return this;
	}

}
