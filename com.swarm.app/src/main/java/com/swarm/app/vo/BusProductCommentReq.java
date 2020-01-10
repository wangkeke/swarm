package com.swarm.app.vo;

import javax.validation.constraints.NotNull;

import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusProductCommentReq implements VO {
	
	@NotNull(message = "请选择购买的商品！")
	private Integer[] id;
	
	@NotNull(message = "评价内容不能为空！")
	private String[] content;
	
	@NotNull(message = "请选择是否上传图片！")
	private Boolean[] hasImg;
	
	private String[] image;

}
