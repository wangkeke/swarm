package com.swarm.web.vo;

import com.swarm.base.entity.BusWeApiInfo;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusWeApiInfoRes extends Res<BusWeApiInfo> {
	
	/**
	 * 小程序二维码路径
	 */
	private String mnqrcode;
	
	/**
	 * 小程序二维码素材路径
	 */
	private String mnqrcodeMater;
	
	/**
	 * 支付二维码路径
	 */
	private String payqrcode;

	@Override
	public VO apply(BusWeApiInfo t) {
		this.id = t.getId();
		this.createDate = t.getCreateDate();
		this.updateDate = t.getUpdateDate();
		this.mnqrcode = t.getMnqrcode();
		this.mnqrcodeMater = t.getMnqrcodeMater();
		this.payqrcode = t.getPayqrcode();
		return this;
	}
	
}
