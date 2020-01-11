package com.swarm.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.base.dao.BusStoreInfoDao;
import com.swarm.base.dao.SysDictDao;
import com.swarm.base.entity.BusStoreInfo;
import com.swarm.base.entity.DictType;
import com.swarm.base.entity.SysDict;
import com.swarm.base.service.TemplateResourceService;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusStoreInfoRes;
import com.swarm.web.vo.SysDictRes;
import com.swarm.web.vo.UpdateBusStoreInfoReq;

@Service
@Transactional(readOnly = true)
public class BusStoreInfoService {
	
	public static final String TEMPLATE_DIR = "storeInfo";
	public static final String TEMPLATE_NAME = "storeInfo";
	
	@Autowired
	private BusStoreInfoDao dao;
	
	@Autowired
	private SysDictDao sysDictDao;
	
	@Autowired
	private TemplateResourceService templateResourceService;
	
	
	public List<VO> getType() {
		List<SysDict> list = sysDictDao.findByType(DictType.SHOP_TYPE);
		List<VO> ress = new ArrayList<VO>();
		for (SysDict sysDict : list) {
			ress.add(new SysDictRes().apply(sysDict));
		}
		return ress;
	}
	
	public VO get() {
		Integer busUserId = CurrentUser.getBusUserId();
		BusStoreInfo busStoreInfo = dao.findFirstByBusUserId(busUserId);
		if(busStoreInfo==null)
			return null;
		return new BusStoreInfoRes().apply(busStoreInfo);
	}
	
	
	@Transactional
	public void update(UpdateBusStoreInfoReq req) {
		Integer busUserId = CurrentUser.getBusUserId();
		BusStoreInfo busStoreInfo = dao.findFirstByBusUserId(busUserId);
		if(busStoreInfo==null) {
			busStoreInfo = new BusStoreInfo();
			busStoreInfo.setCreateDate(new Date());
		}
		req.update(busStoreInfo);
		dao.save(busStoreInfo);
		templateResourceService.updateTemplateResource(busUserId, TEMPLATE_DIR, busStoreInfo.getId()+"", busStoreInfo, TEMPLATE_NAME);
	}
	
}
