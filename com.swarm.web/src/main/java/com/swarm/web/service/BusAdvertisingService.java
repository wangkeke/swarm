package com.swarm.web.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.base.dao.BusAdvertisingDao;
import com.swarm.base.entity.BusAdvertising;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusAdvertisingReq;
import com.swarm.web.vo.BusAdvertisingRes;
import com.swarm.web.vo.UpdateBusAdvertisingReq;

@CacheConfig(keyGenerator = "redisKeyGenerator")
@Service
@Transactional(readOnly = true)
public class BusAdvertisingService {
	
	@Autowired
	private BusAdvertisingDao dao;
	
	public Page<VO> page(Boolean enable , Paging paging){
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("updateDate")));
		Page<BusAdvertising> page = null;
		if(enable==null)
			page = dao.findAll(pageable);
		else
			page = dao.findByEnableAndBusUserId(enable, CurrentUser.getBusUser().getId(), pageable);
		return page.map(new BusAdvertisingRes());
	}
	
	@CacheEvict("advertising:#p0")
	@Transactional
	public Integer save(Integer busUserId , BusAdvertisingReq req) {
		BusAdvertising busAdvertising = req.create();
		dao.save(busAdvertising);
		return busAdvertising.getId();
	}
	
	public VO get(Integer id) {
		if(id == null) {
			throw new ServiceException("ID不能为空！");
		}
		Optional<BusAdvertising> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusAdvertising advertising = optional.get();
		if(advertising.getBusUserId()!=CurrentUser.getBusUser().getId()) {
			throw new ServiceException("ID不存在！");
		}
		return new BusAdvertisingRes().apply(advertising);
	}
	
	@CacheEvict("advertising:#p0")
	@Transactional
	public void update(Integer busUserId,UpdateBusAdvertisingReq req) {
		if(req.getId() == null) {
			throw new ServiceException("ID不能为空！");
		}
		Optional<BusAdvertising> optional = dao.findById(req.getId());
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusAdvertising advertising = optional.get();
		if(advertising.getBusUserId()!=CurrentUser.getBusUser().getId()) {
			throw new ServiceException("ID不存在！");
		}
		req.update(advertising);
		dao.save(advertising);
	}
	
	@CacheEvict("advertising:#p0")
	@Transactional
	public void enable(Integer busUserId,Integer id , Boolean enable) {
		if(id == null) {
			throw new ServiceException("ID不能为空！");
		}
		if(enable==null) {
			throw new ServiceException("是否启用不能为空！");
		}
		Optional<BusAdvertising> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusAdvertising advertising = optional.get();
		if(advertising.getBusUserId()!=CurrentUser.getBusUser().getId()) {
			throw new ServiceException("ID不存在！");
		}
		if(enable.booleanValue()!=advertising.isEnable()) {
			advertising.setEnable(enable);
			advertising.setUpdateDate(new Date());
			dao.save(advertising);
		}
	}
	
}
