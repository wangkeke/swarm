package com.swarm.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.app.vo.BusWeUserAddressReq;
import com.swarm.app.vo.BusWeUserAddressRes;
import com.swarm.app.vo.UpdateBusWeUserAddressReq;
import com.swarm.base.dao.BusWeUserAddressDao;
import com.swarm.base.dao.BusWechatUserDao;
import com.swarm.base.entity.BusWeUserAddress;
import com.swarm.base.entity.BusWechatUser;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.VO;

@Service
@Transactional(readOnly = true)
@CacheConfig(keyGenerator = "redisKeyGenerator")
public class AddressService {
	
	@Autowired
	private BusWeUserAddressDao busWeUserAddressDao;
	
	@Autowired
	private BusWechatUserDao busWechatUserDao;
	
	
	@Cacheable(cacheNames = "address")
	public List<VO> list(Integer busUserId , Integer userId){
		if(userId==null) {
			throw new ServiceException("参数不正确！");
		}
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("用户不存在！");
		}
		List<BusWeUserAddress> list = busWeUserAddressDao.findByBusWechatUserAndBusUserIdAndFlagNot(busWechatUser, busUserId, -1);
		List<VO> vos = new ArrayList<VO>(list.size());
		for (BusWeUserAddress address : list) {
			vos.add(new BusWeUserAddressRes().apply(address));
		}
		return vos;
	}
	
	@CacheEvict(cacheNames = "address",key = "'address:'+#p0+':'+#p1")
	@Transactional
	public Integer save(Integer busUserId , Integer userId , BusWeUserAddressReq req) {
		if(userId==null) {
			throw new ServiceException("参数不正确！");
		}
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("用户不存在！");
		}
		BusWeUserAddress busWeUserAddress = req.create();
		busWeUserAddress.setBusUserId(busUserId);
		if(busWeUserAddress.isFirst()) {   //清除其他首选项			
			busWeUserAddressDao.clearBusWeUserAddressFirst(busUserId, busWechatUser);
		}
		busWeUserAddressDao.save(busWeUserAddress);
		return busWeUserAddress.getId();
	}
	
	@CacheEvict(cacheNames = "address",key = "'address:'+#p0+':'+#p1")
	@Transactional
	public void update(Integer busUserId , Integer userId , UpdateBusWeUserAddressReq req) {
		if(userId==null) {
			throw new ServiceException("参数不正确！");
		}
		BusWeUserAddress busWeUserAddress = busWeUserAddressDao.findByIdAndBusUserId(req.getId(), busUserId);
		if(busWeUserAddress==null || busWeUserAddress.getBusWechatUser().getId()!=userId) {
			throw new ServiceException("收货地址不存在！");
		}
		req.update(busWeUserAddress);
		if(req.getFirst()!=null && req.getFirst()!=busWeUserAddress.isFirst() && req.getFirst()) {  //清除其他首选项			
			busWeUserAddressDao.clearBusWeUserAddressFirst(busUserId, busWeUserAddress.getBusWechatUser());
			busWeUserAddress.setFirst(true);
		}
		busWeUserAddressDao.save(busWeUserAddress);
	}
	
	@Cacheable(cacheNames = "address")
	@Transactional
	public void first(Integer busUserId , Integer userId , Integer id) {
		if(userId==null || id==null) {
			throw new ServiceException("参数不正确！");
		}
		BusWeUserAddress busWeUserAddress = busWeUserAddressDao.findByIdAndBusUserId(id, busUserId);
		if(busWeUserAddress==null || busWeUserAddress.getBusWechatUser().getId()!=userId) {
			throw new ServiceException("收货地址不存在！");
		}
		if(!busWeUserAddress.isFirst()) {
			busWeUserAddressDao.clearBusWeUserAddressFirst(busUserId, busWeUserAddress.getBusWechatUser());
			busWeUserAddress.setFirst(true);
			busWeUserAddressDao.save(busWeUserAddress);
		}
	}
	
	@CacheEvict(cacheNames = "address",key = "'address:'+#p0+':'+#p1")
	@Transactional
	public void delete(Integer busUserId , Integer userId , Integer id){
		if(userId==null || id==null) {
			throw new ServiceException("参数不正确！");
		}
		BusWeUserAddress busWeUserAddress = busWeUserAddressDao.findByIdAndBusUserId(id, busUserId);
		if(busWeUserAddress==null || busWeUserAddress.getBusWechatUser().getId()!=userId) {
			throw new ServiceException("收货地址不存在！");
		}
		busWeUserAddressDao.delete(busWeUserAddress);
	}
	
}
