package com.swarm.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swarm.base.entity.Attachment;

public interface AttachmentDao extends JpaRepository<Attachment, Integer> {
	
	public Attachment findFirstByMd5(String md5); 
	
	public List<Attachment> findByBusUserId(Integer busUserId);
	
	public List<Attachment> findByFiletypeLikeAndBusUserId(String fileType , Integer busUserId);
	
	public List<Attachment> findByFiletypeNotLikeAndBusUserId(String fileType , Integer busUserId);
	
	public List<Attachment> findByBusUserIdNull();
	
	public List<Attachment> findByFiletypeLikeAndBusUserIdNull(String fileType);
	
	public List<Attachment> findByFiletypeNotLikeAndBusUserIdNull(String fileType);
	
}
