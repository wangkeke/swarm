package com.swarm.web.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.base.dao.BusDeliveryAddressDao;
import com.swarm.base.entity.BusDeliveryAddress;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusDeliveryAddressReq;
import com.swarm.web.vo.BusDeliveryAddressRes;
import com.swarm.web.vo.UpdateBusDeliveryAddressReq;

@Service
@Transactional(readOnly = true)
public class BusDeliveryAddressService {
	
	@Autowired
	private BusDeliveryAddressDao dao;
	
	
	public Page<VO> page(Paging paging){
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("id")));
		Page<BusDeliveryAddress> page = dao.findByBusUserIdAndFlagNot(CurrentUser.getBusUserId(), -1, pageable);
		return page.map(new BusDeliveryAddressRes());
	}
	
	@Transactional
	public Integer save(BusDeliveryAddressReq req) {
		BusDeliveryAddress busDeliveryAddress = req.create();
		dao.save(busDeliveryAddress);
		return busDeliveryAddress.getId();
	}
	
	@Transactional
	public void update(UpdateBusDeliveryAddressReq req) {
		Optional<BusDeliveryAddress> optional = dao.findById(req.getId());
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusDeliveryAddress busDeliveryAddress = optional.get();
		if(busDeliveryAddress.getFlag()<0 || busDeliveryAddress.getBusUserId()!=CurrentUser.getBusUserId()) {
			throw new ServiceException("ID不存在！");
		}
		req.update(busDeliveryAddress);
		dao.save(busDeliveryAddress);
	}
	
	@Transactional
	public void enable(Integer id , Boolean enable) {
		if(id==null || enable==null) {
			throw new ServiceException("参数不正确！");
		}
		Optional<BusDeliveryAddress> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusDeliveryAddress busDeliveryAddress = optional.get();
		if(busDeliveryAddress.getFlag()<0 || busDeliveryAddress.getBusUserId()!=CurrentUser.getBusUserId()) {
			throw new ServiceException("ID不存在！");
		}
		if(busDeliveryAddress.isEnable()!=enable.booleanValue()) {
			busDeliveryAddress.setUpdateDate(new Date());
			busDeliveryAddress.setEnable(enable);
			dao.save(busDeliveryAddress);
		}
	}
	
	@Transactional
	public void delete(Integer id) {
		if(id==null) {
			throw new ServiceException("ID不能为空！");
		}
		dao.removeById(id, CurrentUser.getBusUserId(),new Date());
	}
	
}
