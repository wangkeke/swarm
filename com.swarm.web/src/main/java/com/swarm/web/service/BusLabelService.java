package com.swarm.web.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.base.dao.BusLabelDao;
import com.swarm.base.dao.BusProductDao;
import com.swarm.base.entity.BusLabel;
import com.swarm.base.service.ServiceException;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusLabelReq;
import com.swarm.web.vo.BusLabelRes;
import com.swarm.web.vo.UpdateBusLabelReq;

@Transactional(readOnly = true)
@Service
public class BusLabelService {
	
	@Autowired
	private BusLabelDao dao;
	
	@Autowired
	private BusProductDao busProductDao;
	
	
	public List<BusLabelRes> list() {
		List<BusLabel> list = dao.findByBusUserId(CurrentUser.getBusUserId());
		List<BusLabelRes> ress = new ArrayList<BusLabelRes>();
		for (BusLabel label : list) {
			BusLabelRes res = new BusLabelRes();
			res.apply(label);
			ress.add(res);
		}
		return ress;
	}
	
	
	public boolean validLabel(String label) {
		if(StringUtils.isBlank(label)) {
			return false;
		}
		int count = dao.countByLabelAndBusUserId(label, CurrentUser.getBusUserId());
		return count>0?false:true;
	}
	
	@Transactional
	public Integer save(BusLabelReq req) {
		if(!validLabel(req.getLabel())) {
			throw new ServiceException("标签名称已存在！");
		}
		BusLabel busLabel = req.create();
		dao.save(busLabel);
		return busLabel.getId();
	}
	
	@Transactional
	public void update(UpdateBusLabelReq req) {
		BusLabel busLabel = dao.findByIdAndBusUserId(req.getId(), CurrentUser.getBusUserId());
		if(busLabel==null) {
			throw new ServiceException("ID不存在！");
		}
		if(!busLabel.getLabel().equals(req.getLabel())) {
			if(!validLabel(req.getLabel())) {
				throw new ServiceException("标签名称已存在！");
			}
		}
		req.update(busLabel);
		dao.save(busLabel);
	}
	
	@Transactional
	public void delete(Integer id) {
		if(id == null) {
			throw new ServiceException("ID不能为空！");
		}
		BusLabel busLabel = dao.findByIdAndBusUserId(id, CurrentUser.getBusUserId());
		if(busLabel==null) {
			throw new ServiceException("ID不存在！");
		}
		int count = busProductDao.countByLabelAndBusUserIdAndFlagNot(busLabel, CurrentUser.getBusUserId() , -1);
		if(count>0) {
			throw new ServiceException("删除失败，该标签已绑定到产品！");
		}
		dao.delete(busLabel);
	}
	
}
