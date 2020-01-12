package com.swarm.web.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.base.dao.BusCategoryDao;
import com.swarm.base.dao.BusCouponCategoryDao;
import com.swarm.base.dao.BusCouponDao;
import com.swarm.base.dao.BusWeUserCouponDao;
import com.swarm.base.entity.BusCategory;
import com.swarm.base.entity.BusCoupon;
import com.swarm.base.entity.BusCouponCategory;
import com.swarm.base.service.ServiceException;
import com.swarm.base.service.TemplateResourceService;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusCategoryRes;
import com.swarm.web.vo.BusCouponReq;
import com.swarm.web.vo.BusCouponRes;
import com.swarm.web.vo.UpdateBusCouponReq;

@Service
@Transactional(readOnly = true)
public class BusCouponService {
	
	public static final String TEMPLATE_DIR = "coupon";
	public static final String TEMPLATE_NAME = "coupon";
	
	@Autowired
	private BusCouponDao dao;
	
	@Autowired
	private BusCouponCategoryDao busCouponCategoryDao;
	
	@Autowired
	private BusCategoryDao busCategoryDao;
	
	@Autowired
	private BusWeUserCouponDao busWeUserCouponDao;
	
	@Autowired
	private TemplateResourceService templateResourceService;
	
	
	public Page<VO> page(Paging paging){
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("updateDate")));
		Page<BusCoupon> page = dao.findByBusUserId(CurrentUser.getBusUserId(), pageable);
		return page.map(new BusCouponRes());
	}
	
	@Transactional
	public Integer save(BusCouponReq req) {
		Integer busUserId = CurrentUser.getBusUserId();
		BusCoupon busCoupon = req.create();
		dao.save(busCoupon);
		Integer[] category = req.getCategory();
		if(req.getEnableCate() && category!=null && category.length>0) {
			List<BusCategory> busCategories = busCategoryDao.findByIdInAndBusUserId(Arrays.asList(category) , CurrentUser.getBusUserId());
			if(busCategories.size()>0) {
				List<BusCouponCategory> list = new ArrayList<BusCouponCategory>(busCategories.size());
				for (BusCategory busCategory : busCategories) {
					BusCouponCategory busCouponCategory = new BusCouponCategory();
					busCouponCategory.setCreateDate(new Date());
					busCouponCategory.setUpdateDate(new Date());
					busCouponCategory.setBusCategory(busCategory);
					busCouponCategory.setBusCoupon(busCoupon);
					list.add(busCouponCategory);
				}
				busCouponCategoryDao.saveAll(list);
			}
		}
		templateResourceService.updateTemplateResource(busUserId, TEMPLATE_DIR, TEMPLATE_NAME, coupon(busUserId), TEMPLATE_NAME);
		return busCoupon.getId();
	}
	
	private List<BusCouponRes> coupon(Integer busUserId){
		Date currentDate = new Date();
		List<BusCoupon> list = dao.findByBusUserIdAndEnableAndOfferStartLessThanEqualAndOfferEndAfterOrderByParValueAsc(busUserId, true, currentDate, currentDate);
		List<BusCouponRes> ress = new ArrayList<BusCouponRes>(list.size());
		for (BusCoupon busCoupon : list) {
			BusCouponRes res = new BusCouponRes();
			res.apply(busCoupon);
			if(res.getEnableCate()!=null && res.getEnableCate()) {
				List<BusCouponCategory> bccs = busCouponCategoryDao.findByBusCouponAndBusUserId(busCoupon , busUserId);
				List<VO> cvos = null;
				for (BusCouponCategory busCouponCategory : bccs) {
					if(cvos==null) {
						cvos = new ArrayList<VO>();
					}
					cvos.add(new BusCategoryRes().apply(busCouponCategory.getBusCategory()));
				}
				res.setCategorys(cvos);
			}
			ress.add(res);
		}
		return ress;
	}
	
	
	public VO get(Integer id) {
		if(id == null) {
			throw new ServiceException("ID不能为空！");
		}
		Integer busUserId = CurrentUser.getBusUserId();
		BusCoupon busCoupon = dao.findByIdAndBusUserId(id, busUserId);
		if(busCoupon==null) {
			throw new ServiceException("ID不存在！");
		}
		List<BusCouponCategory> list = busCouponCategoryDao.findByBusCouponAndBusUserId(busCoupon , busUserId);
		List<Integer> category = new ArrayList<Integer>();
		for (BusCouponCategory cate : list) {
			category.add(cate.getBusCategory().getId());
		}
		BusCouponRes busCouponRes = new BusCouponRes();
		busCouponRes.setCategory(category);
		busCouponRes.apply(busCoupon);
		return busCouponRes;
	}
	
	@Transactional
	public void update(UpdateBusCouponReq req) {
		Integer busUserId = CurrentUser.getBusUserId();
		BusCoupon busCoupon = dao.findByIdAndBusUserId(req.getId(), busUserId);
		if(busCoupon==null) {
			throw new ServiceException("ID不存在！");
		}
		req.update(busCoupon);
		dao.save(busCoupon);
		busCouponCategoryDao.deleteByBusCouponAndBusUserId(busCoupon , busUserId);
		Integer[] category = req.getCategory();
		if(req.getEnableCate() && category!=null && category.length>0) {
			List<BusCategory> busCategories = busCategoryDao.findByIdInAndBusUserId(Arrays.asList(category) , busUserId);
			if(busCategories.size()>0) {
				List<BusCouponCategory> list = new ArrayList<BusCouponCategory>(busCategories.size());
				for (BusCategory busCategory : busCategories) {
					BusCouponCategory busCouponCategory = new BusCouponCategory();
					busCouponCategory.setCreateDate(new Date());
					busCouponCategory.setUpdateDate(new Date());
					busCouponCategory.setBusCategory(busCategory);
					busCouponCategory.setBusCoupon(busCoupon);
					list.add(busCouponCategory);
				}
				busCouponCategoryDao.saveAll(list);
			}
		}
		templateResourceService.updateTemplateResource(busUserId, TEMPLATE_DIR, TEMPLATE_NAME, coupon(busUserId), TEMPLATE_NAME);
	}
	
	@Transactional
	public void enable(Integer id , Boolean enable) {
		if(id==null || enable==null) {
			throw new ServiceException("参数不正确！");
		}
		Integer busUserId = CurrentUser.getBusUserId();
		BusCoupon busCoupon = dao.findByIdAndBusUserId(id, busUserId);
		if(busCoupon==null) {
			throw new ServiceException("ID不存在！");
		}
		if(busCoupon.isEnable() != enable.booleanValue()) {
			busCoupon.setEnable(enable);
			busCoupon.setUpdateDate(new Date());
			dao.save(busCoupon);
			templateResourceService.updateTemplateResource(busUserId, TEMPLATE_DIR, TEMPLATE_NAME, coupon(busUserId), TEMPLATE_NAME);
		}
	}
	
	@Transactional
	public void delete(Integer id) {
		if(id==null) {
			throw new ServiceException("ID不能为空！");
		}
		Integer busUserId = CurrentUser.getBusUserId();
		BusCoupon busCoupon = dao.findByIdAndBusUserId(id, busUserId);
		if(busCoupon==null) {
			throw new ServiceException("ID不存在！");
		}
		int count = busWeUserCouponDao.countByBusCouponAndBusUserId(busCoupon, busUserId);
		if(count>0) {
			throw new ServiceException("删除失败，该优惠券已被微信用户领取！");
		}
		dao.delete(busCoupon);
		if(busCoupon.isEnable()) {
			templateResourceService.updateTemplateResource(busUserId, TEMPLATE_DIR, TEMPLATE_NAME, coupon(busUserId), TEMPLATE_NAME);
		}
	}
	
}
