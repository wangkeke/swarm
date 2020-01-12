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

import com.swarm.base.dao.BusMenuDao;
import com.swarm.base.entity.BusMenu;
import com.swarm.base.service.ServiceException;
import com.swarm.base.service.TemplateResourceService;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusMenuRes;
import com.swarm.web.vo.UpdateBusMenuReq;

@Service
@Transactional(readOnly = true)
public class BusMenuService {
	
	public static final String TEMPLATE_DIR = "menu";
	public static final String TEMPLATE_NAME = "menu";
	
	@Autowired
	private BusMenuDao dao;
	
	@Autowired
	private TemplateResourceService templateResourceService;

	
	public Page<VO> page(Paging paging){
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.asc("sort")));
		Page<BusMenu> page = dao.findByBusUserId(CurrentUser.getBusUserId(), pageable);
		return page.map(new BusMenuRes());
	}
	
	@Transactional
	public void update(UpdateBusMenuReq req) {
		Integer busUserId = CurrentUser.getBusUserId();
		Optional<BusMenu> optional = dao.findById(req.getId());
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusMenu busMenu = optional.get();
		if(busMenu.getBusUserId()!=busUserId) {
			throw new ServiceException("ID不存在！");
		}
		req.update(busMenu);
		dao.save(busMenu);
		templateResourceService.updateTemplateResource(busUserId, TEMPLATE_DIR, TEMPLATE_NAME, menu(busUserId),TEMPLATE_NAME);
	} 
	
	@Transactional
	public void show(Integer id , Boolean show) {
		if(id==null || show==null) {
			throw new ServiceException("参数不正确！");
		}
		Optional<BusMenu> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		Integer busUserId = CurrentUser.getBusUserId();
		BusMenu busMenu = optional.get();
		if(busMenu.getBusUserId()!=busUserId) {
			throw new ServiceException("ID不存在！");
		}
		if(busMenu.isShow()!=show.booleanValue()) {
			busMenu.setUpdateDate(new Date());
			busMenu.setShow(show);
			dao.save(busMenu);
			templateResourceService.updateTemplateResource(busUserId, TEMPLATE_DIR, TEMPLATE_NAME, menu(busUserId),TEMPLATE_NAME);
		}
	}
	
	private List<VO> menu(Integer busUserId){
		List<BusMenu> list = dao.findByShowAndBusUserIdOrderBySortDesc(true, busUserId);
		List<VO> menus = new ArrayList<VO>();
		for (BusMenu busMenu : list) {
			menus.add(new BusMenuRes().apply(busMenu));
		}
		return menus;
	}
	
}
