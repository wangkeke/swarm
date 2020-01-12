package com.swarm.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.base.dao.BusCategoryDao;
import com.swarm.base.dao.BusProductDao;
import com.swarm.base.entity.BusCategory;
import com.swarm.base.service.ServiceException;
import com.swarm.base.service.TemplateResourceService;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusCategoryReq;
import com.swarm.web.vo.BusCategoryRes;
import com.swarm.web.vo.UpdateBusCategoryReq;

@Transactional(readOnly = true)
@Service
public class BusCategoryService {
	
	public static final String TEMPLATE_DIR = "category";
	public static final String TEMPLATE_NAME = "category";
	
	@Autowired
	private BusCategoryDao dao;
	
	@Autowired
	private BusProductDao busProductDao;
	
	@Autowired
	private TemplateResourceService templateResourceService;
	
	public List<BusCategoryRes> list(){
		List<BusCategory> list = dao.findByBusUserId(CurrentUser.getBusUser().getId());
		List<BusCategoryRes> ress = new ArrayList<BusCategoryRes>(list.size());
		Map<Integer, BusCategory> map = new HashMap<Integer, BusCategory>();
		Map<Integer, BusCategoryRes> resMap = new HashMap<Integer, BusCategoryRes>();
		for (BusCategory category : list) {
			BusCategoryRes res = (BusCategoryRes)new BusCategoryRes().apply(category);
			ress.add(res);
			map.put(res.getId(), category);
			resMap.put(res.getId(), res);
		}
		for (BusCategoryRes res : ress) {
			if(map.get(res.getId()).getParent()!=null) {
				res.setParent(resMap.get(map.get(res.getId()).getParent().getId()));
			}
		}
		return ress;
	}
	
	
	
	@Transactional
	public Integer save(BusCategoryReq req) {
		Integer busUserId = CurrentUser.getBusUserId();
		BusCategory busCategory = req.create();
		if(req.getParentId()!=null) {
			BusCategory parent = dao.findFirstByIdAndBusUserId(req.getParentId(), busUserId);
			if(parent==null) {
				throw new ServiceException("父级分类不存在！");
			}
			busCategory.setParent(parent);
		}
		busCategory.setBusUserId(busUserId);
		dao.save(busCategory);
		templateResourceService.updateTemplateResource(busUserId, TEMPLATE_DIR, TEMPLATE_NAME, category(busUserId), TEMPLATE_NAME);
		return busCategory.getId();
	}
	
	@Transactional
	public void update(UpdateBusCategoryReq req) {
		Integer busUserId = CurrentUser.getBusUserId();
		BusCategory busCategory = dao.findFirstByIdAndBusUserId(req.getId(), busUserId);
		if(busCategory==null) {
			throw new ServiceException("分类不存在！");
		}
		req.update(busCategory);
		dao.save(busCategory);
		templateResourceService.updateTemplateResource(busUserId, TEMPLATE_DIR, TEMPLATE_NAME, category(busUserId), TEMPLATE_NAME);
	}
	
	@Transactional
	public void show(Integer id , Boolean show) {
		if(id==null || show==null) {
			throw new ServiceException("参数不正确！");
		}
		Integer busUserId = CurrentUser.getBusUserId();
		BusCategory busCategory = dao.findFirstByIdAndBusUserId(id, busUserId);
		if(busCategory==null) {
			throw new ServiceException("该分类不存在！");
		}
		if(busCategory.isShow() != show.booleanValue()) {
			busCategory.setShow(show);
			busCategory.setUpdateDate(new Date());
			dao.save(busCategory);
			templateResourceService.updateTemplateResource(busUserId, TEMPLATE_DIR, TEMPLATE_NAME, category(busUserId), TEMPLATE_NAME);
		}
	} 
	
	@Transactional
	public void delete(Integer id) {
		Integer busUserId = CurrentUser.getBusUserId();
		BusCategory busCategory = dao.findFirstByIdAndBusUserId(id, busUserId);
		if(busCategory!=null) {
			int count = dao.countByParentAndBusUserId(busCategory, busUserId);
			if(count>0) {
				throw new ServiceException("请先删除子级分类！");
			}
			count = busProductDao.countByCategoryAndBusUserIdAndFlagNot(busCategory , busUserId , -1);
			if(count>0) {
				throw new ServiceException("请先删除该分类下的产品！");
			}
			dao.delete(busCategory);
			templateResourceService.updateTemplateResource(busUserId, TEMPLATE_DIR, TEMPLATE_NAME, category(busUserId), TEMPLATE_NAME);
		}
	}
	
	public List<BusCategoryRes> category(Integer busUserId){
		List<BusCategory> list = dao.findByBusUserIdAndShow(busUserId, true);
		List<BusCategoryRes> ress = new ArrayList<BusCategoryRes>(list.size());
		Map<Integer, List<BusCategoryRes>> map = new HashMap<Integer, List<BusCategoryRes>>();
		for (BusCategory busCategory : list) {
			BusCategoryRes res = new BusCategoryRes();
			res.apply(busCategory);
			if(busCategory.getParent()==null) {
				ress.add(res);
			}else {
				List<BusCategoryRes> sublist = map.get(res.getParentId());
				if(sublist==null) {
					sublist = new ArrayList<BusCategoryRes>();
					map.put(res.getParentId(), sublist);
				}
				sublist.add(res);
			}
		}
		for (BusCategoryRes busCategoryRes : ress) {
			busCategoryRes.setSublist(map.get(busCategoryRes.getParentId()));
		}
		return ress;
	}
	
}
