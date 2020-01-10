package com.swarm.base.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.base.dao.BusCashbackDao;
import com.swarm.base.dao.BusCashbackRecordDao;
import com.swarm.base.dao.BusDictDao;
import com.swarm.base.dao.BusWeUserWalletDao;
import com.swarm.base.entity.BusCashback;
import com.swarm.base.entity.BusCashbackRecord;
import com.swarm.base.entity.BusDict;
import com.swarm.base.entity.BusOrder;
import com.swarm.base.entity.BusWeUserWallet;
import com.swarm.base.entity.BusWechatUser;
import com.swarm.base.entity.DictType;
import com.swarm.base.entity.SalesRuleType;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CashbackRuleHandler extends SalesRuleHandler {
	
	@Autowired
	private BusDictDao busDictDao;
	
	@Autowired
	private BusCashbackDao busCashbackDao;
	
	@Autowired
	private BusCashbackRecordDao busCashbackRecordDao;

	
	@Autowired
	private BusWeUserWalletDao busWeUserWalletDao;
	
	
	@PostConstruct
	public void init() {
		regist(this);
	}
	
	@Override
	public SalesRuleType getRuleType() {
		return SalesRuleType.CASHBACK;
	}

	@Override
	public DictType getRuleSetType() {
		return DictType.CASHBACK_SET;
	}

	@Transactional
	@Override
	public void handle(BusOrder busOrder , BusWechatUser busWechatUser, Integer busUserId, BigDecimal amount, Object... args) {
		List<BusDict> busDicts = busDictDao.findByTypeAndBusUserId(getRuleSetType(), busUserId);
		Map<String, BusDict> dictMap = new HashMap<String, BusDict>();
		for (BusDict busDict : busDicts) {
			dictMap.put(busDict.getKey(), busDict);
		}
		Date currentDate = new Date();
		BusDict min_charges = dictMap.get(getRuleSetType().name().toLowerCase()+"_min_charges");   //触发返现规则的最低消费
		BusDict req_percent = dictMap.get(getRuleSetType().name().toLowerCase()+"_req_percent");   //返现总额度基于首笔订单的百分比，默认100%
		BusDict wallet_pay_enable = dictMap.get(getRuleSetType().name().toLowerCase()+"_wallet_pay_enable");   //默认关闭
		BusDict queue_enable = dictMap.get(getRuleSetType().name().toLowerCase()+"_queue_enable");        //开启队列返现  默认开启
		BusDict queue_random_enable = dictMap.get(getRuleSetType().name().toLowerCase()+"_queue_random_enable");        //根据队列顺序权重随机返现，默认关闭
		BusDict queue_random_factor = dictMap.get(getRuleSetType().name().toLowerCase()+"_queue_random_factor");        //队列顺序权重返现随机因子，默认为0
		BusDict queue_start_date = dictMap.get(getRuleSetType().name().toLowerCase()+"_queue_start_date");       //队列返现开始时间
		BusDict queue_end_date = dictMap.get(getRuleSetType().name().toLowerCase()+"_queue_end_date");       //队列返现结束时间
		BusDict queue_percent = dictMap.get(getRuleSetType().name().toLowerCase()+"_queue_percent");      //队列返现比例
		BusDict start_date = dictMap.get(getRuleSetType().name().toLowerCase()+"_start_date");       //返现开始时间
		BusDict end_date = dictMap.get(getRuleSetType().name().toLowerCase()+"_end_date");           //返现结束时间
		BusDict weuser_percent = dictMap.get(getRuleSetType().name().toLowerCase()+"_weuser_percent");   //好友返现额度百分比 , 默认是10
		BusDict activity_pointcut = dictMap.get(getRuleSetType().name().toLowerCase()+"_activity_pointcut");   //触发返现的流程切入点 , 默认是收货完成，即：订单状态已完成
		
		if(min_charges!=null && amount.doubleValue()<Double.parseDouble(min_charges.getValue())) {   //未触发返现规则
			return;
		}
		if(wallet_pay_enable==null || !"1".equals(wallet_pay_enable.getValue())) {   //禁用钱包支付返现
			if(busOrder.isWalletPay()) {
				return;
			}
		}
		//是否触发返现的流程切入点
		if((activity_pointcut==null || StringUtils.isBlank(activity_pointcut.getValue())) && busOrder.getActivityNode()!=ActivityNode.CONFIRMED) {
			return;
		}
		if(activity_pointcut!=null && StringUtils.isNotBlank(activity_pointcut.getValue())) {
			ActivityNode node = ActivityNode.valueOf(activity_pointcut.getValue());
			if(node!=busOrder.getActivityNode() && node!=ActivityNode.CONFIRMED) {				
				return;
			}
			if(node!=busOrder.getActivityNode() && node==ActivityNode.CONFIRMED) {
			 	int count = busCashbackRecordDao.countByBusOrderAndBusUserId(busOrder, busUserId);
			 	if(count > 0) {   //该订单存在返现记录，则不再返现
			 		return;
			 	}
			}
		}
		if(start_date!=null && StringUtils.isNotBlank(start_date.getValue())) {
			Date startDate = null;
			try {
				startDate = dateFormat.parse(start_date.getValue());
			} catch (ParseException e) {
				log.error("时间转换异常！", e);
				e.printStackTrace();
			}
			if(startDate!=null && currentDate.before(startDate)) {
				return;
			}
		}
		if(end_date!=null && StringUtils.isNotBlank(end_date.getValue())) {
			Date endDate = null;
			try {
				endDate = dateFormat.parse(end_date.getValue());
			} catch (ParseException e) {
				log.error("时间转换异常！", e);
				e.printStackTrace();
			}
			if(end_date!=null && currentDate.after(endDate)) {
				return;
			}
		}
		boolean queue = true;
		if(queue_enable!=null) {
			if("1".equals(queue_enable.getValue())) {
				if(queue_start_date!=null && StringUtils.isNotBlank(queue_start_date.getValue())) {
					Date queueStartDate = null;
					try {
						queueStartDate = dateFormat.parse(queue_start_date.getValue());
					} catch (ParseException e) {
						log.error("时间转换异常！", e);
						e.printStackTrace();
					}
					if(queueStartDate!=null && currentDate.before(queueStartDate)) {
						queue = false;
					}
				}
				if(queue_end_date!=null && StringUtils.isNotBlank(queue_end_date.getValue())) {
					Date endDate = null;
					try {
						endDate = dateFormat.parse(queue_end_date.getValue());
					} catch (ParseException e) {
						log.error("时间转换异常！", e);
						e.printStackTrace();
					}
					if(endDate!=null && currentDate.before(endDate)) {
						queue = true;
					}
				}
			}else {
				queue = false;
			}
		}
		
		BusCashback busCashback = busCashbackDao.findFirstByBusWechatUserAndBusUserIdOrderByIdDesc(busWechatUser, busUserId);
		BigDecimal newAmount = null;
		if(req_percent!=null && StringUtils.isNotBlank(req_percent.getValue())) {
			newAmount = amount.multiply(new BigDecimal(Integer.parseInt(req_percent.getValue())/100.0D));
		}else {
			newAmount = amount;
		}
		if(busCashback==null) {
			busCashback = new BusCashback();
			busCashback.setCreateDate(currentDate);
			busCashback.setUpdateDate(currentDate);
			busCashback.setBusUserId(busUserId);
			busCashback.setBusWechatUser(busWechatUser);
			busCashback.setHasCashback(new BigDecimal(0));
			busCashback.setReqCashback(newAmount);
			busCashback.setActivityNode(ActivityNode.CASHBACKING);
			busCashbackDao.save(busCashback);
		}else {
			if(busCashback.getActivityNode()!=ActivityNode.CANCELLED) {
				busCashbackDao.updateReqcashbackByIdAndBusUserId(busCashback.getId(), busUserId, newAmount, busCashback.getReqCashback() , currentDate , ActivityNode.CASHBACKING);
			}
		}
		//父级返现
		BusWechatUser parent = busWechatUser.getParent();
		BigDecimal cashbackMoney = null;
		boolean finish = false;
		BusCashback currentCashback = null;
		int percent = 10;
		if(parent == null) {   //不存在父级则进行队列返现
			if(queue) {
				if(queue_random_enable!=null && "1".equals(queue_random_enable.getValue())) {
					List<BusCashback> list = busCashbackDao.queryByBusUserIdAndActivityNodeAndIdLessEqualAndParentNot(busUserId, ActivityNode.CASHBACKING, busCashback.getId());
					int factor = 0;
					if(queue_random_factor!=null && StringUtils.isNotBlank(queue_random_factor.getValue())) {
						factor = Integer.parseInt(queue_random_factor.getValue());
					}
					currentCashback = list.get(weightRandom(list.size(),factor));
				}else {
					currentCashback = busCashbackDao.queryFirstByBusUserIdAndActivityNode(busUserId, ActivityNode.CASHBACKING);
				}
				if(queue_percent!=null && StringUtils.isNotBlank(queue_percent.getValue())) {
					percent = Integer.parseInt(queue_percent.getValue());
					if(percent<0) {
						percent = 0;
					}
				}
			}
		}else {   //存在父级则进行好友返现
			currentCashback = busCashbackDao.findFirstByBusWechatUserAndBusUserIdAndActivityNodeOrderByIdDesc(parent, busUserId, ActivityNode.CASHBACKING);
			if(weuser_percent!=null && StringUtils.isNotBlank(weuser_percent.getValue())) {
				percent = Integer.parseInt(weuser_percent.getValue());
				if(percent<0) {
					percent = 0;
				}
			}
		}
		
		if(currentCashback==null) {   //没有需要返现的金额，不返现
			return;
		}
		BigDecimal subtract = currentCashback.getReqCashback().subtract(currentCashback.getHasCashback());
		if(subtract.doubleValue()<=0) {
			return;
		}
		
		cashbackMoney = amount.multiply(new BigDecimal(percent/100.0D));
		if(cashbackMoney.doubleValue()>=subtract.doubleValue()) {
			cashbackMoney = subtract;
			finish = true;
		}
		BusCashbackRecord busCashbackRecord = new BusCashbackRecord();
		busCashbackRecord.setUpdateDate(currentDate);
		busCashbackRecord.setCreateDate(currentDate);
		busCashbackRecord.setBusCashback(currentCashback);
		busCashbackRecord.setBusOrder(busOrder);
		busCashbackRecord.setBusUserId(busUserId);
		busCashbackRecord.setBusWechatUser(currentCashback.getBusWechatUser());
		busCashbackRecord.setCashbackMoney(cashbackMoney);
		busCashbackRecordDao.save(busCashbackRecord);
		busCashbackDao.updateHascashbackByIdAndBusUserId(currentCashback.getId(), busUserId, cashbackMoney, finish?ActivityNode.CONFIRMED:ActivityNode.CASHBACKING, currentDate);
		BusWeUserWallet busWeUserWallet = busWeUserWalletDao.findByBusWechatUserAndBusUserId(busWechatUser, busUserId);
		if(busWeUserWallet==null) {
			busWeUserWallet = new BusWeUserWallet();
			busWeUserWallet.setUpdateDate(new Date());
			busWeUserWallet.setCreateDate(new Date());
			busWeUserWallet.setBalance(cashbackMoney);
			busWeUserWallet.setBusUserId(busUserId);
			busWeUserWallet.setBusWechatUser(busWechatUser);
			busWeUserWalletDao.save(busWeUserWallet);
		}else {			
			busWeUserWalletDao.rechargeBusWeUserWallet(busWeUserWallet.getId(), busUserId, cashbackMoney, busWeUserWallet.getBalance());
		}
	}
	
	private int weightRandom(int size , int factor) {
		int index = 0;
		double round = 0;
		double[] ranges = new double[size];
		int j = 0;
		for (int i = size; i > 0; i-- , j++) {
			double add = i/(j+factor);
			round += add;
			ranges[j] = add; 
		}
		double seed = RandomUtils.nextDouble(0,round);
		int add = 0;
		for (int k = 0; k < ranges.length; k++) {
			add += ranges[k];
			if(add>=seed) {
				index = k;
				break;
			}
		}
		return index;
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		list.add("f");
		list.add("g");
		list.add("h");
		list.add("i");
		list.add("j");
//		list.add("a");
		double round = 0;
		double[] ranges = new double[list.size()];
		int j = 0;
		for (int i = list.size(); i > 0; i-- , j++) {
		 	double add = i/(j+0.1);
			round += add;
			ranges[j] = add; 
		}
		
		int acount = 0 ,bcount = 0,ccount = 0 , dcount=0 , ecount = 0;
		for (int i = 0; i < 100; i++) {			
			double seed = RandomUtils.nextDouble(0, round);
//			System.out.println(seed);
			int add = 0;
			for (int k = 0; k < ranges.length; k++) {
				add += ranges[k];
				if(add>=seed) {
					if(list.get(k).equals("a")) {
						acount++;
					}
					if(list.get(k).equals("b")) {
						bcount++;
					}
					if(list.get(k).equals("c")) {
						ccount++;
					}
					if(list.get(k).equals("d")) {
						dcount++;
					}
					if(list.get(k).equals("e")) {
						ecount++;
					}
					break;
				}
			}
		}
		System.out.println("a : " +acount);
		System.out.println("b : " +bcount);
		System.out.println("c : " +ccount);
		System.out.println("d : " +dcount);
		System.out.println("e : " +ecount);
	}
	
}
