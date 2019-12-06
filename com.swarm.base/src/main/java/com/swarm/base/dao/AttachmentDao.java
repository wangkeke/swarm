package com.swarm.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.Attachment;

public interface AttachmentDao extends JpaRepository<Attachment, Integer> {
	
	public Attachment findFirstByMd5(String md5); 
	
}
