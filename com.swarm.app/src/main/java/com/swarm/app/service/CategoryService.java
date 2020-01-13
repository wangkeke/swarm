package com.swarm.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.app.vo.BusCategoryRes;
import com.swarm.app.vo.BusProductRes;
import com.swarm.base.dao.BusCategoryDao;
import com.swarm.base.dao.BusProductDao;
import com.swarm.base.entity.BusCategory;
import com.swarm.base.entity.BusProduct;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.VO;

@Service
@Transactional(readOnly = true)
@CacheConfig(keyGenerator = "redisKeyGenerator")
public class CategoryService {
	
	@Autowired
	private BusCategoryDao busCategoryDao;
	
	@Autowired
	private BusProductDao busProductDao;
	
	@Cacheable("category")
	public List<BusCategoryRes> category(Integer busUserId){
		List<BusCategory> list = busCategoryDao.findByBusUserIdAndShow(busUserId, true);
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
	
	@Cacheable("product")
	public Page<VO> products(Integer busUserId , Integer categoryId , Pageable pageable){
		Page<BusProduct> page = null;
		if(categoryId==null) {
			page = busProductDao.findByShowAndBusUserIdAndFlagNot(true, busUserId, -1, pageable);
		}else {			
			BusCategory busCategory = busCategoryDao.findFirstByIdAndBusUserId(categoryId, busUserId);
			if(busCategory==null) {
				throw new ServiceException("不存在的分类！");
			}
			page = busProductDao.findByCategoryAndShowAndBusUserIdAndFlagNot(busCategory, true, busUserId, -1, pageable);
		}
		return page.map(new BusProductRes());
	}
	
}
