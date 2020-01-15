package com.swarm.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.app.vo.BusAdvertisingRes;
import com.swarm.app.vo.BusCategoryRes;
import com.swarm.app.vo.BusCouponRes;
import com.swarm.app.vo.BusImageRes;
import com.swarm.app.vo.BusMenuRes;
import com.swarm.app.vo.BusProductRes;
import com.swarm.app.vo.BusSalesRuleRes;
import com.swarm.app.vo.BusStoreInfoRes;
import com.swarm.app.vo.IndexRes;
import com.swarm.base.dao.BusAdvertisingDao;
import com.swarm.base.dao.BusCouponCategoryDao;
import com.swarm.base.dao.BusCouponDao;
import com.swarm.base.dao.BusImageDao;
import com.swarm.base.dao.BusMenuDao;
import com.swarm.base.dao.BusProductDao;
import com.swarm.base.dao.BusSalesRuleDao;
import com.swarm.base.dao.BusStoreInfoDao;
import com.swarm.base.entity.BusAdvertising;
import com.swarm.base.entity.BusCoupon;
import com.swarm.base.entity.BusCouponCategory;
import com.swarm.base.entity.BusImage;
import com.swarm.base.entity.BusImageType;
import com.swarm.base.entity.BusMenu;
import com.swarm.base.entity.BusProduct;
import com.swarm.base.entity.BusSalesRule;
import com.swarm.base.entity.BusStoreInfo;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;

@Service
@Transactional(readOnly = true)
@CacheConfig(keyGenerator = "redisKeyGenerator")
public class IndexService {
	
	@Autowired
	private BusImageDao busImageDao;
	
	@Autowired
	private BusProductDao busProductDao;
	
	@Autowired
	private BusMenuDao busMenuDao;
	
	@Autowired
	private BusAdvertisingDao busAdvertisingDao;
	
	@Autowired
	private BusSalesRuleDao busSalesRuleDao;
	
	@Autowired
	private BusCouponDao busCouponDao;
	
	@Autowired
	private BusCouponCategoryDao busCouponCategoryDao;
	
	@Autowired
	private BusStoreInfoDao busStoreInfoDao;
	
	
	@Cacheable(cacheNames = "index")
	public VO index(Integer busUserId) {
		List<VO> carousel = carousel(busUserId);
		List<VO> menu = menu(busUserId);
		VO advertising = advertising(busUserId);
		List<VO> salesRules = salesRules(busUserId);
		List<BusCouponRes> coupon = coupon(busUserId);
		IndexRes res = new IndexRes();
		res.setAdvertising(advertising);
		res.setCarousel(carousel);
		res.setCoupon(coupon);
		res.setMenu(menu);
		res.setSalesRules(salesRules);
		return res;
	}
	
	@Cacheable(cacheNames = "product")
	public Page<VO> search(Integer busUserId , String keyword , Paging paging) {
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Direction.DESC, "sales","favorite","id");
		Page<BusProduct> page = null;
		if(StringUtils.isBlank(keyword)) {
			page = busProductDao.findByShowAndBusUserIdAndFlagNot(true, busUserId, -1, pageable);
		}else {			
			page = busProductDao.findByTitleLikeAndShowAndBusUserIdAndFlagNot("%"+keyword+"%", true, busUserId, -1, pageable);
		}
		return page.map(new BusProductRes());
	}
	
	@Cacheable(cacheNames = "carousel")
	public List<VO> carousel(Integer busUserId){
		List<BusImage> list = busImageDao.findByBusUserIdAndBusImageTypeOrderBySortDesc(busUserId, BusImageType.SHOP_HOME_CAROUSEL);
		List<VO> vos = new ArrayList<VO>(list.size());
		for (BusImage busImage : list) {
			vos.add(new BusImageRes().apply(busImage));
		}
		return vos;
	}
	
	@Cacheable(cacheNames = "menu")
	public List<VO> menu(Integer busUserId){
		List<BusMenu> list = busMenuDao.findByShowAndBusUserIdOrderBySortDesc(true, busUserId);
		List<VO> menus = new ArrayList<VO>();
		for (BusMenu busMenu : list) {
			menus.add(new BusMenuRes().apply(busMenu));
		}
		return menus;
	}
	
	@Cacheable(cacheNames = "advertising")
	public VO advertising(Integer busUserId) {
		Date currentDate = new Date();
		BusAdvertising busAdvertising = busAdvertisingDao.findFirstByBusUserIdAndEnableAndStartDateLessThanEqualAndEndDateAfterOrderByIdDesc(busUserId, true, currentDate, currentDate);
		if(busAdvertising!=null) {
			return new BusAdvertisingRes().apply(busAdvertising);
		}
		return null;
	}
	
	
	@Cacheable(cacheNames = "salesRule")
	public List<VO> salesRules(Integer busUserId){
		List<BusSalesRule> list = busSalesRuleDao.findByBusUserIdAndEnable(busUserId, true);
		List<VO> rules = new ArrayList<VO>(list.size());
		for (BusSalesRule busSalesRule : list) {
			rules.add(new BusSalesRuleRes().apply(busSalesRule));
		}
		return rules;
	}
	
	@Cacheable(cacheNames = "salesRule")
	public String details(Integer busUserId , Integer ruleId) {
		if(ruleId==null) {
			throw new ServiceException("参数不正确！");
		}
		BusSalesRule busSalesRule = busSalesRuleDao.findByBusUserIdAndEnableAndId(busUserId, true, ruleId);
		return busSalesRule.getContent();
	}
	
	@Cacheable(cacheNames = "product")
	public Page<VO> products(Integer busUserId, Paging paging){
		Pageable pageable = PageRequest.of(0, paging.getSize(), Direction.DESC, "label.sort","sales","favorite","id");
		Page<BusProduct> page = busProductDao.findByShowAndBusUserIdAndFlagNot(true, busUserId, -1, pageable);
		return page.map(new BusProductRes());
	}
	
	@Cacheable(cacheNames = "coupon")
	public List<BusCouponRes> coupon(Integer busUserId){
		Date currentDate = new Date();
		List<BusCoupon> list = busCouponDao.findByBusUserIdAndEnableAndOfferStartLessThanEqualAndOfferEndAfterOrderByParValueAsc(busUserId, true, currentDate, currentDate);
		List<BusCouponRes> ress = new ArrayList<BusCouponRes>(list.size());
		for (BusCoupon busCoupon : list) {
			BusCouponRes res = new BusCouponRes();
			res.apply(busCoupon);
			if(res.getEnableCate()!=null && res.getEnableCate()) {
				List<BusCouponCategory> bccs = busCouponCategoryDao.findByBusCouponAndBusUserId(busCoupon, busUserId);
				List<VO> cvos = null;
				for (BusCouponCategory busCouponCategory : bccs) {
					if(cvos==null) {
						cvos = new ArrayList<VO>();
					}
					cvos.add(new BusCategoryRes().apply(busCouponCategory.getBusCategory()));
				}
				res.setCategory(cvos);
			}
			ress.add(res);
		}
		return ress;
	}
	
	@Cacheable(cacheNames = "storeInfo")
	public VO storeInfo(Integer busUserId){
		BusStoreInfo busStoreInfo = busStoreInfoDao.findFirstByBusUserId(busUserId);
		return new BusStoreInfoRes().apply(busStoreInfo);
	}
	
}
