package com.swarm.app.vo;

import java.math.BigDecimal;

import com.swarm.base.entity.BusWeUserWallet;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

/**
 * 商家微信用户钱包
 * @author Administrator
 *
 */
@Getter
@Setter
public class BusWeUserWalletRes extends Res<BusWeUserWallet> {

	
	/**
	 * 账户余额
	 */
	private BigDecimal balance;

	@Override
	public VO apply(BusWeUserWallet t) {
		this.balance = t.getBalance();
		return this;
	}
	
}
