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
		if(ostr.length()>5) {
			ostr = ostr.substring(ostr.length()-5) + (ostr.length()-5);
		}if(ostr.length()==5){
			ostr = ostr.length()+ostr;
		}else {
			ostr = ostr.length()+ostr;
			char[] chars = new char[6];
			for (int i = 0; i < chars.length; i++) {
				int r = RandomUtils.nextInt(48, 58);
				chars[i] = (char)r;
			}
			ostr = ostr + String.valueOf(chars).substring(ostr.length());
		}
		return ostr;
	}
	

}
