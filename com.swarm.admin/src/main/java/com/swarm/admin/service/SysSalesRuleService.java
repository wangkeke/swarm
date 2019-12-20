package com.swarm.admin.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.admin.vo.SysSalesRuleReq;
import com.swarm.admin.vo.SysSalesRuleRes;
import com.swarm.admin.vo.UpdateSysSalesRuleReq;
import com.swarm.base.dao.SysSalesRuleDao;
import com.swarm.base.entity.SysSalesRule;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;

@Service
@Transactional(readOnly = true)
public class SysSalesRuleService {
	
	@Autowired
	private SysSalesRuleDao salesRuleDao;
	
	public Page<VO> page(String name , Paging paging){
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("createDate")));
		Page<SysSalesRule> page = null;
		if(StringUtils.isBlank(name)) {
			page = salesRuleDao.findAll(pageable);
		}else {
			page = salesRuleDao.findByNameLike(name, pageable);
		}
		return page.map(new SysSalesRuleRes());
	}
	
	public boolean validKey(String key) {
		if(StringUtils.isBlank(key)) {
			return false;
		}
		return salesRuleDao.countByKey(key)>0?false:true;
	}
	
	@Transactional
	public Integer save(SysSalesRuleReq req) {
		SysSalesRule rule = req.create();
		if(!validKey(rule.getKey())) {
			throw new ServiceException("Key已存在！");
		}
		salesRuleDao.save(rule);
		return rule.getId();
	}
	
	public VO get(Integer id) {
		if(id == null)
			throw new ServiceException("ID不能为空！");
		Optional<SysSalesRule> optional = salesRuleDao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		SysSalesRule rule = optional.get();
		return new SysSalesRuleRes().apply(rule);
	}
	
	@Transactional
	public void update(UpdateSysSalesRuleReq req) {
		Optional<SysSalesRule> optional = salesRuleDao.findById(req.getId());
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		SysSalesRule rule = optional.get();
		req.update(rule);
		salesRuleDao.save(rule);
	}
	
	@Transactional
	public void enable(Integer id , Boolean enable) {
		if(id==null)
			throw new ServiceException("ID不能为空！");
		if(enable==null) {
			throw new ServiceException("请选择是否启用！");
		}
		Optional<SysSalesRule> optional = salesRuleDao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		SysSalesRule rule = optional.get();
		if(rule.isEnable() != enable.booleanValue()) {
			rule.setEnable(enable);
			salesRuleDao.save(rule);
		}
	}
	
}
