package com.swarm.web.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.base.dao.BusMnprogramDao;
import com.swarm.base.entity.BusMnprogram;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusMnprogramReq;
import com.swarm.web.vo.BusMnprogramRes;

@Transactional(readOnly = true)
@Service
public class BusMnprogramService {
	
	@Autowired
	private BusMnprogramDao busMnprogramDao;
	
	public VO get() {
	 	Integer busUserId = CurrentUser.getBusUser().getId();
	 	BusMnprogram busMnprogram = busMnprogramDao.findFirstByBusUserId(busUserId);
	 	if(busMnprogram==null)
	 		return null;
	 	return new BusMnprogramRes().apply(busMnprogram);
	}
	
	@Transactional
	public Integer save(BusMnprogramReq req) {
		Integer busUserId = CurrentUser.getBusUser().getId();
		BusMnprogram busMnprogram = busMnprogramDao.findFirstByBusUserId(busUserId);
		if(busMnprogram==null) {			
			busMnprogram = req.create();
			busMnprogramDao.save(busMnprogram);
			return busMnprogram.getId();
		}else {
			busMnprogram.setUpdateDate(new Date());
			busMnprogram.setMpname(req.getMpname());
			busMnprogram.setAppID(req.getAppID());
			busMnprogram.setAppSecret(req.getAppSecret());
			busMnprogram.setPaySecretKey(req.getPaySecretKey());
			busMnprogram.setApiclientcertPath(req.getApiclientcertPath());
			busMnprogram.setApiclientkeyPath(req.getApiclientkeyPath());
			busMnprogramDao.save(busMnprogram);
			return busMnprogram.getId();
		}
	}
	
}
