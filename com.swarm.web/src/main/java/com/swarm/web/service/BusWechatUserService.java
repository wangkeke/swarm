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

import com.swarm.base.dao.BusWechatUserDao;
import com.swarm.base.entity.BusWechatUser;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusWechatUserRes;

@Transactional(readOnly = true)
@Service
public class BusWechatUserService {
	
	@Autowired
	private BusWechatUserDao dao;
	
	public Page<VO> page(String nicename, Paging paging){
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("id")));
		Page<BusWechatUser> page = null;
		Integer busUserId = CurrentUser.getBusUserId();
		if(StringUtils.isBlank(nicename)) {
			page = dao.findByBusUserId(busUserId, pageable);
		}else {
			page = dao.findByNicknameLikeAndBusUserId("%"+nicename+"%", busUserId, pageable);
		}
		return page.map(new BusWechatUserRes());
	}
	
}
