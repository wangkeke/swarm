package com.swarm.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.admin.vo.BusUserReq;
import com.swarm.admin.vo.BusUserRes;
import com.swarm.admin.vo.UpdateBusUserReq;
import com.swarm.base.dao.BusDictDao;
import com.swarm.base.dao.BusMenuDao;
import com.swarm.base.dao.BusSalesRuleDao;
import com.swarm.base.dao.BusUserDao;
import com.swarm.base.dao.SysDictDao;
import com.swarm.base.dao.SysMenuDao;
import com.swarm.base.dao.SysSalesRuleDao;
import com.swarm.base.entity.BusDict;
import com.swarm.base.entity.BusMenu;
import com.swarm.base.entity.BusSalesRule;
import com.swarm.base.entity.BusUser;
import com.swarm.base.entity.SysDict;
import com.swarm.base.entity.DictType;
import com.swarm.base.entity.SysMenu;
import com.swarm.base.entity.SysSalesRule;
import com.swarm.base.entity.SysUser;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;

@Service
@Transactional(readOnly = true)
public class BusUserService {
	
	@Autowired
	private BusUserDao dao;
	
	@Autowired
	private SysMenuDao sysMenuDao;
	
	@Autowired
	private BusMenuDao busMenuDao;
	
	@Autowired
	private SysDictDao sysDictDao;
	
	@Autowired
	private BusDictDao busDictDao;
	
	@Autowired
	private SysSalesRuleDao sysSalesRuleDao;
	
	@Autowired
	private BusSalesRuleDao busSalesRuleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	public Page<VO> page(String keyword , Paging paging){
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("createDate")));
		Page<BusUser> page = null;
		if(StringUtils.isBlank(keyword)) {
			page = dao.findAll(pageable);
		}else {
			page = dao.findByUsernameLikeOrDescLike("%" + keyword + "%","%"+keyword+"%",pageable);
		}
		return page.map(new BusUserRes());
	}
	
	public boolean validUsername(String username) {
		if(StringUtils.isBlank(username))
			return false;
		int count = dao.countByUsername(username);
		return count>0?false:true;
	}
	
	/**
	 * 创建商家用户，需要复制相应的数据信息给商家用户(菜单，字典，促销规则)
	 * @param req
	 * @return
	 */
	public Integer save(BusUserReq req) {
		if(!validUsername(req.getUsername())) {
			throw new  ServiceException("用户名已存在！");
		}
		BusUser busUser = req.create();
		busUser.setPassword(passwordEncoder.encode(req.getPassword()));
		dao.save(busUser);
		//复制菜单
		List<SysMenu> sysMenus = sysMenuDao.findByFlagNot(-1);
		List<BusMenu> busMenus = new ArrayList<BusMenu>();
		for (SysMenu sysMenu : sysMenus) {
			BusMenu busMenu = new BusMenu();
			busMenu.setBusUserId(busUser.getId());
			busMenu.setCreateDate(new Date());
			busMenu.setUpdateDate(new Date());
			busMenu.setDesc(sysMenu.getDesc());
			busMenu.setIcon(sysMenu.getIcon());
			busMenu.setKey(sysMenu.getKey());
			busMenu.setName(sysMenu.getName());
			busMenu.setShow(true);
			busMenu.setSort(sysMenu.getSort());
			busMenu.setUrl(sysMenu.getUrl());
			busMenus.add(busMenu);
		}
		busMenuDao.saveAll(busMenus);
		//复制促销规则
		List<SysSalesRule> sysSalesRules = sysSalesRuleDao.findByEnable(true);
		List<BusSalesRule> busSalesRules = new ArrayList<BusSalesRule>();
		for (SysSalesRule sysSalesRule : sysSalesRules) {
			BusSalesRule rule = new BusSalesRule();
			rule.setCreateDate(new Date());
			rule.setUpdateDate(new Date());
			rule.setBusUserId(busUser.getId());
			rule.setConfigUrl(sysSalesRule.getConfigUrl());
			rule.setContent(sysSalesRule.getContent());
			rule.setEnable(true);
			rule.setIcon(sysSalesRule.getIcon());
			rule.setType(sysSalesRule.getType());
			rule.setName(sysSalesRule.getName());
			busSalesRules.add(rule);
		}
		busSalesRuleDao.saveAll(busSalesRules);
		//复制业务字典信息
		List<DictType> types = new ArrayList<DictType>(DictType.values().length);
		for (DictType t : DictType.values()) {
			if(t.getId()==DictType.PRIVATE) {
				types.add(t);
			}
		}
		if(types.size()>0) {			
			List<SysDict> sysDicts = sysDictDao.findByTypeInAndParent(types, null);
			List<BusDict> busDicts = new ArrayList<BusDict>(sysDicts.size());
			Map<Integer, BusDict> sysbusMap = new HashMap<Integer, BusDict>();
			while(true) {
				if(sysDicts==null || sysDicts.isEmpty())
					break;
				busDicts.clear();
				for (SysDict sysDict : sysDicts) {
					BusDict bus = new BusDict();
					bus.setBusUserId(busUser.getId());
					bus.setCreateDate(new Date());
					bus.setUpdateDate(new Date());
					bus.setDesc(sysDict.getDesc());
					bus.setKey(sysDict.getKey());
					bus.setSort(sysDict.getSort());
					bus.setType(sysDict.getType());
					bus.setValue(sysDict.getValue());
					bus.setValue2(sysDict.getValue2());
					if(sysDict.getParent()!=null) {
						bus.setParent(sysbusMap.get(sysDict.getParent().getId()));
					}
					busDicts.add(bus);
					sysbusMap.put(sysDict.getId(), bus);
				}
				busDictDao.saveAll(busDicts);
				sysDicts = sysDictDao.findByTypeInAndParentIn(types, sysDicts);
			}
			
		}
		
		return busUser.getId();
	}
	
	
	
	public VO get(Integer id) {
		if(id == null)
			throw new ServiceException("ID不能为空！");
		Optional<BusUser> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("不存在的ID！");
		}
		BusUser busUser = optional.get();
	 	return new BusUserRes().apply(busUser);
	}
	
	public void update(UpdateBusUserReq req) {
		if(req.getId() == null)
			throw new ServiceException("ID不能为空！");
		Optional<BusUser> optional = dao.findById(req.getId());
		if(!optional.isPresent()) {
			throw new ServiceException("不存在的ID！");
		}
		BusUser busUser = optional.get();
		if(!busUser.getUsername().equals(req.getUsername())) {
			if(!validUsername(req.getUsername())) {
				throw new  ServiceException("用户名已存在！");
			}
		}
		req.update(busUser);
		busUser.setPassword(passwordEncoder.encode(req.getPassword()));
		dao.save(busUser);
	}
	
	public String resetPwd(Integer id) {
		if(id == null)
			throw new ServiceException("ID不能为空！");
		Optional<BusUser> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("不存在的ID！");
		}
		BusUser busUser = optional.get();
		busUser.setUpdateDate(new Date());
		busUser.setPassword(passwordEncoder.encode(SysUser.DEFAULT_PASSWORD));
		dao.save(busUser);
		return SysUser.DEFAULT_PASSWORD;
	}
	
	public void enable(Integer id , Boolean enable) {
		if(id==null)
			throw new ServiceException("ID不能为空！");
		if(enable==null) {
			throw new ServiceException("请选择是否启用！");
		}
		Optional<BusUser> optional = dao.findById(id);
		if(!optional.isPresent()) {
			throw new ServiceException("ID不存在！");
		}
		BusUser busUser = optional.get();
		if(busUser.isEnable() != enable.booleanValue()) {
			busUser.setEnable(enable);
			dao.save(busUser);
		}
	}
	
}
