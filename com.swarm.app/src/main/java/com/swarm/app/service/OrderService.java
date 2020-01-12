package com.swarm.app.service;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONObject;
import com.swarm.app.service.UserService.AscallComparator;
import com.swarm.app.vo.BusOrderProductRes;
import com.swarm.app.vo.BusOrderReq;
import com.swarm.app.vo.BusOrderRes;
import com.swarm.app.vo.BusProductCommentReq;
import com.swarm.app.vo.BusProductRes;
import com.swarm.base.dao.BusCouponCategoryDao;
import com.swarm.base.dao.BusMnprogramDao;
import com.swarm.base.dao.BusOrderAddressDao;
import com.swarm.base.dao.BusOrderCouponDao;
import com.swarm.base.dao.BusOrderDao;
import com.swarm.base.dao.BusOrderProductDao;
import com.swarm.base.dao.BusPickcodeDao;
import com.swarm.base.dao.BusProductCommentDao;
import com.swarm.base.dao.BusProductDao;
import com.swarm.base.dao.BusRecordDao;
import com.swarm.base.dao.BusSalesRuleDao;
import com.swarm.base.dao.BusWeUserAddressDao;
import com.swarm.base.dao.BusWeUserCouponDao;
import com.swarm.base.dao.BusWeUserWalletDao;
import com.swarm.base.dao.BusWechatPayNotifyDao;
import com.swarm.base.dao.BusWechatUserDao;
import com.swarm.base.entity.BaseEntity;
import com.swarm.base.entity.BusCoupon;
import com.swarm.base.entity.BusCouponCategory;
import com.swarm.base.entity.BusMnprogram;
import com.swarm.base.entity.BusOrder;
import com.swarm.base.entity.BusOrderAddress;
import com.swarm.base.entity.BusOrderCoupon;
import com.swarm.base.entity.BusOrderProduct;
import com.swarm.base.entity.BusPickcode;
import com.swarm.base.entity.BusProduct;
import com.swarm.base.entity.BusProductComment;
import com.swarm.base.entity.BusRecord;
import com.swarm.base.entity.BusSalesRule;
import com.swarm.base.entity.BusWeUserAddress;
import com.swarm.base.entity.BusWeUserCoupon;
import com.swarm.base.entity.BusWeUserWallet;
import com.swarm.base.entity.BusWechatPayNotify;
import com.swarm.base.entity.BusWechatUser;
import com.swarm.base.entity.CouponType;
import com.swarm.base.service.Activity;
import com.swarm.base.service.ActivityNode;
import com.swarm.base.service.OrderProcess;
import com.swarm.base.service.SalesRuleHandler;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.ActivityRes;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Transactional(readOnly = true)
@Service
public class OrderService {
	
	@Autowired
	private BusWechatUserDao busWechatUserDao;
	
	@Autowired
	private BusMnprogramDao busMnprogramDao;
	
	@Autowired
	private BusOrderDao busOrderDao;
	
	@Autowired
	private BusRecordDao busRecordDao;
	
	@Autowired
	private BusProductCommentDao busProductCommentDao;
	
	@Autowired
	private BusProductDao busProductDao;
	
	@Autowired
	private BusOrderProductDao busOrderProductDao;
	
	@Autowired
	private BusOrderAddressDao busOrderAddressDao;
	
	@Autowired
	private BusPickcodeDao busPickcodeDao;
	
	@Autowired
	private BusWechatPayNotifyDao busWechatPayNotifyDao;
	
	@Autowired
	private BusWeUserCouponDao busWeUserCouponDao;
	
	@Autowired
	private BusCouponCategoryDao busCouponCategoryDao;
	
	@Autowired
	private BusWeUserAddressDao busWeUserAddressDao;
	
	@Autowired
	private BusOrderCouponDao busOrderCouponDao;
	
	@Autowired
	private BusSalesRuleDao busSalesRuleDao;
	
	@Autowired
	private BusWeUserWalletDao busWeUserWalletDao;
	
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@Autowired
	private OrderProcess orderProcess;
	
	private final RestTemplate restTemplate;
	
	@Autowired
	public OrderService(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}
	
	
	public Page<VO> page(Integer busUserId ,Integer userId , ActivityNode[] nodes , Paging paging){
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("用户不存在！");
		}
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("id")));
		Page<BusOrder> page = null;
		if(nodes==null || nodes.length==0) {			
			page = busOrderDao.findByBusWechatUserAndBusUserId(busWechatUser, busUserId, pageable);
		}else {
			page = busOrderDao.findByBusWechatUserAndActivityNodeInAndBusUserId(busWechatUser, Arrays.asList(nodes), busUserId, pageable);
		}
		List<BusOrderProduct> products = busOrderProductDao.findByBusOrderInAndBusUserId(page.getContent(), busUserId);
		Map<Integer, List<BusOrderProduct>> productMap = new HashMap<Integer, List<BusOrderProduct>>();
		for (BusOrderProduct busOrderProduct : products) {
			List<BusOrderProduct> ps = productMap.get(busOrderProduct.getBusOrder().getId());
			if(ps==null) {
				ps = new ArrayList<BusOrderProduct>();
				productMap.put(busOrderProduct.getBusOrder().getId(), ps);
			}
			ps.add(busOrderProduct);
		}
		
		Page<VO> mapPage = page.map(new BusOrderRes());
		for (VO vo : mapPage.getContent()) {
			BusOrderRes res = (BusOrderRes)vo;
			List<BusOrderProduct> busOrderProducts = productMap.get(res.getId());
			if(busOrderProducts!=null) {
				List<VO> vos = new ArrayList<VO>(busOrderProducts.size());
				for (BusOrderProduct p : busOrderProducts) {
					vos.add(new BusOrderProductRes().apply(p));
				}
				res.setProducts(vos);
			}
			Activity activity = orderProcess.get(res.getActivityNode().getActivityNode());
			List<VO> nexts = null;
			for (Activity a : activity.getNexts()) {
				if(a.getOperator()==null || a.getOperator()==Activity.WECHAT_USER) {
					if(nexts==null) {
						nexts = new ArrayList<VO>();
					}
					nexts.add(new ActivityRes().apply(a));
				}
			}
			if(nexts!=null) {
				res.setButtonActivitys(nexts);
			}
		}
		return mapPage;
	}
	
	@Transactional
	public void process(Integer busUserId ,Integer userId , Integer id ,ActivityNode node) {
		if(id==null || node==null) {
			throw new ServiceException("参数不正确！");
		}
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("用户不存在！");
		}
		BusOrder busOrder = busOrderDao.findByIdAndBusWechatUserAndBusUserId(id, busWechatUser, busUserId);
		if(busOrder==null) {
			throw new ServiceException("订单不存在！");
		}
		Activity activity = orderProcess.get(busOrder.getActivityNode());
		List<Activity> nexts = activity.getNexts();
		Activity activity2 = null;
		for (Activity a : nexts) {
			if(a.getNode()==node && (a.getOperator()==null || a.getOperator()==Activity.WECHAT_USER)) {
				activity2 = a;
				break;
			}
		}
		if(activity2==null) {
			throw new ServiceException("不存在的流程操作！");
		}
		//添加订单流程记录
		BusRecord record = new BusRecord();
		record.setUpdateDate(new Date());
		record.setCreateDate(new Date());
		record.setActivityNode(busOrder.getActivityNode());
		record.setBusOrder(busOrder);
		record.setBusWechatUser(busWechatUser);
		record.setBusUserId(busUserId);
		record.setComment(busOrder.getComment());
		busRecordDao.save(record);
		//修改订单流程状态
		busOrder.setUpdateDate(new Date());
		busOrder.setActivityNode(node);
		busOrderDao.save(busOrder);
	}
	
	@Transactional
	public void comment(Integer busUserId , Integer userId , Integer id , BusProductCommentReq req) {
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("用户不存在！");
		}
		BusOrder busOrder = busOrderDao.findByIdAndBusWechatUserAndBusUserId(id, busWechatUser, busUserId);
		if(busOrder==null) {
			throw new ServiceException("订单不存在！");
		}
		if(busOrder.getActivityNode()!=ActivityNode.CONFIRMED && busOrder.getActivityNode()!=ActivityNode.HASEVALUATION) {
			throw new ServiceException("订单暂不能评价！");
		}
		List<BusOrderProduct> products = busOrderProductDao.findByBusOrderAndBusUserId(busOrder , busUserId);
		Map<Integer, BusOrderProduct> map = new HashMap<Integer, BusOrderProduct>();
		for (BusOrderProduct busOrderProduct : products) {
			map.put(busOrderProduct.getBusProduct().getId(), busOrderProduct);
		}
		Date currentDate = new Date();
		for (int i = 0; i < req.getId().length; i++) {
		 	BusOrderProduct busOrderProduct = map.get(req.getId()[i]);
			if(busOrderProduct==null) {
				throw new ServiceException("不存在的商品ID！");
			}
		 	BusProductComment busProductComment = new BusProductComment();
			busProductComment.setUpdateDate(currentDate);
			busProductComment.setCreateDate(currentDate);
			busProductComment.setBusProduct(busOrderProduct.getBusProduct());
			busProductComment.setBusUserId(busUserId);
			busProductComment.setContent(req.getContent()[i]);
			if(req.getHasImg()[i]) {
				busProductComment.setImages(req.getImage()[i]);
			}
			busProductCommentDao.save(busProductComment);
		}
	}
	
	public List<VO> products(Integer busUserId , Integer[] ids){
		List<VO> list = new ArrayList<VO>();
		if(ids==null || ids.length==0) {
			return list;
		}
		List<BusProduct> busProducts = busProductDao.findByIdInAndBusUserIdAndShowAndFlagNot(Arrays.asList(ids), busUserId, true, -1);
		for (BusProduct busProduct : busProducts) {
			BusProductRes res = new BusProductRes();
			res.setId(busProduct.getId());
			res.setTitle(busProduct.getTitle());
			res.setPrice(busProduct.getPrice());
			res.setStocks(busProduct.getStocks());
			res.setImage(busProduct.getImage());
			list.add(res);
		}
		return list;
	}
	
	@Transactional
	public Map<String, Object> save(Integer busUserId , Integer userId , BusOrderReq req , String notify_url) {
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("用户不存在！");
		}
		BusOrder busOrder = new BusOrder();
		busOrder.setUpdateDate(new Date());
		busOrder.setCreateDate(new Date());
		busOrder.setActivityNode(ActivityNode.PAY_NO);
		busOrder.setBusUserId(busUserId);
		busOrder.setBusWechatUser(busWechatUser);
		busOrder.setOnlyPay(false);
		if(req.getWalletPay()!=null && req.getWalletPay()) {
			busOrder.setWalletPay(true);
		}else {
			busOrder.setWalletPay(false);
		}
		String out_trade_no = BusOrder.generateOrderCode(busUserId);
		busOrder.setOrderCode(out_trade_no);
		List<BusOrderProduct> busOrderProducts = null;
		List<BusOrderCoupon> busOrderCoupons = null;
		BusOrderAddress busOrderAddress = null;
		BusPickcode busPickcode = null;
		BigDecimal count = new BigDecimal(0);
		count = count.setScale(2, RoundingMode.DOWN);
		if(req.getProductId()==null || req.getProductId().length==0) {   //单纯的买单付款
			busOrder.setAmount(req.getAmount());
			busOrder.setSelfpick(false);
			busOrder.setRealAmount(req.getAmount());
			busOrder.setOnlyPay(true);
			count = count.add(req.getAmount());
		}else {
			List<BusProduct> busProducts = busProductDao.findByIdInAndBusUserId(Arrays.asList(req.getProductId()), busUserId);
			Map<Integer, BusProduct> productMap = new HashMap<Integer, BusProduct>();
			for (BusProduct busProduct : busProducts) {
				productMap.put(busProduct.getId(), busProduct);
			}
			int colorIndex = 0 , sizeIndex = 0;
			for (int i = 0; i < req.getProductId().length; i++) {
				BusProduct busProduct = productMap.get(req.getProductId()[i]);
				if(busProduct==null) {
					throw new ServiceException("部分商品不存在！");
				}
				count = count.add(busProduct.getPrice().multiply(new BigDecimal(req.getNumber()[i])));
				BusOrderProduct busOrderProduct = new BusOrderProduct();
				busOrderProduct.setBusOrder(busOrder);
				busOrderProduct.setUpdateDate(new Date());
				busOrderProduct.setCreateDate(new Date());
				busOrderProduct.setBusProduct(busProduct);
				busOrderProduct.setNumber(req.getNumber()[i]);
				busOrderProduct.setPrice(busProduct.getPrice());
				busOrderProduct.setBusUserId(busUserId);
				busOrderProduct.setImage(busProduct.getImage());
				busOrderProduct.setTitle(busProduct.getTitle());
				if(StringUtils.isNotBlank(busProduct.getColors())) {					
					busOrderProduct.setColor(req.getColor()[colorIndex]);
					colorIndex++;
				}
				if(StringUtils.isNotBlank(busProduct.getSizes())) {					
					busOrderProduct.setSize(req.getSize()[sizeIndex]);
					sizeIndex++;
				}
				if(busOrderProducts==null) {
					busOrderProducts = new ArrayList<BusOrderProduct>(req.getProductId().length);
				}
				busOrderProducts.add(busOrderProduct);
			}
			//优惠券
			if(req.getUserCouponId()!=null) {
				boolean overlay = true;
				for (int i = 0; i < req.getUserCouponId().length; i++) {
					BusWeUserCoupon busWeUserCoupon = busWeUserCouponDao.findByBusWechatUserAndIdAndBusUserId(busWechatUser, req.getUserCouponId()[i], busUserId);
					if(busWeUserCoupon==null) {
						throw new ServiceException("优惠券不存在！");
					}
					if(busWeUserCoupon.isUsed()) {
						throw new ServiceException("优惠券已使用！");
					}
					BusCoupon busCoupon = busWeUserCoupon.getBusCoupon();
					Date currentDate = new Date();
					if(busCoupon.getUseStart().after(currentDate) || busCoupon.getUseEnd().before(currentDate)) {
						throw new ServiceException("优惠券已过期！");
					}
					if(!busCoupon.isEnable()) {
						throw new ServiceException("优惠券暂时不可用！");
					}
					if(busCoupon.getMinAmount().doubleValue()>count.doubleValue()) {
						throw new ServiceException("优惠券最小消费限制为" + busCoupon.getMinAmount().doubleValue() + "！");
					}
					if(busCoupon.isEnableCate()) {
						List<BusCouponCategory> busCouponCategories = busCouponCategoryDao.findByBusCouponAndBusUserId(busCoupon, busUserId);
						Map<Integer, BusCouponCategory> busCouponCategoryMap = new HashMap<Integer, BusCouponCategory>();
						for (BusCouponCategory busCouponCategory : busCouponCategories) {
							busCouponCategoryMap.put(busCouponCategory.getBusCategory().getId(), busCouponCategory);
						}
						for (BusProduct busProduct : productMap.values()) {
							if(!busCouponCategoryMap.containsKey(busProduct.getCategory().getId())) {
								throw new ServiceException("优惠券不能用于该分类的商品！");
							}
						}
					}
					if(!busCoupon.isOverlay()) {
						if(!overlay) {
							throw new ServiceException("优惠券不可叠加！");
						}else {
							overlay = false;
						}
					}
					if(busCoupon.getCouponType()==CouponType.COUPON_DISCOUNT_TYPE) {						
						count = count.multiply(busCoupon.getParValue());
					}else {
						count = count.subtract(busCoupon.getParValue());
					}
					BusOrderCoupon busOrderCoupon = new BusOrderCoupon();
					busOrderCoupon.setCreateDate(currentDate);
					busOrderCoupon.setUpdateDate(currentDate);
					busOrderCoupon.setBusOrder(busOrder);
					busOrderCoupon.setBusWeUserCoupon(busWeUserCoupon);
					if(busOrderCoupons==null) {
						busOrderCoupons = new ArrayList<BusOrderCoupon>();
					}
					busOrderCoupons.add(busOrderCoupon);
				}
			}
			//收货地址或自提
			if(req.getSelfpick()!=null && req.getSelfpick()) {
				busOrder.setSelfpick(true);
				busPickcode = new BusPickcode();
				busPickcode.setCreateDate(new Date());
				busPickcode.setUpdateDate(new Date());
				busPickcode.setBusOrder(busOrder);
				busPickcode.setBusUserId(busUserId);
				if(StringUtils.isBlank(req.getName())) {
					throw new ServiceException("请输入自提人姓名！");
				}
				busPickcode.setName(req.getName());
				if(StringUtils.isBlank(req.getPhone())) {
					throw new ServiceException("请输入自提人电话！");
				}
				busPickcode.setPhone(req.getPhone());
				busPickcode.setUsed(false);
			}else {
				busOrder.setSelfpick(false);
				if(req.getAddressId()==null) {
					throw new ServiceException("请选择收货地址！");
				}
				BusWeUserAddress address = busWeUserAddressDao.findByIdAndBusWechatUserAndBusUserId(req.getAddressId(), busWechatUser, busUserId);
				if(address==null) {
					throw new ServiceException("收货地址不存在！");
				}
				busOrderAddress = new BusOrderAddress();
				busOrderAddress.setUpdateDate(new Date());
				busOrderAddress.setCreateDate(new Date());
				busOrderAddress.setAddress(address.getAddress());
				busOrderAddress.setBusOrder(busOrder);
				busOrderAddress.setBusUserId(busUserId);
				busOrderAddress.setContact(address.getContact());
				busOrderAddress.setLocation(address.getLocation());
				busOrderAddress.setPhone(address.getPhone());
			}
		}
		if(Math.abs(count.doubleValue()-req.getAmount().doubleValue())>1) {
			throw new ServiceException("金额不正确！");
		}
		busOrder.setAmount(count);
		busOrder.setRealAmount(req.getAmount());
		busOrderDao.save(busOrder);
		if(busOrderProducts!=null) {
			busOrderProductDao.saveAll(busOrderProducts);
		}
		if(busOrderCoupons!=null) {
			busOrderCouponDao.saveAll(busOrderCoupons);
		}
		if(busOrderAddress!=null) {
			busOrderAddressDao.save(busOrderAddress);
		}
		if(busPickcode!=null) {
			busPickcodeDao.save(busPickcode);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(busOrder.isWalletPay()) {  //如果通过钱包支付
			BusWeUserWallet busWeUserWallet = busWeUserWalletDao.findByBusWechatUserAndBusUserId(busWechatUser, busUserId);
			if(busWeUserWallet==null || busWeUserWallet.getBalance().doubleValue()<busOrder.getRealAmount().doubleValue()) {
				throw new ServiceException("钱包余额不足！");
			}
			int updateCount = busWeUserWalletDao.chargeBusWeUserWallet(busWeUserWallet.getId(), busUserId, busOrder.getRealAmount(), busWeUserWallet.getBalance());
			if(updateCount==0) {   //存在并发，重新提交
				throw new ServiceException("操作失败，请重新提交订单！");
			}
			resultMap.put("orderId", busOrder.getId());
			return resultMap;
		}
		BusMnprogram busMnprogram = busMnprogramDao.findFirstByBusUserId(busUserId);
		BigDecimal amount = req.getAmount();
		String body = "商品购买支付订单";
		BusWechatPayNotify notify = new BusWechatPayNotify();
		notify.setBusUserId(busUserId);
		notify.setBusWechatUser(busWechatUser);
		notify.setCreateDate(new Date());
		notify.setUpdateDate(new Date());
		notify.setMoney(amount);
		notify.setBody(body);
		notify.setNotifyUrl(notify_url);
		notify.setOrderCode(out_trade_no);
		busWechatPayNotifyDao.save(notify);
		Map<String, String> paramMap = wechatPrepayApiClient(busMnprogram, busWechatUser, amount, body, notify_url , out_trade_no);
		resultMap.put("orderCode", out_trade_no);
		resultMap.put("package", "prepay_id="+paramMap.get("prepay_id"));
		String nonce_str = BaseEntity.generateMD5(busMnprogram.getAppID() + busMnprogram.getPayBusNumber() + System.currentTimeMillis());
		resultMap.put("nonceStr", nonce_str);
		long timeStamp = System.currentTimeMillis()/1000;
		resultMap.put("timeStamp", timeStamp);
		StringBuilder builder = new StringBuilder();
		builder.append("appId=" + busMnprogram.getAppID())
			.append("&nonceStr=" + nonce_str)
			.append("&package=" + paramMap.get("package"))
			.append("&signType=MD5")
			.append("&timeStamp=" + timeStamp)
			.append("&key=" + busMnprogram.getPaySecretKey());
		String paySign = BaseEntity.generateMD5(builder.toString()).toUpperCase();
		resultMap.put("signType", "MD5");
		resultMap.put("paySign", paySign);
		return resultMap;		
	}

	
	@Transactional
	public JSONObject paid(Integer busUserId,Integer userId,String body) {
		JSONObject result = new JSONObject();
		try {
			//查询用户
			BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
			if(busWechatUser==null) {
				throw new ServiceException("用户不存在！");
			}
			//查询用户钱包
			BusMnprogram busMnprogram = busMnprogramDao.findFirstByBusUserId(busUserId);
			Map<String, String> paramMap = parseWechatApiXMLResult(busMnprogram, body);
			String out_trade_no = paramMap.get("out_trade_no");
			BusWechatPayNotify busWechatPayNotify = busWechatPayNotifyDao.findByOrderCodeAndBusWechatUserAndBusUserId(out_trade_no, busWechatUser, busUserId);
			if(busWechatPayNotify==null || busWechatPayNotify.getStatus()>0) {   //如果已支付，直接返回成功
				result.put("return_code", "SUCCESS");
				result.put("return_msg", "OK");
				return result;
			}
			BigDecimal amount = new BigDecimal(paramMap.get("total_fee"));
			if(Math.abs(amount.doubleValue()-busWechatPayNotify.getMoney().doubleValue())>=1) {
				result.put("return_code", "FAIL");
				result.put("return_msg", "支付金额不正确！");
				return result;
			}
			//如果为未支付：0，则更新支付通知状态为，1：已支付
			int updateCount = busWechatPayNotifyDao.updateStatusByIdAndStatusAndBusUserId(1, new Date(), busWechatPayNotify.getId(), busWechatPayNotify.getStatus(), busUserId);
			if(updateCount<=0) {   //存在并发，返回失败结果
				result.put("return_code", "FAIL");
				result.put("return_msg", "concurrent execption！");
				log.warn("微信支付通知---- 商品支付---- 并发修改数据库数据！");
				return result;
			}
			BusOrder busOrder = busOrderDao.findFirstByOrderCodeAndBusWechatUserAndBusUserId(out_trade_no, busWechatUser, busUserId);
			if(busOrder.isOnlyPay()) {				
				if(busOrder.getActivityNode()!=ActivityNode.CONFIRMED) {					
					updateCount = busOrderDao.updateActivityByIdAndBusUserId(ActivityNode.CONFIRMED, new Date(), busOrder.getId(), busUserId, busOrder.getActivityNode());
					if(updateCount>0) {
						threadPoolTaskExecutor.execute(new SalesRuleTask(busSalesRuleDao, busOrder, busWechatUser, busUserId, amount));
					}
				}
			}else {
				if(busOrder.getActivityNode()!=ActivityNode.PAID) {					
					updateCount = busOrderDao.updateActivityByIdAndBusUserId(ActivityNode.PAID, new Date(), busOrder.getId(), busUserId, busOrder.getActivityNode());
					if(updateCount>0) {
						threadPoolTaskExecutor.execute(new SalesRuleTask(busSalesRuleDao, busOrder, busWechatUser, busUserId, amount));
					}
				}
			}
			result.put("return_code", "SUCCESS");
			result.put("return_msg", "OK");
			return result;
		} catch (Exception e) {
			result.put("return_code", "FAIL");
			result.put("return_msg", e.getMessage());
			return result;
		}
	}
	
	public static class SalesRuleTask implements Runnable{
		
		private BusSalesRuleDao busSalesRuleDao;
		private BusOrder busOrder;
		private BusWechatUser busWechatUser;
		private Integer busUserId;
		private BigDecimal amount;
		private Object[] args;
		
		public SalesRuleTask(BusSalesRuleDao busSalesRuleDao , BusOrder busOrder , BusWechatUser busWechatUser , Integer busUserId , BigDecimal amount , Object... args) {
			this.busSalesRuleDao = busSalesRuleDao;
			this.busOrder = busOrder;
			this.busWechatUser = busWechatUser;
			this.busUserId = busUserId;
			this.amount = amount;
			this.args = args;
		}
		
		@Override
		public void run() {
			List<BusSalesRule> rules = busSalesRuleDao.findByBusUserIdAndEnable(busUserId, true);
			for (BusSalesRule r : rules) {			
				SalesRuleHandler handler = SalesRuleHandler.getHandler(r.getType());
				if(handler!=null) {					
					handler.handle(busOrder, busWechatUser, busUserId, amount, args);
				}
			}
		}
		
	}
	
	private Map<String, String> parseWechatApiXMLResult(BusMnprogram busMnprogram , String result){
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = documentBuilder.parse(new InputSource(new StringReader(result)));
			Node return_code = document.getElementsByTagName("return_code").item(0);
			if("SUCCESS".equals(return_code.getTextContent())) {
				List<String> paramList = new LinkedList<String>();
				Map<String, String> paramMap = new HashMap<String, String>();
				NodeList nodeList = document.getChildNodes();
				String sign = null;
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node node = nodeList.item(i);
					String name = node.getNodeName();
					String value = node.getTextContent();
					if("sign".equals(name)) {
						sign = value;
					}else {
						if(StringUtils.isNotBlank(value)) {							
							paramList.add(name + "=" + value);
						}
					}
					paramMap.put(name, value);
				}
				//校验返回结果
				paramList.sort(new AscallComparator());
				String[] sss = paramList.toArray(new String[0]);
				String ss = String.join("&", sss) + "&key=" + busMnprogram.getPaySecretKey();
				String md5 = BaseEntity.generateMD5(ss).toUpperCase();
				if(md5.equals(sign)) {
					return paramMap;
				}else {
					throw new ServiceException("签名验证失败！");
				}
				
			}else {
				log.error(result);
				throw new ServiceException(document.getElementsByTagName("return_msg").item(0).getTextContent());
			}
		} catch (Exception e) {
		 	throw new ServiceException("参数格式校验错误！", e);
		}
	}
	
	
	public static final String WECHAT_PREPAY_API_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	/**
	 * 微信预支付调用API接口
	 * @param money
	 */
	private Map<String, String> wechatPrepayApiClient(BusMnprogram busMnprogram , BusWechatUser busWechatUser , BigDecimal money , String body , String notify_url , String out_trade_no) {
		String requestbody = wechatPrepayRequestBody(busMnprogram,busWechatUser, money, body, notify_url , out_trade_no);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(WECHAT_PREPAY_API_URL, requestbody , String.class);
		return parseWechatApiXMLResult(busMnprogram, responseEntity.getBody());
	}

	
	private String wechatPrepayRequestBody(BusMnprogram busMnprogram , BusWechatUser busWechatUser , BigDecimal money , String body , String notify_url , String out_trade_no) {
		String ipaddress = BaseEntity.getIPAddress();
		String nonce_str = BaseEntity.generateMD5(busMnprogram.getAppID() + busMnprogram.getPayBusNumber() + System.currentTimeMillis());
		double amount = money.doubleValue();
		StringBuilder signBuilder = new StringBuilder();
		signBuilder
			.append("appid=" + busMnprogram.getAppID())
			.append("&attach=" + busMnprogram.getBusUserId())
			.append("&body=" + body)
			.append("&mch_id=" + busMnprogram.getPayBusNumber())
			.append("&nonce_str=" + nonce_str)
			.append("&notify_url=" + notify_url)
			.append("&openid=" + busWechatUser.getOpenId())
			.append("&out_trade_no=" + out_trade_no)
			.append("&spbill_create_ip=" + ipaddress)
			.append("&total_fee=" + amount)
			.append("&trade_type=JSAPI")
			.append("&key=" + busMnprogram.getPaySecretKey());

		StringBuilder builder = new StringBuilder();
		builder.append("<xml>")
		.append("<appid>" + busMnprogram.getAppID() + "</appid>")
		.append("<attach>" + busMnprogram.getBusUserId() + "</attach>")
		.append("<body>" + body + "</body>")
		.append("<mch_id>" + busMnprogram.getPayBusNumber() + "</mch_id>")
		.append("<nonce_str>" + nonce_str + "</nonce_str>")
		.append("<notify_url>" + notify_url + "</notify_url>")
		.append("<openid>" + busWechatUser.getOpenId() + "</openid>")
		.append("<out_trade_no>" + out_trade_no + "</out_trade_no>")
		.append("<spbill_create_ip>" + ipaddress + "</spbill_create_ip>")
		.append("<total_fee>" + amount + "</total_fee>")
		.append("<trade_type>JSAPI</trade_type>")
		.append("<sign>" + BaseEntity.generateMD5(signBuilder.toString()).toUpperCase() + "</sign>")
		.append("</xml>");
		return builder.toString();
	}
	
}
