package com.swarm.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * 商家用户字典表
 * @author Administrator
 *
 */
@Entity
@Getter
@Setter
public class BusDict extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5211693433116247116L;
	
	/**
	 * 字典类型，如：bank_type(银行类型:中国银行，建设银行，工商银行等),shop_type(商城类型：建材，家居，电器)，
	 * region_type(地区类型：河南省，郑州市，洛阳市，开封市)，
	 * Withdrawal_set(提现设置：开启微信提现，开启支付宝提现，开启银行卡提现,提现最低金额，提现手续费率百分比)，
	 * order_set(订单配置：快递配送，到店自提，运费)
	 */
	@Enumerated(EnumType.ORDINAL)
	private SysDictType type;
	
	/**
	 * 同一类型下key唯一
	 */
	@Column(unique = true)
	private String key;
	
	/**
	 * 值
	 */
	private String value;
	
	/**
	 * 值2
	 */
	private String value2;
	
	/**
	 * 描述说明
	 */
	private String desc;
	
	/**
	 * 顺序
	 */
	private int sort;
	
	/**
	 * 关联的父级，如地域类型region_type，需要关联上下级
	 */
	@ManyToOne
	@JoinColumn
	private BusDict parent;
	
	/**
	 * 商家用户ID，分表分库字段
	 */
	private Integer busUserId;
	
}
