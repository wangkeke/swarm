package com.swarm.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.app.vo.BusCategoryRes;
import com.swarm.app.vo.BusProductCommentRes;
import com.swarm.app.vo.BusProductRes;
import com.swarm.base.dao.BusProductCommentDao;
import com.swarm.base.dao.BusProductDao;
import com.swarm.base.entity.BusProduct;
import com.swarm.base.entity.BusProductComment;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;

@Service
@Transactional(readOnly = true)
@CacheConfig(keyGenerator = "redisKeyGenerator")
public class ProductService {
	
	@Autowired
	private BusProductDao busProductDao;
	
	@Autowired
	private BusProductCommentDao busProductCommentDao;

	
	@Cacheable("product")
	public VO details(Integer busUserId , Integer id) {
		if(id==null) {
			throw new ServiceException("参数不正确！");
		}
		BusProduct busProduct = busProductDao.findByBusUserIdAndIdAndShowAndFlagNot(busUserId, id, true, -1);
		if(busProduct==null) {
			throw new ServiceException("商品不存在或已下架！");
		}
		BusProductRes res = new BusProductRes();
		res.apply(busProduct);
		res.setCategory(new BusCategoryRes().apply(busProduct.getCategory()));
		res.setImages(busProduct.getImages());
		res.setContent(busProduct.getContent());
		return res;
	}
	
	@Cacheable(cacheNames = "comment")
	public Page<VO> comment(Integer busUserId , Integer id , Paging paging){
		if(id==null) {
			throw new ServiceException("参数不正确！");
		}
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize());
		BusProduct busProduct = busProductDao.findByBusUserIdAndIdAndShowAndFlagNot(busUserId, id, true, -1);
		if(busProduct==null) {
			throw new ServiceException("商品不存在或已下架！");
		}
		Page<BusProductComment> page = busProductCommentDao.findByBusProductAndBusUserId(busProduct, busUserId , pageable);
		return page.map(new BusProductCommentRes());
	}
	
}
