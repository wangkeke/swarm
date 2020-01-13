package com.swarm.base.vo;

import java.util.Collection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@EqualsAndHashCode
public class Paging implements VO {
	
	public static final int DEFAULT_PAGE = 0;
	public static final int DEFAULT_SIZE = 20;
	
	/**
	 * 请求的页码
	 */
	@Setter
	private Integer page;
	
	/**
	 * 页大小
	 */
	@Setter
	private Integer size;
	
	/**
	 * 数据总数
	 */
	@Getter
	@Setter
	private long count;
	
	/**
	 * 总页数
	 */
	@Getter
	@Setter
	private int total;
	
	
	/**
	 * 页数据
	 */
	@Getter
	@Setter
	private Collection data;
	
	
	public Paging() {}
	
	public Paging(Integer page , Integer size){
		this.page = page;
		this.size = size;
	}
	
	
	public Integer getPage() {
		if(this.page==null || this.page<=0) {
			return DEFAULT_PAGE;
		}
		return this.page-1;
	}
	
	public Integer getSize(){
		if(this.size==null || this.size<=0) {
			this.size = DEFAULT_SIZE;
		}
		return this.size;
	}
	
}
