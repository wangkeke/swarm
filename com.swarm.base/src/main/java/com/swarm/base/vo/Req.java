package com.swarm.base.vo;

import com.swarm.base.entity.BaseEntity;

public abstract class Req<T extends BaseEntity> implements VO{
	
	public abstract T build();
	
}
