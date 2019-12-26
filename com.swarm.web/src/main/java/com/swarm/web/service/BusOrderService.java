package com.swarm.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.base.dao.BusOrderCouponDao;
import com.swarm.base.dao.BusOrderDao;
import com.swarm.base.dao.BusOrderProductDao;
import com.swarm.base.dao.BusPickcodeDao;
import com.swarm.base.dao.BusRecordDao;
import com.swarm.base.entity.BusOrder;
import com.swarm.base.entity.BusOrderCoupon;
import com.swarm.base.entity.BusOrderProduct;
import com.swarm.base.entity.BusPickcode;
import com.swarm.base.entity.BusRecord;
import com.swarm.base.service.Activity;
import com.swarm.base.service.ActivityNode;
import com.swarm.base.service.OrderProcess;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.ActivityRes;
import com.swarm.web.vo.BusOrderCouponRes;
import com.swarm.web.vo.BusOrderProductRes;
import com.swarm.web.vo.BusOrderRes;
import com.swarm.web.vo.BusPickcodeRes;
import com.swarm.web.vo.BusRecordRes;

@Transactional(readOnly = true)
@Service
public class BusOrderService {
	
	@Autowired
	private BusOrderDao dao;
	
	@Autowired
	private BusRecordDao busRecordDao;
	
	@Autowired
	private BusOrderCouponDao busOrderCouponDao;
	
	@Autowired
	private BusOrderProductDao busOrderProductDao;
	
	@Autowired
	private BusPickcodeDao busPickcodeDao;
	
	@Autowired
	private OrderProcess process;
	
	
	
	
	public Page<VO> page(Paging paging){
		Integer busUserId = CurrentUser.getBusUserId();
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("id")));
		Page<BusOrder> page = dao.findByBusUserId(busUserId, pageable);
		Page<VO> mapPage = page.map(new BusOrderRes());
		for (VO vo : mapPage.getContent()) {
			BusOrderRes res = (BusOrderRes)vo;
			Activity activity = process.get(res.getActivityNode());
			List<VO> nexts = null;
			for (Activity a : activity.getNexts()) {
				if(a.getOperator() == Activity.BUS_USER) {
					if(nexts==null) {
						nexts = new ArrayList<VO>();
					}
					nexts.add(new ActivityRes().apply(a));
				}
			}
			if(nexts!=null) {
				res.setButtonActivitys(nexts);
			}
		}
		return mapPage;
	}
	
	@Transactional
	public void process(Integer id ,String comment, ActivityNode node) {
		if(id==null || node==null) {
			throw new ServiceException("参数不正确！");
		}
		Integer busUserId = CurrentUser.getBusUserId();
		Optional<BusOrder> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusOrder order = optional.get();
		if(order.getBusUserId()!=busUserId) {
			throw new ServiceException("ID不存在！");
		}
		Activity activity = process.get(order.getActivityNode());
		List<Activity> nexts = activity.getNexts();
		Activity activity2 = null;
		for (Activity a : nexts) {
			if(a.getNode()==node && a.getOperator()==Activity.BUS_USER) {
				activity2 = a;
				break;
			}
		}
		if(activity2==null) {
			throw new ServiceException("不存在的流程操作！");
		}
		//添加订单流程记录
		BusRecord record = new BusRecord();
		record.setUpdateDate(new Date());
		record.setCreateDate(new Date());
		record.setActivityNode(order.getActivityNode());
		record.setBusOrder(order);
		record.setBusUser(CurrentUser.getBusUser());
		record.setBusUserId(busUserId);
		record. setComment(order.getComment());
		busRecordDao.save(record);
		//修改订单流程状态
		order.setComment(comment);
		order.setUpdateDate(new Date());
		order.setActivityNode(node);
		dao.save(order);
	}
	
	public VO details(Integer id) {
		if(id==null) {
			throw new ServiceException("ID不能为空！");
		}
		Integer busUserId = CurrentUser.getBusUserId();
		Optional<BusOrder> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusOrder order = optional.get();
		if(order.getBusUserId()!=busUserId) {
			throw new ServiceException("ID不存在！");
		}
		
		BusOrderRes busOrderRes = new BusOrderRes();
		busOrderRes.apply(order);
		
		//查询订单中使用的优惠券
		List<VO> orderCoupons = null;
		List<BusOrderCoupon> busOrderCoupons = busOrderCouponDao.findByBusOrder(order);
		for (BusOrderCoupon busOrderCoupon : busOrderCoupons) {
			if(orderCoupons==null) {
				orderCoupons = new ArrayList<VO>();
			}
			orderCoupons.add(new BusOrderCouponRes().apply(busOrderCoupon));
		}
		busOrderRes.setOrderCoupons(orderCoupons);
		
		//查询订单中的商品列表
		List<VO> productRess = null;
		List<BusOrderProduct> busOrderProducts = busOrderProductDao.findByBusOrder(order);
		for (BusOrderProduct busOrderProduct : busOrderProducts) {
			if(productRess==null) {
				productRess = new ArrayList<VO>(busOrderProducts.size());
			}
			productRess.add(new BusOrderProductRes().apply(busOrderProduct));
		}
		busOrderRes.setOrderProducts(productRess);
		
		//查询订单记录列表
		List<VO> recordVos = null;
		List<BusRecord> records = busRecordDao.findByBusOrderAndBusUserId(order, busUserId);
		for (BusRecord busRecord : records) {
			BusRecordRes recordRes = new BusRecordRes();
			recordRes.apply(busRecord);
			recordRes.setActivity(new ActivityRes().apply(process.get(busRecord.getActivityNode())));
			if(recordVos==null) {
				recordVos = new ArrayList<VO>();
			}
			recordVos.add(recordRes);
		}
		busOrderRes.setRecords(recordVos);
		
		//查询自提信息
		if(order.isSelfpick()) {
			BusPickcode pickcode = busPickcodeDao.findByBusOrder(order);
			busOrderRes.setPickcode(new BusPickcodeRes().apply(pickcode));
		}
		return busOrderRes;
	}
	
}
