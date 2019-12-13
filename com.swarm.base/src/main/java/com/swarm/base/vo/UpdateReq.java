package com.swarm.base.vo;

import com.swarm.base.entity.BaseEntity;

public abstract class UpdateReq<T extends BaseEntity> implements VO{
	
	public abstract void update(T t);
	
}
