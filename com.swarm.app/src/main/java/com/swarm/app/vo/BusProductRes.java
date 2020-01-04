package com.swarm.app.vo;

import java.math.BigDecimal;

import com.swarm.base.entity.BusProduct;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusProductRes extends Res<BusProduct> {
	
	/**
	 * 商品分类
	 */
	private VO category;
	
	/**
	 * 商品标题
	 */
	private String title;
	
	/**
	 * 价格
	 */
	private BigDecimal price;
	
	/**
	 * 总数/库存数
	 */
	private Integer stocks;
	
	/**
	 * 销量
	 */
	private Integer sales;
	
	/**
	 * 人气收藏
	 */
	private Integer favorite;
	
	/**
	 * 首图
	 */
	private String image;
	
	/**
	 * 商品相册
	 */
	private String images;
	
	/**
	 * 商品内容
	 */
	private String content;
	
	/**
	 * 是否上架
	 */
	private Boolean show;
	
	/**
	 * 标签，如热销，折扣，爆款等
	 */
	private BusLabelRes label;
	
	@Override
	public VO apply(BusProduct t) {
		this.id = t.getId();
		this.title = t.getTitle();
		this.price = t.getPrice();
		this.stocks = t.getStocks();
		this.sales = t.getSales();
		this.favorite = t.getFavorite();
//		this.content = t.getContent();
//		this.show = t.isShow();
		if(t.getLabel()!=null) {
			this.label = new BusLabelRes();
			this.label.apply(t.getLabel());
		}
//		this.category = new BusCategoryRes().apply(t.getCategory());
		this.image =  t.getImage();
//		this.images = t.getImages();
		return this;
	}

}
