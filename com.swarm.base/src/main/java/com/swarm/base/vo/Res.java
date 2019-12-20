package com.swarm.base.vo;

import java.util.Date;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.swarm.base.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Res<T extends BaseEntity> implements VO,Function<T, VO> {
	
	public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	protected Integer id;
	
	@JsonFormat(shape = Shape.STRING , pattern = DEFAULT_DATETIME_PATTERN)
	protected Date updateDate;
	
	@JsonFormat(shape = Shape.STRING , pattern = DEFAULT_DATETIME_PATTERN)
	protected Date createDate;
	
	
}
