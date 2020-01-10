package com.swarm.base.service;

import org.springframework.stereotype.Component;

/**
 * 订单流程
 * @author Administrator
 *
 */
@Component
public class OrderProcess extends Process {
	
	/**
	 * 订单流程描述：
	 *  1.未付款===》已付款===》已发货|已提货===》已确认
	 *  2.未付款===》已付款===》申请退款===》已退款
	 *  3.未付款===》已付款===》申请退款===》拒绝退款申请===》已发货===》已确认
	 *  4.未付款===》已取消
	 */
	@Override
	public void buildProcess() {
		//结束流程节点
		Activity cancel = new Activity(Activity.WECHAT_USER,ActivityNode.CANCELLED,"取消", ActivityStatus.CANCELLED);
		Activity hasevaluation = new Activity(Activity.WECHAT_USER,ActivityNode.HASEVALUATION,"评价", ActivityStatus.HASEVALUATION);
		Activity refunded = new Activity(Activity.BUS_USER,ActivityNode.REFUNDED,"退款", ActivityStatus.REFUNDED);
		//其他流程节点
		Activity confirmed = new Activity(Activity.WECHAT_USER,ActivityNode.CONFIRMED,"确认收货", ActivityStatus.CONFIRMED,hasevaluation);
		Activity pickedup = new Activity(Activity.BUS_USER, ActivityNode.PICKEDUP, ActivityStatus.PENDING_CONFIRM,confirmed);
		Activity shipped = new Activity(Activity.BUS_USER,ActivityNode.SHIPPED,ActivityStatus.PENDING_CONFIRM, confirmed);
		Activity refuse_refund = new Activity(Activity.BUS_USER,ActivityNode.REFUSE_REFUND, ActivityStatus.PENDING_SHIP, shipped);
		Activity apply_refund = new Activity(Activity.WECHAT_USER,ActivityNode.APPLY_REFUND, ActivityStatus.PENDING_SHIP, refuse_refund,refunded);
		Activity paid = new Activity(Activity.WECHAT_USER,ActivityNode.PAID,"付款", ActivityStatus.PENDING_SHIP, apply_refund,shipped,pickedup);
		Activity payno = new Activity(Activity.WECHAT_USER,ActivityNode.PAY_NO, ActivityStatus.PENDING_PAYMENT, paid,cancel);
		this
			.add(pickedup)
			.add(cancel)
			.add(hasevaluation)
			.add(refunded)
			.add(confirmed)
			.add(shipped)
			.add(refuse_refund)
			.add(apply_refund)
			.add(paid)
			.add(payno);
	}

}
