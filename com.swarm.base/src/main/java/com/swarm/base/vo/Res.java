package com.swarm.base.vo;

import java.util.function.Function;

import com.swarm.base.entity.BaseEntity;

public abstract class Res<T extends BaseEntity> implements VO,Function<T, VO> {
	
}
