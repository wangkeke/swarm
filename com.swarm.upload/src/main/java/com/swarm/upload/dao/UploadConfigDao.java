package com.swarm.upload.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.upload.entity.UploadConfig;

public interface UploadConfigDao extends JpaRepository<UploadConfig, Integer>{
	
	public UploadConfig findFirstByLabel(String label);
	
}
