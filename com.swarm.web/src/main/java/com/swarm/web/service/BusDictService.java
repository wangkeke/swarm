package com.swarm.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.base.dao.BusDictDao;
import com.swarm.base.entity.BusDict;
import com.swarm.base.entity.DictType;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusDictRes;

@Transactional(readOnly = true)
@Service
public class BusDictService {
	
	@Autowired
	private BusDictDao dao;
	
	public List<VO> get(DictType type){
		List<VO> list = new ArrayList<VO>();
		if(type==null)
			return list;
		Integer busUserId = CurrentUser.getBusUserId();
		List<BusDict> busDicts = dao.findByTypeAndBusUserId(type, busUserId);
		for (BusDict busDict : busDicts) {
			list.add(new BusDictRes().apply(busDict));
		}
		return list;
	}
	
	@Transactional
	public void update(DictType type , Map<String, String[]> params) {
		if(type==null) {
			throw new ServiceException("字典类型不能为空！");
		}
		if(params==null || params.isEmpty()) {
			return;
		}
		Integer busUserId = CurrentUser.getBusUserId();
		List<BusDict> busDicts = dao.findByTypeAndBusUserId(type, busUserId);
		for (BusDict busDict : busDicts) {
			if(params.containsKey(busDict.getKey())) {
				String[] values = params.remove(busDict.getKey());
				if(values==null || values.length==0) {
					continue;
				}
				busDict.setValue(values[0]);
				busDict.setUpdateDate(new Date());
				if(values.length>1) {
					busDict.setValue2(values[1]);
				}
				dao.save(busDict);
			}
		}
		if(params.isEmpty()) {
			return;
		}
		for (String key : params.keySet()) {
			String[] values = params.get(key);
			if(values==null || values.length==0) {
				continue;
			}
			BusDict busDict = new BusDict();
			busDict.setBusUserId(busUserId);
			busDict.setCreateDate(new Date());
			busDict.setUpdateDate(new Date());
			busDict.setKey(key);
			busDict.setType(type);
			busDict.setValue(values[0]);
			if(values.length>1) {
				busDict.setValue2(values[1]);
			}
			dao.save(busDict);
		}
	}
	
}
