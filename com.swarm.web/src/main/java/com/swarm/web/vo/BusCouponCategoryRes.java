package com.swarm.web.vo;

import com.swarm.base.entity.BusCouponCategory;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusCouponCategoryRes extends Res<BusCouponCategory> {
	
	/**
	 * 优惠券
	 */
	private Integer busCouponId;
	
	/**
	 * 商品类型
	 */
	private Integer busCategoryId;
	
	@Override
	public VO apply(BusCouponCategory t) {
		this.busCouponId = t.getBusCoupon().getId();
		this.busCategoryId = t.getBusCategory().getId();
		return this;
	}

}
