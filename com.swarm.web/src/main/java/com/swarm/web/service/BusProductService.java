package com.swarm.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swarm.base.dao.BusProductDao;

@Service
@Transactional(readOnly = true)
public class BusProductService {
	
	@Autowired
	private BusProductDao dao;
	
}
