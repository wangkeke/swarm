package com.swarm.base.service;

/**
 * 订单流程
 * @author Administrator
 *
 */
public class OrderProcess extends Process {
	
	/**
	 * 订单流程描述：
	 *  1.未付款===》已付款===》已发货===》已确认
	 *  2.未付款===》已付款===》申请退款===》已退款
	 *  3.未付款===》已付款===》申请退款===》拒绝退款申请===》已发货===》已确认
	 *  4.未付款===》已取消
	 */
	@Override
	public void buildProcess() {
		//结束流程节点
		Activity cancel = new Activity(ActivityNode.CANCELLED,"取消", ActivityStatus.CANCELLED);
		Activity confirmed = new Activity(ActivityNode.CONFIRMED,"确认收货", ActivityStatus.CONFIRMED);
		Activity refunded = new Activity(ActivityNode.REFUNDED,"退款", ActivityStatus.REFUNDED);
		//其他流程节点
		Activity shipped = new Activity(ActivityNode.SHIPPED,ActivityStatus.PENDING_CONFIRM, confirmed);
		Activity refuse_refund = new Activity(ActivityNode.REFUSE_REFUND, ActivityStatus.PENDING_SHIP, shipped);
		Activity apply_refund = new Activity(ActivityNode.APPLY_REFUND, ActivityStatus.PENDING_REFUNDING, refuse_refund,refunded);
		Activity paid = new Activity(ActivityNode.PAID,"付款", ActivityStatus.PENDING_SHIP, apply_refund,shipped);
		Activity payno = new Activity(ActivityNode.PAY_NO, ActivityStatus.PENDING_PAYMENT, paid,cancel);
		this
			.add(cancel)
			.add(refunded)
			.add(confirmed)
			.add(shipped)
			.add(refuse_refund)
			.add(apply_refund)
			.add(paid)
			.add(payno);
	}

}
