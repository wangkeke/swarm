package com.swarm.base.entity;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 商铺分类
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class SysShopType extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4834782256158766883L;
	
	private String typeName;
	
	/**
	 * 删除标识：-1表示删除，0表示正常
	 */
	private int flag;
	
}
