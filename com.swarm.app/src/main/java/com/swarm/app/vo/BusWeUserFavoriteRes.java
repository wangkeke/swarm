package com.swarm.app.vo;

import com.swarm.base.entity.BusWeUserFavorite;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusWeUserFavoriteRes extends Res<BusWeUserFavorite> {
	
	private BusProductRes busProduct;
	
	@Override
	public VO apply(BusWeUserFavorite t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.busProduct = new BusProductRes();
		this.busProduct.apply(t.getBusProduct());
		return this;
	}

}
