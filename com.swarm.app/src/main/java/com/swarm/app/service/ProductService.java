package com.swarm.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.swarm.app.vo.BusProductCommentRes;
import com.swarm.app.vo.BusProductRes;
import com.swarm.base.dao.BusProductCommentDao;
import com.swarm.base.dao.BusProductDao;
import com.swarm.base.dao.BusWeUserFavoriteDao;
import com.swarm.base.dao.BusWechatUserDao;
import com.swarm.base.entity.BusProduct;
import com.swarm.base.entity.BusProductComment;
import com.swarm.base.entity.BusWeUserFavorite;
import com.swarm.base.entity.BusWechatUser;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;

@Service
@Transactional(readOnly = true)
public class ProductService {
	
	@Autowired
	private BusProductDao busProductDao;
	
	@Autowired
	private BusWeUserFavoriteDao busWeUserFavoriteDao;
	
	@Autowired
	private BusWechatUserDao busWechatUserDao;
	
	@Autowired
	private BusProductCommentDao busProductCommentDao;
	
	
	public VO details(Integer busUserId , Integer id) {
		if(id==null) {
			throw new ServiceException("参数不正确！");
		}
		BusProduct busProduct = busProductDao.findByBusUserIdAndIdAndShowAndFlagNot(busUserId, id, true, -1);
		if(busProduct==null) {
			throw new ServiceException("商品不存在或已下架！");
		}
		BusProductRes res = new BusProductRes();
		res.setImages(busProduct.getImages());
		res.setContent(busProduct.getContent());
		return res;
	}
	
	@Transactional
	public void favorite(Integer busUserId , Integer id , Integer userId) {
		if(id==null || userId==null) {
			throw new ServiceException("参数不正确！");
		}
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("不存在的用户！");
		}
		BusProduct busProduct = busProductDao.findByBusUserIdAndIdAndShowAndFlagNot(busUserId, id, true, -1);
		if(busProduct==null) {
			throw new ServiceException("该商品不存在或已下架！");
		}
		List<BusWeUserFavorite> busWeUserFavorites = busWeUserFavoriteDao.findByBusProductAndBusWechatUserAndBusUserId(busProduct, busWechatUser, busUserId);
		if(busWeUserFavorites==null || busWeUserFavorites.size()==0) {
			BusWeUserFavorite busWeUserFavorite = new BusWeUserFavorite();
			busWeUserFavorite.setCreateDate(new Date());
			busWeUserFavorite.setUpdateDate(new Date());
			busWeUserFavorite.setBusProduct(busProduct);
			busWeUserFavorite.setBusUserId(busUserId);
			busWeUserFavorite.setBusWechatUser(busWechatUser);
			busWeUserFavoriteDao.save(busWeUserFavorite);
		}else {
			busWeUserFavoriteDao.deleteAll(busWeUserFavorites);
		}
	}
	
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
