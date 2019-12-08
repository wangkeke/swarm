package com.swarm.base.entity;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.RandomUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 提货码
 * @author Administrator
 *
 */
@Getter
@Setter
@Entity
public class BusPickcode extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6345980804957112974L;
	
	
	/**
	 * 源订单，该订单提货类型必须是自提类型
	 */
	@ManyToOne
	@JoinColumn
	private BusOrder busOrder;
	
	/**
	 * 提货人姓名
	 */
	private String name;
	
	/**
	 * 提货人手机号
	 */
	private String phone;
	
	/**
	 * 提货码，生成方式（订单ID，+ 随机补齐六位的字符大写字符串）
	 */
	private String pickCode;
	
	/**
	 * 是否已提货
	 */
	private boolean used;
	
	/**
	 * 备注，提货成功后添加备注，以防后期产生纠纷
	 */
	private String remark;
	
	/**
	 * 商家用户ID，分表分库字段
	 */
	private Integer busUserId;
	
	/**
	 * 生成提货码
	 * @param orderId
	 * @return
	 */
	public static String generateCode(Integer orderId) {
		String ostr = String.valueOf(orderId);
		if(ostr.length()>=7) {
			ostr = Integer.toHexString(orderId).toUpperCase();
			if(ostr.length()<6) {
				ostr += "XXXXXX".substring(0,6-ostr.length());
			}
		}else if(ostr.length()<6){
			ostr = ostr.length()+ostr;
			while(ostr.length()<6) {
				ostr += RandomUtils.nextInt(0, 10);
			}
		}
		return ostr;
	}
	
}
