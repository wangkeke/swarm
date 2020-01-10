package com.swarm.base.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.swarm.base.entity.BusOrder;
import com.swarm.base.entity.BusWechatUser;
import com.swarm.base.entity.DictType;
import com.swarm.base.entity.SalesRuleType;
import com.swarm.base.vo.VO;

public abstract class SalesRuleHandler {
	
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(VO.DEFAULT_DATE_PATTERN);
	
	private static Map<SalesRuleType, SalesRuleHandler> map = new HashMap<SalesRuleType, SalesRuleHandler>();
	
	public abstract SalesRuleType getRuleType();
	
	public abstract DictType getRuleSetType();
	
	public abstract void handle(BusOrder busOrder , BusWechatUser busWechatUser , Integer busUserId , BigDecimal amount , Object... args);
	
	public void regist(SalesRuleHandler handler) {
		map.put(handler.getRuleType(), handler);
	}
	
	public static SalesRuleHandler getHandler(SalesRuleType type) {
		return map.get(type);
	}
	
	public static boolean existHandler(SalesRuleType type) {
		return map.containsKey(type);
	}
	
}
