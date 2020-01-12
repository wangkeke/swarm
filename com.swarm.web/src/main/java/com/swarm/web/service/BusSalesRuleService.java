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

import com.swarm.base.dao.BusSalesRuleDao;
import com.swarm.base.entity.BusSalesRule;
import com.swarm.base.service.ServiceException;
import com.swarm.base.service.TemplateResourceService;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusSalesRuleRes;
import com.swarm.web.vo.UpdateBusSalesRuleReq;

@Service
@Transactional(readOnly = true)
public class BusSalesRuleService {
	
	private static final String TEMPLATE_DIR = "salesRule";
	private static final String TEMPLATE_NAME = "salesRule";
	
	@Autowired
	private BusSalesRuleDao dao;
	
	@Autowired
	private TemplateResourceService templateResourceService;
	
	public Page<VO> page(Paging paging){
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.asc("id")));
		Page<BusSalesRule> page = dao.findByBusUserId(CurrentUser.getBusUserId(), pageable);
		return page.map(new BusSalesRuleRes());
	}
	
	@Transactional
	public void update(UpdateBusSalesRuleReq req) {
		Optional<BusSalesRule> optional = dao.findById(req.getId());
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		Integer busUserId = CurrentUser.getBusUserId();
		BusSalesRule busSalesRule = optional.get();
		if(busSalesRule.getBusUserId()!=CurrentUser.getBusUserId()) {
			throw new ServiceException("ID不存在！");
		}
		req.update(busSalesRule);
		dao.save(busSalesRule);
		templateResourceService.updateTemplateResource(busUserId, TEMPLATE_DIR, TEMPLATE_NAME, salesRules(busUserId), TEMPLATE_NAME);
	}
	
	@Transactional
	public void enable(Integer id , Boolean enable) {
		if(id==null || enable==null) {
			throw new ServiceException("参数不正确！");
		}
		Optional<BusSalesRule> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusSalesRule busSalesRule = optional.get();
		Integer busUserId = CurrentUser.getBusUserId();
		if(busSalesRule.getBusUserId()!=busUserId) {
			throw new ServiceException("ID不存在！");
		}
		if(busSalesRule.isEnable() != enable.booleanValue()) {
			busSalesRule.setUpdateDate(new Date());
			busSalesRule.setEnable(enable);
			dao.save(busSalesRule);
			templateResourceService.updateTemplateResource(busUserId, TEMPLATE_DIR, TEMPLATE_NAME, salesRules(busUserId), TEMPLATE_NAME);
		}
	}
	
	private List<VO> salesRules(Integer busUserId){
		List<BusSalesRule> list = dao.findByBusUserIdAndEnable(busUserId, true);
		List<VO> rules = new ArrayList<VO>(list.size());
		for (BusSalesRule busSalesRule : list) {
			rules.add(new BusSalesRuleRes().apply(busSalesRule));
		}
		return rules;
	}
	
}
