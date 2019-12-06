package com.swarm.zuul.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.zuul.entity.UploadConfig;


public interface UploadConfigDao extends JpaRepository<UploadConfig, Integer>{
	
	public UploadConfig findFirstByLabel(String label);
	
}
