package com.swarm.app.vo;

import com.swarm.base.entity.BusProductComment;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusProductCommentRes extends Res<BusProductComment> {
	
	/**
	 * 评论内容
	 */
	private String content;
	
	/**
	 * 评论图片；多个图片以逗号分割
	 */
	private String images;
	
	@Override
	public VO apply(BusProductComment t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.content = t.getContent();
		this.images = t.getImages();
		return this;
	}

}
