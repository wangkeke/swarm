package com.swarm.web.vo;

import java.math.BigDecimal;

import com.swarm.base.entity.BusOrderProduct;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusOrderProductRes extends Res<BusOrderProduct> {
	
	
	/**
	 * 购买的商品
	 */
	private BusProductRes busProduct;
	
	/**
	 * 购买数量
	 */
	private Integer number;
	
	/**
	 * 商品对应的单价
	 */
	private BigDecimal price;
	
	/**
	 * 商品颜色
	 */
	private String color;
	
	/**
	 * 商品尺寸
	 */
	private String size;
	
	
	@Override
	public VO apply(BusOrderProduct t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.busProduct = new BusProductRes();
		this.busProduct.setId(t.getBusProduct().getId());
		this.busProduct.setImage(t.getBusProduct().getImage());
		this.busProduct.setTitle(t.getBusProduct().getTitle());
		this.number = t.getNumber();
		this.price = t.getPrice();
		this.color = t.getColor();
		this.size = t.getSize();
		return this;
	}
	
}
