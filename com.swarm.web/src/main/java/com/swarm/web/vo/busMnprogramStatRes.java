package com.swarm.web.vo;

import com.swarm.base.entity.BusMnprogramStat;
import com.swarm.base.vo.Res;
import com.swarm.base.vo.VO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class busMnprogramStatRes extends Res<BusMnprogramStat> {
	
	/**
	 * 统计日期，格式：yyyyMMdd
	 */
	private String statDate;
	
	/**
	 * 累计用户数
	 */
	private Integer visitTotal;
	
	/**
	 * 转发次数
	 */
	private Integer sharePv;
	
	/**
	 * 转发人数
	 */
	private Integer shareUv;
	
	/**
	 * 新增用户
	 */
	private Integer visitUvNew;
	
	/**
	 * 活跃用户
	 */
	private Integer visitUv;

	@Override
	public VO apply(BusMnprogramStat t) {
		this.id = t.getId();
		this.updateDate = t.getUpdateDate();
		this.createDate = t.getCreateDate();
		this.statDate = t.getStatDate();
		this.visitTotal = t.getVisitTotal();
		this.sharePv = t.getSharePv();
		this.shareUv = t.getShareUv();
		this.visitUv = t.getVisitUv();
		this.visitUvNew = t.getVisitUvNew();
		return this;
	}
	
}
