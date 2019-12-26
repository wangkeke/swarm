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

import com.swarm.base.dao.BusWeWithdrawalDao;
import com.swarm.base.entity.BusWeWithdrawal;
import com.swarm.base.service.Activity;
import com.swarm.base.service.ActivityNode;
import com.swarm.base.service.ServiceException;
import com.swarm.base.service.WithdrawalProcess;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.ActivityRes;
import com.swarm.web.vo.BusWeWithdrawalRes;

@Transactional(readOnly = true)
@Service
public class BusWeWithdrawalService {
	
	@Autowired
	private BusWeWithdrawalDao dao;
	
	@Autowired
	private WithdrawalProcess process;
	
	
	public Page<VO> page(Paging paging) {
		Integer busUserId = CurrentUser.getBusUserId();
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("id")));
		Page<BusWeWithdrawal> page = dao.findByBusUserId(busUserId, pageable);
		Page<VO> mapPage = page.map(new BusWeWithdrawalRes());
		for (VO vo : mapPage.getContent()) {
			BusWeWithdrawalRes res = (BusWeWithdrawalRes)vo;
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
	public void process(Integer id , String comment , ActivityNode node) {
		if(id==null || node==null) {
			throw new ServiceException("参数不正确！");
		}
		Integer busUserId = CurrentUser.getBusUserId();
		Optional<BusWeWithdrawal> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusWeWithdrawal busWeWithdrawal = optional.get();
		if(busWeWithdrawal.getBusUserId()!=busUserId) {
			throw new ServiceException("ID不存在！");
		}
		Activity activity = process.get(busWeWithdrawal.getActivityNode());
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
		busWeWithdrawal.setComment(comment);
		busWeWithdrawal.setUpdateDate(new Date());
		busWeWithdrawal.setActivityNode(node);
		dao.save(busWeWithdrawal);
	}
	
}
