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

import com.swarm.base.dao.BusCashbackDao;
import com.swarm.base.dao.BusCashbackRecordDao;
import com.swarm.base.entity.BusCashback;
import com.swarm.base.entity.BusCashbackRecord;
import com.swarm.base.service.Activity;
import com.swarm.base.service.ActivityNode;
import com.swarm.base.service.CashbackProcess;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.ActivityRes;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusCashbackRecordRes;
import com.swarm.web.vo.BusCashbackRes;

@Transactional(readOnly = true)
@Service
public class BusCashbackService {
	
	@Autowired
	private BusCashbackDao dao;
	
	@Autowired
	private BusCashbackRecordDao busCashbackRecordDao;
	
	@Autowired
	private CashbackProcess process;
	
	
	
	public Page<VO> page(Paging paging) {
		Integer busUserId = CurrentUser.getBusUserId();
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("id")));
		Page<BusCashback> page = dao.findByBusUserId(busUserId, pageable);
		Page<VO> mapPage = page.map(new BusCashbackRes());
		for (VO vo : mapPage.getContent()) {
			BusCashbackRes res = (BusCashbackRes)vo;
			Activity activity = process.get(res.getActivityNode());
			List<VO> nexts = null;
			for (Activity a : activity.getNexts()) {
				if(a.getOperator()==null || a.getOperator()==Activity.BUS_USER) {
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
	public void process(Integer id , ActivityNode node) {
		if(id==null || node==null) {
			throw new ServiceException("参数不正确！");
		}
		Integer busUserId = CurrentUser.getBusUserId();
		Optional<BusCashback> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusCashback busCashback = optional.get();
		if(busCashback.getBusUserId()!=busUserId) {
			throw new ServiceException("ID不存在！");
		}
		Activity activity = process.get(busCashback.getActivityNode());
		List<Activity> nexts = activity.getNexts();
		Activity activity2 = null;
		for (Activity a : nexts) {
			if(a.getNode()==node && (a.getOperator()==null || a.getOperator()==Activity.BUS_USER)) {
				activity2 = a;
				break;
			}
		}
		if(activity2==null) {
			throw new ServiceException("不存在的流程操作！");
		}
		busCashback.setUpdateDate(new Date());
		busCashback.setActivityNode(node);
		dao.save(busCashback);
	}
	
	public Page<VO> details(Integer id , Paging paging){
		if(id==null) {
			throw new ServiceException("ID不能为空！");
		}
		Integer busUserId = CurrentUser.getBusUserId();
		Optional<BusCashback> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusCashback busCashback = optional.get();
		if(busCashback.getBusUserId()!=busUserId) {
			throw new ServiceException("ID不存在！");
		}
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("id")));
		Page<BusCashbackRecord> page = busCashbackRecordDao.findByBusCashbackAndBusUserId(busCashback, busUserId, pageable);
		return page.map(new BusCashbackRecordRes());
	}
	
}
