package com.swarm.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.base.dao.BusImageDao;
import com.swarm.base.entity.BusImage;
import com.swarm.base.entity.BusImageType;
import com.swarm.base.service.ServiceException;
import com.swarm.base.service.TemplateResourceService;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusImageReq;
import com.swarm.web.vo.BusImageRes;
import com.swarm.web.vo.UpdateBusImageReq;

@Service
@Transactional(readOnly = true)
public class BusImageService {
	
	public static final String TEMPLATE_DIR = "carousel";
	public static final String TEMPLATE_NAME = "carousel";
	
	@Autowired
	private BusImageDao dao;
	
	@Autowired
	private TemplateResourceService templateResourceService;
	
	
	public Page<VO> page(Paging paging){
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("id")));
		Page<BusImage> page = dao.findByBusUserId(CurrentUser.getBusUserId(), pageable);
		return page.map(new BusImageRes());
	}
	
	@Transactional
	public Integer save(BusImageReq req) {
		BusImage busImage = req.create();
		dao.save(busImage);
		if(busImage.getBusImageType()==BusImageType.SHOP_HOME_CAROUSEL) {
			templateResourceService.updateTemplateResource(busImage.getBusUserId(), TEMPLATE_DIR, TEMPLATE_NAME, carousel(busImage.getBusUserId()), TEMPLATE_NAME);
		}
		return busImage.getId();
	}
	
	@Transactional
	public void update(UpdateBusImageReq req) {
		BusImage busImage = dao.findByIdAndBusUserId(req.getId(), CurrentUser.getBusUserId());
		if(busImage==null) {
			throw new ServiceException("ID不存在！");
		}
		req.update(busImage);
		dao.save(busImage);
		if(busImage.getBusImageType()==BusImageType.SHOP_HOME_CAROUSEL) {
			templateResourceService.updateTemplateResource(busImage.getBusUserId(), TEMPLATE_DIR, TEMPLATE_NAME, carousel(busImage.getBusUserId()), TEMPLATE_NAME);
		}
	}
	
	@Transactional
	public void delete(Integer id) {
		if(id==null) {
			throw new ServiceException("ID不能为空！");
		}
		BusImage busImage = dao.findByIdAndBusUserId(id, CurrentUser.getBusUserId());
		if(busImage==null) {
			throw new ServiceException("ID不存在！");
		}
		dao.delete(busImage);
		if(busImage.getBusImageType()==BusImageType.SHOP_HOME_CAROUSEL) {
			templateResourceService.updateTemplateResource(busImage.getBusUserId(), TEMPLATE_DIR, TEMPLATE_NAME, carousel(busImage.getBusUserId()), TEMPLATE_NAME);
		}
	}
	
	private List<VO> carousel(Integer busUserId){
		List<BusImage> list = dao.findByBusUserIdAndBusImageTypeOrderBySortDesc(busUserId, BusImageType.SHOP_HOME_CAROUSEL);
		List<VO> vos = new ArrayList<VO>(list.size());
		for (BusImage busImage : list) {
			vos.add(new BusImageRes().apply(busImage));
		}
		return vos;
	}
	
}
