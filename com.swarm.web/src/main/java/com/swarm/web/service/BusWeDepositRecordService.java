package com.swarm.web.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.base.dao.BusWeDepositRecordDao;
import com.swarm.base.entity.BusWeDepositRecord;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusWeDepositRecordRes;

@Transactional(readOnly = true)
@Service
public class BusWeDepositRecordService {
	
	@Autowired
	private BusWeDepositRecordDao dao;
	
	
	public Page<VO> page(String nicename , Paging paging){
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("updateDate")));
		Page<BusWeDepositRecord> page = null;
		if(StringUtils.isNotBlank(nicename)) {
			page = dao.queryByNicenameLike("%"+nicename+"%", CurrentUser.getBusUserId() , pageable);
		}else {
			page = dao.findByBusUserId(CurrentUser.getBusUserId(), pageable);
		}
		return page.map(new BusWeDepositRecordRes());
	}
	
}
