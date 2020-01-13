package com.swarm.app.vo;

import java.io.Serializable;
import java.util.List;

import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndexRes implements VO , Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2218470579665579899L;
	
	private List<VO> carousel;
	
	private List<VO> menu;
	
	private VO advertising;
	
	private List<VO> salesRules;

	private List<BusCouponRes> coupon;
	
}
