package com.swarm.base.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;


/**
 * 商家小程序微信用户收藏夹管理
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusWeUserFavorite extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5838654887460829635L;
	
	@ManyToOne
	@JoinColumn
	private BusWechatUser busWechatUser;
	
	@ManyToOne
	@JoinColumn
	private BusProduct busProduct;
	
	/**
	 * 商家用户ID，分库分表字段
	 */
	private Integer busUserId;
	
}
