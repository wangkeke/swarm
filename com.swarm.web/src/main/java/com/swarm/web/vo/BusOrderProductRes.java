package com.swarm.web.vo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.swarm.base.entity.BusOrder;
import com.swarm.base.entity.BusOrderProduct;
import com.swarm.base.entity.BusProduct;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Setter;

import lombok.Getter;

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
		return this;
	}
	
}
