package com.swarm.admin.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.admin.vo.SysBusApplyRes;
import com.swarm.base.dao.SysBusApplyDao;
import com.swarm.base.entity.SysBusApply;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;

@Service
@Transactional(readOnly = true)
public class SysBusApplyService {
	
	@Autowired
	private SysBusApplyDao sysBusApplyDao;
	
	public Page<VO> page(Paging paging){
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("id")));
		Page<SysBusApply> page = sysBusApplyDao.findAll(pageable);
		return page.map(new SysBusApplyRes());
	}
	
	/**
	 * 已确认
	 * @param id
	 */
	@Transactional
	public void confirmed(Integer id) {
		if(id==null)
			throw new ServiceException("ID不能为空！");
		Optional<SysBusApply> optional = sysBusApplyDao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		SysBusApply sysBusApply = optional.get();
		sysBusApply.setUpdateDate(new Date());
		sysBusApply.setStatus(1);
		sysBusApplyDao.save(sysBusApply);
	}
	
}
