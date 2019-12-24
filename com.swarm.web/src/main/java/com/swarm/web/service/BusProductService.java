package com.swarm.web.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.base.dao.BusCategoryDao;
import com.swarm.base.dao.BusImageDao;
import com.swarm.base.dao.BusLabelDao;
import com.swarm.base.dao.BusProductDao;
import com.swarm.base.entity.BusCategory;
import com.swarm.base.entity.BusImage;
import com.swarm.base.entity.BusLabel;
import com.swarm.base.entity.BusProduct;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusProductReq;
import com.swarm.web.vo.BusProductRes;
import com.swarm.web.vo.UpdateBusProductReq;

@Service
@Transactional(readOnly = true)
public class BusProductService {
	
	@Autowired
	private BusProductDao dao;
	
	@Autowired
	private BusCategoryDao busCategoryDao;
	
	@Autowired
	private BusLabelDao busLabelDao;
	
	
	public Page<VO> page(Integer categroyId , String title , Paging paging){
		List<BusCategory> list = null;
		if(categroyId!=null) {
			BusCategory busCategory = busCategoryDao.findFirstByIdAndBusUserId(categroyId, CurrentUser.getBusUserId());
			if(busCategory != null) {
				list = new ArrayList<BusCategory>();
				list.add(busCategory);
				List<BusCategory> busCategories = list;
				while(true) {
					busCategories = busCategoryDao.findByParentInAndBusUserId(busCategories, CurrentUser.getBusUserId());
					if(busCategories==null || busCategories.isEmpty()) {
						break;
					}else {
						list.addAll(busCategories);
					}
				}
			}
		}
		Page<BusProduct> page = null;
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("id")));
		if(list!=null && StringUtils.isNotBlank(title)) {
			page = dao.findByCategoryInAndTitleLikeAndBusUserIdAndFlagNot(list, title, CurrentUser.getBusUserId(), -1, pageable);
		}else if(list!=null && StringUtils.isBlank(title)) {
			page = dao.findByCategoryInAndBusUserIdAndFlagNot(list, CurrentUser.getBusUserId(), -1, pageable);
		}else if (list==null && StringUtils.isNotBlank(title)) {
			page = dao.findByTitleLikeAndBusUserIdAndFlagNot(title, CurrentUser.getBusUserId(), -1, pageable);
		}else {
			page = dao.findByBusUserIdAndFlagNot(CurrentUser.getBusUserId(), -1, pageable);
		}
	 	return page.map(new BusProductRes());
	}
	
	@Transactional
	public Integer save(BusProductReq req) {
		BusProduct busProduct = req.create();
		if(req.getBusLabelId()!=null) {
			BusLabel busLabel = busLabelDao.findByIdAndBusUserId(req.getBusLabelId(), CurrentUser.getBusUserId());
			if(busLabel==null)
				throw new ServiceException("标签不存在！");
			busProduct.setLabel(busLabel);
		}
		BusCategory busCategory = busCategoryDao.findFirstByIdAndBusUserId(req.getCategoryId(), CurrentUser.getBusUserId());
		if(busCategory==null)
			throw new ServiceException("商品分类不存在！");
		busProduct.setCategory(busCategory);
		dao.save(busProduct);
		return busProduct.getId();
	}
	
	public VO get(Integer id) {
		if(id == null)
			throw new ServiceException("ID不能为空！");
		Optional<BusProduct> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusProduct busProduct = optional.get();
		if(busProduct.getFlag()<0 || busProduct.getBusUserId()!=CurrentUser.getBusUserId()) {
			throw new ServiceException("ID不存在！");
		}
		return new BusProductRes().apply(busProduct);
	}
	
	@Transactional
	public void update(UpdateBusProductReq req) {
		Optional<BusProduct> optional = dao.findById(req.getId());
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusProduct product = optional.get();
		if(product.getFlag()<0 || product.getBusUserId()!=CurrentUser.getBusUserId()) {
			throw new ServiceException("ID不存在！");
		}
//		req.update(product);
		if(req.getBusLabelId()!=null && product.getLabel()!=null && req.getBusLabelId()!=product.getLabel().getId()) {
			BusLabel busLabel = busLabelDao.findByIdAndBusUserId(req.getBusLabelId(), CurrentUser.getBusUserId());
			if(busLabel==null)
				throw new ServiceException("标签不存在！");
			product.setLabel(busLabel);
		}
		if(product.getCategory().getId()!=req.getCategoryId()) {			
			BusCategory busCategory = busCategoryDao.findFirstByIdAndBusUserId(req.getCategoryId(), CurrentUser.getBusUserId());
			if(busCategory==null)
				throw new ServiceException("商品分类不存在！");
			product.setCategory(busCategory);
		}
		req.update(product);
		dao.save(product);
	}
	
	@Transactional
	public void show(Integer id , Boolean show) {
		if(id == null || show==null)
			throw new ServiceException("参数不正确！");
		Optional<BusProduct> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusProduct busProduct = optional.get();
		if(busProduct.getFlag()<0 || busProduct.getBusUserId()!=CurrentUser.getBusUserId()) {
			throw new ServiceException("ID不存在！");
		}
		if(busProduct.isShow()!=show.booleanValue()) {
			busProduct.setShow(show);
			busProduct.setUpdateDate(new Date());
			dao.save(busProduct);
		}
	}
	
	@Transactional
	public void delete(Integer id) {
		if(id == null)
			throw new ServiceException("ID不能为空！");
		Optional<BusProduct> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusProduct busProduct = optional.get();
		if(busProduct.getFlag()<0 || busProduct.getBusUserId()!=CurrentUser.getBusUserId()) {
			throw new ServiceException("ID不存在！");
		}
		busProduct.setFlag(-1);
		busProduct.setUpdateDate(new Date());
		dao.save(busProduct);
	}
	
}
