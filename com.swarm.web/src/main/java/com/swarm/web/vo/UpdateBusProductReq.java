package com.swarm.web.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.swarm.base.entity.BusProduct;
import com.swarm.base.vo.UpdateReq;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBusProductReq extends UpdateReq<BusProduct> {
	
	@NotNull(message = "ID不能为空！")
	private Integer id;
	
	/**
	 * 商品分类
	 */
	@NotNull(message = "请选择商品分类！")
	private Integer categoryId;
	
	/**
	 * 商品标题
	 */
	@NotBlank(message = "商品标题不能为空！")
	@Size(max = 30 , message = "商品标题长度不能超过30位！")
	private String title;

	
	/**
	 * 价格
	 */
	@NotNull(message = "价格不能为空！")
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal price;
	
	/**
	 * 总数/库存数
	 */
	@NotNull(message = "库存总数不能为空！")
	private Integer stocks;
	
	/**
	 * 初始销量，可手动修改销量
	 */
	private Integer sales;
	
	/**
	 * 商品首图
	 */
	@NotBlank(message = "请选择商品首图！")
	private String image;
	
	/**
	 * 商品相册
	 */
	@NotBlank(message = "请上传商品图片！")
	private String[] images;
	
	/**
	 * 商品内容
	 */
	@NotBlank(message = "商品内容不能为空！")
	private String content;
	
	/**
	 * 是否上架
	 */
	@NotNull(message = "请设置是否上架！")
	private Boolean show;

	/**
	 * 标签，如热销，折扣，爆款等
	 */
	private Integer busLabelId;
	
	
	@Override
	public void update(BusProduct t) {
		t.setUpdateDate(new Date());
		t.setTitle(this.title);
		t.setPrice(this.price);
		t.setStocks(this.stocks);
		if(this.sales!=null)
			t.setSales(this.sales);
		t.setContent(this.content);
		t.setShow(this.show);
		t.setImage(this.image);
		t.setImages(String.join(",", this.images));
	}

}
