package com.swarm.web.vo;

import com.swarm.base.entity.BusDict;
import com.swarm.base.entity.DictType;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusDictRes extends Res<BusDict> {
	
	/**
	 * 字典类型，如：bank_type(银行类型:中国银行，建设银行，工商银行等),shop_type(商城类型：建材，家居，电器)，
	 * region_type(地区类型：河南省，郑州市，洛阳市，开封市)，
	 * Withdrawal_set(提现设置：开启微信提现，开启支付宝提现，开启银行卡提现,提现最低金额，提现手续费率百分比)，
	 * order_set(订单配置：快递配送，到店自提，运费)
	 */
	private DictType type;
	
	/**
	 * 同一类型下key唯一
	 */
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
	private Integer sort;
	
	/**
	 * 关联的父级，如地域类型region_type，需要关联上下级
	 */
	private Integer parentId;

	@Override
	public VO apply(BusDict t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.type = t.getType();
		this.key = t.getKey();
		this.value = t.getValue();
		this.value2 = t.getValue2();
		this.desc = t.getDesc();
		this.sort = t.getSort();
		if(t.getParent()!=null) {			
			this.parentId = t.getParent().getId();
		}
		return this;
	}
	
}
