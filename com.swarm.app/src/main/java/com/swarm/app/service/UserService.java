package com.swarm.app.service;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONObject;
import com.swarm.app.vo.BusCategoryRes;
import com.swarm.app.vo.BusCouponRes;
import com.swarm.app.vo.BusDictRes;
import com.swarm.app.vo.BusWeUserFavoriteRes;
import com.swarm.app.vo.BusWeUserWalletRes;
import com.swarm.app.vo.BusWeWithdrawalReq;
import com.swarm.app.vo.BusWeWithdrawalRes;
import com.swarm.app.vo.BusWechatUserRes;
import com.swarm.app.vo.SysBusApplyReq;
import com.swarm.app.vo.SysDictRes;
import com.swarm.app.vo.UserInfo;
import com.swarm.base.dao.BusCouponCategoryDao;
import com.swarm.base.dao.BusCouponDao;
import com.swarm.base.dao.BusDictDao;
import com.swarm.base.dao.BusMnprogramDao;
import com.swarm.base.dao.BusProductDao;
import com.swarm.base.dao.BusWeUserCouponDao;
import com.swarm.base.dao.BusWeUserFavoriteDao;
import com.swarm.base.dao.BusWeUserWalletDao;
import com.swarm.base.dao.BusWeWithdrawalDao;
import com.swarm.base.dao.BusWechatPayNotifyDao;
import com.swarm.base.dao.BusWechatUserDao;
import com.swarm.base.dao.SysBusApplyDao;
import com.swarm.base.dao.SysDictDao;
import com.swarm.base.entity.BaseEntity;
import com.swarm.base.entity.BusCoupon;
import com.swarm.base.entity.BusCouponCategory;
import com.swarm.base.entity.BusDict;
import com.swarm.base.entity.BusMnprogram;
import com.swarm.base.entity.BusOrder;
import com.swarm.base.entity.BusProduct;
import com.swarm.base.entity.BusWeUserCoupon;
import com.swarm.base.entity.BusWeUserFavorite;
import com.swarm.base.entity.BusWeUserWallet;
import com.swarm.base.entity.BusWeWithdrawal;
import com.swarm.base.entity.BusWechatPayNotify;
import com.swarm.base.entity.BusWechatUser;
import com.swarm.base.entity.DictType;
import com.swarm.base.entity.PaymentType;
import com.swarm.base.entity.SysBusApply;
import com.swarm.base.entity.SysDict;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional(readOnly = true)
@CacheConfig(keyGenerator = "redisKeyGenerator")
public class UserService {
	
	/**
	 * GET 登录验证
	 */
	public static final String WECHAT_API_CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code&";

	
	@Autowired
	private BusProductDao busProductDao;
	
	@Autowired
	private BusWeUserFavoriteDao busWeUserFavoriteDao;
	
	@Autowired
	private BusWechatUserDao busWechatUserDao;
	
	@Autowired
	private BusWeUserWalletDao busWeUserWalletDao;
	
	@Autowired
	private BusWeUserCouponDao busWeUserCouponDao;
	
	@Autowired
	private BusMnprogramDao busMnprogramDao;
	
	@Autowired
	private BusCouponDao busCouponDao;
	
	@Autowired
	private BusDictDao busDictDao;
	
	@Autowired
	private SysDictDao sysDictDao;
	
	@Autowired
	private BusCouponCategoryDao busCouponCategoryDao;
	
	@Autowired
	private BusWechatPayNotifyDao busWechatPayNotifyDao;
	
	@Autowired
	private BusWeWithdrawalDao busWeWithdrawalDao;
	
	@Autowired
	private SysBusApplyDao sysBusApplyDao;
	
	private final RestTemplate restTemplate;
	
	
	@Autowired
	public UserService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	/**
	 * 微信授权登录
	 * @param busUserId
	 * @param code
	 * @return
	 */
	@Transactional
	public VO login(Integer busUserId , String js_code , Integer userId) {
		if(js_code==null) {
			throw new ServiceException("参数不正确！");
		}
		BusMnprogram busMnprogram = busMnprogramDao.findFirstByBusUserId(busUserId);
		String param = "appid="+busMnprogram.getAppID() + "&secret=" + busMnprogram.getAppSecret() + "&js_code="+js_code;
		String result = restTemplate.getForObject(WECHAT_API_CODE2SESSION_URL+param, String.class);
		JSONObject jsonObject = JSONObject.parseObject(result);
		if(!jsonObject.containsKey("openid")) {  //success			
			throw new ServiceException(result);
		}
		String openid = jsonObject.getString("openid");
		String session_key = jsonObject.getString("session_key");
		String unionid = jsonObject.getString("unionid");
		BusWechatUser busWechatUser = busWechatUserDao.findByOpenIdAndBusUserId(openid, busUserId);
		int coupons = 0;
		BusWeUserWallet busWeUserWallet = null;
		if(busWechatUser==null) {
			busWechatUser = new BusWechatUser();
			busWechatUser.setCreateDate(new Date());
			busWechatUser.setBusUserId(busUserId);
			if(userId!=null) {
				BusWechatUser parent = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
				if(parent!=null) {
					busWechatUser.setParent(parent);
				}
			}
			busWechatUser.setOpenId(openid);
			busWechatUser.setUnionId(unionid);
			busWechatUser.setUpdateDate(new Date());
			busWechatUser.setSessionKey(session_key);
			busWechatUser.setNickname(BusWechatUser.randomNickname());
			busWechatUserDao.save(busWechatUser);
			busWeUserWallet = new BusWeUserWallet();
			busWeUserWallet.setUpdateDate(new Date());
			busWeUserWallet.setCreateDate(new Date());
			busWeUserWallet.setBalance(new BigDecimal(0));
			busWeUserWallet.setBusUserId(busUserId);
			busWeUserWallet.setBusWechatUser(busWechatUser);
			busWeUserWalletDao.save(busWeUserWallet);
		}else {
			busWechatUser.setOpenId(openid);
			busWechatUser.setUnionId(unionid);
			busWechatUser.setUpdateDate(new Date());
			busWechatUser.setSessionKey(session_key);
			busWechatUserDao.save(busWechatUser);
			coupons = busWeUserCouponDao.countByBusWechatUserAndUsedAndBusUserId(busWechatUser, false, busUserId);
			busWeUserWallet = busWeUserWalletDao.findByBusWechatUserAndBusUserId(busWechatUser, busUserId);
		}
		
		UserInfo userInfo = new UserInfo();
		userInfo.setBusWechatUser(new BusWechatUserRes().apply(busWechatUser));
		userInfo.setBusWeUserWallet(new BusWeUserWalletRes().apply(busWeUserWallet));
		userInfo.setCoupons(coupons);
		return userInfo;
	}
	
	/**
	 * 设置用户授权的微信用户头像信息
	 * @param busUserId
	 * @param code
	 * @return
	 */
	@Transactional
	public VO setInfo(Integer busUserId , Integer userId , String res) {
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("用户不存在！");
		}
		JSONObject jsonObject = JSONObject.parseObject(res);
		JSONObject userInfo = jsonObject.getJSONObject("userInfo");
		busWechatUser.setNickname(userInfo.getString("nickName"));
		busWechatUser.setGender(userInfo.getIntValue("gender"));
		busWechatUser.setCity(userInfo.getString("city"));
		busWechatUser.setProvince(userInfo.getString("province"));
		busWechatUser.setCountry(userInfo.getString("country"));
		busWechatUser.setPortrait(userInfo.getString("avatarUrl"));
		busWechatUser.setUpdateDate(new Date());
		busWechatUserDao.save(busWechatUser);
		return new BusWechatUserRes().apply(busWechatUser);
	}
	
	@Cacheable(cacheNames = "usercoupon")
	public List<BusCouponRes> list(Integer busUserId ,Integer userId){
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("用户不存在！");
		}
		List<BusWeUserCoupon> list = busWeUserCouponDao.findByBusWechatUserAndUsedAndBusUserId(busWechatUser, false, busUserId);
		List<BusCouponRes> ress = new ArrayList<BusCouponRes>(list.size());
		for (BusWeUserCoupon busWeUserCoupon : list) {
			BusCouponRes res = new BusCouponRes();
			res.apply(busWeUserCoupon.getBusCoupon());
			if(res.getEnableCate()!=null && res.getEnableCate()) {
				List<BusCouponCategory> bccs = busCouponCategoryDao.findByBusCouponAndBusUserId(busWeUserCoupon.getBusCoupon(),busUserId);
				List<VO> cvos = null;
				for (BusCouponCategory busCouponCategory : bccs) {
					if(cvos==null) {
						cvos = new ArrayList<VO>();
					}
					cvos.add(new BusCategoryRes().apply(busCouponCategory.getBusCategory()));
				}
				res.setCategory(cvos);
			}
			ress.add(res);
		}
		return ress;
	}
	
	@CacheEvict(cacheNames = "usercoupon:#p0:#p1")
	@Transactional
	public void saveCoupon(Integer busUserId , Integer userId , Integer id) {
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("用户不存在！");
		}
		BusCoupon busCoupon = busCouponDao.findByIdAndBusUserId(id, busUserId);
		Date currentDate = new Date();
		if(busCoupon==null || !busCoupon.isEnable() || !(busCoupon.getOfferStart().before(currentDate) && busCoupon.getOfferEnd().after(currentDate))) {
			throw new ServiceException("优惠券不存在或已停止发放！");
		}
		int count = busWeUserCouponDao.countByBusWechatUserAndBusCouponAndBusUserId(busWechatUser, busCoupon, busUserId);
		if(count==0) {
			BusWeUserCoupon busWeUserCoupon = new BusWeUserCoupon();
			busWeUserCoupon.setBusCoupon(busCoupon);
			busWeUserCoupon.setBusUserId(busUserId);
			busWeUserCoupon.setBusWechatUser(busWechatUser);
			busWeUserCoupon.setCreateDate(currentDate);
			busWeUserCoupon.setUpdateDate(currentDate);
			busWeUserCoupon.setUsed(false);
			busWeUserCouponDao.save(busWeUserCoupon);
		}else {
			throw new ServiceException("您已领取该优惠券！");
		}
	}
	
	
	@CacheEvict(cacheNames = "favorite:#p0:#p2")
	@Transactional
	public void favorite(Integer busUserId , Integer id , Integer userId) {
		if(id==null || userId==null) {
			throw new ServiceException("参数不正确！");
		}
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("用户不存在！");
		}
		BusProduct busProduct = busProductDao.findByBusUserIdAndIdAndShowAndFlagNot(busUserId, id, true, -1);
		if(busProduct==null) {
			throw new ServiceException("商品不存在或已下架！");
		}
		List<BusWeUserFavorite> busWeUserFavorites = busWeUserFavoriteDao.findByBusProductAndBusWechatUserAndBusUserId(busProduct, busWechatUser, busUserId);
		if(busWeUserFavorites==null || busWeUserFavorites.size()==0) {
			BusWeUserFavorite busWeUserFavorite = new BusWeUserFavorite();
			busWeUserFavorite.setCreateDate(new Date());
			busWeUserFavorite.setUpdateDate(new Date());
			busWeUserFavorite.setBusProduct(busProduct);
			busWeUserFavorite.setBusUserId(busUserId);
			busWeUserFavorite.setBusWechatUser(busWechatUser);
			busWeUserFavoriteDao.save(busWeUserFavorite);
			busProductDao.increaseFavoriteByIdAndBusUserId(busProduct.getId(), busUserId);
		}else {
			busWeUserFavoriteDao.deleteAll(busWeUserFavorites);
		}
	}
	
	@Cacheable(cacheNames = "favorite")
	public Page<VO> myfavorite(Integer busUserId , Integer userId , Paging paging){
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("用户不存在！");
		}
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("id")));
		Page<BusWeUserFavorite> page = busWeUserFavoriteDao.findByBusWechatUserAndBusUserId(busWechatUser, busUserId, pageable);
		return page.map(new BusWeUserFavoriteRes());
	}
	
	@CacheEvict(cacheNames = "favorite:#p0:#p1")
	@Transactional
	public void delfavorite(Integer busUserId, Integer userId , Integer[] id) {
		if(id!=null && id.length>0) {
			busWeUserFavoriteDao.deleteByIdInAndBusWechatUserAndBusUserId(Arrays.asList(id), userId, busUserId);
		}
	}
	
	/**
	 * 微信充值预支付
	 * @param busUserId
	 * @param userId
	 * @param money
	 * @param body
	 * @param notify_url
	 */
	@Transactional
	public Map<String, Object> prepayRecharge(Integer busUserId, Integer userId , String money , String body , String notify_url) {
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("用户不存在！");
		}
		BusMnprogram busMnprogram = busMnprogramDao.findFirstByBusUserId(busUserId);
		BigDecimal amount = new BigDecimal(money);
		amount = amount.setScale(2, RoundingMode.DOWN);
		String out_trade_no = BusOrder.generateOrderCode(busUserId);
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
		Map<String, Object> resultMap = new HashMap<String, Object>();
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

	
	/**
	 * 微信充值
	 * @param busUserId
	 * @param userId
	 * @param money
	 */
	@Transactional
	public JSONObject recharge(Integer busUserId, Integer userId , String body) {
		JSONObject result = new JSONObject();
		try {
			//查询用户
			BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
			if(busWechatUser==null) {
				throw new ServiceException("用户不存在！");
			}
			//查询用户钱包
			BusWeUserWallet busWeUserWallet = busWeUserWalletDao.findByBusWechatUserAndBusUserId(busWechatUser, busUserId);
			BusMnprogram busMnprogram = busMnprogramDao.findFirstByBusUserId(busUserId);
			Map<String, String> paramMap = parseWechatApiXMLResult(busMnprogram, body);
			String out_trade_no = paramMap.get("out_trade_no");
			BusWechatPayNotify busWechatPayNotify = busWechatPayNotifyDao.findByOrderCodeAndBusWechatUserAndBusUserId(out_trade_no, busWechatUser, busUserId);
			if(busWechatPayNotify==null || busWechatPayNotify.getStatus()>0) {   //如果已支付，直接返回成功
				result.put("return_code", "SUCCESS");
				result.put("return_msg", "OK");
				return result;
			}
			//如果为未支付：0，则更新支付通知状态为，1：已支付
			int updateCount = busWechatPayNotifyDao.updateStatusByIdAndStatusAndBusUserId(1, new Date(), busWechatPayNotify.getId(), busWechatPayNotify.getStatus(), busUserId);
			if(updateCount<=0) {   //存在并发，返回失败结果
				result.put("return_code", "FAIL");
				result.put("return_msg", "concurrent execption！");
				log.warn("微信支付通知---- 钱包充值---- 并发修改数据库数据！");
				return result;
			}
			BigDecimal amount = new BigDecimal(paramMap.get("total_fee"));
			//钱包充值----增加充值金额
			busWeUserWalletDao.rechargeBusWeUserWallet(busWeUserWallet.getId(), busUserId, amount , busWeUserWallet.getBalance());
			result.put("return_code", "SUCCESS");
			result.put("return_msg", "OK");
			return result;
		} catch (Exception e) {
			result.put("return_code", "FAIL");
			result.put("return_msg", e.getMessage());
			return result;
		}
		
	}
	
	@Cacheable("dict")
	public List<VO>  bankDicts(Integer busUserId ,Integer userId){
		List<SysDict> list = sysDictDao.findByTypeOrderBySortAsc(DictType.BANK_TYPE);
		List<VO> vos = new ArrayList<VO>(list.size());
		for (SysDict dict : list) {
			SysDictRes res = new SysDictRes();
			res.setId(dict.getId());
			res.setValue(dict.getValue());
			vos.add(res);
		}
		return vos;
	}
	
	@Cacheable("dict")
	public List<VO>  withdrawDicts(Integer busUserId ,Integer userId){
		List<BusDict> list = busDictDao.findByTypeAndBusUserId(DictType.WITHDRAWAL_SET, busUserId);
		List<VO> vos = new ArrayList<VO>(list.size());
		for (BusDict dict : list) {
			BusDictRes res = new BusDictRes();
			res.setId(dict.getId());
			res.setValue(dict.getValue());
			vos.add(res);
		}
		return vos;
	}
	
	@Cacheable(cacheNames = "withdraw")
	public Page<VO> withdraw(Integer busUserId, Integer userId , Paging paging){
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("用户不存在！");
		}
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("id")));
		Page<BusWeWithdrawal> page = busWeWithdrawalDao.findByBusWechatUserAndBusUserId(busWechatUser, busUserId, pageable);
		return page.map(new BusWeWithdrawalRes());
	}
	
	@CacheEvict(cacheNames = "withdraw:#p0:#p1")
	@Transactional
	public VO withdrawSave(Integer busUserId , Integer userId , BusWeWithdrawalReq req) {
		BusWechatUser busWechatUser = busWechatUserDao.findByIdAndBusUserId(userId, busUserId);
		if(busWechatUser==null) {
			throw new ServiceException("用户不存在！");
		}
		List<BusDict> dicts = busDictDao.findByTypeAndBusUserId(DictType.WITHDRAWAL_SET, busUserId);
		String _min_withdrawal_amount = (DictType.WITHDRAWAL_SET.name()+"_min_withdrawal_amount").toLowerCase();
		BusDict minWithdrawalAmount = null;
		String _handlingFee_percent = (DictType.WITHDRAWAL_SET.name()+"_handlingFee_percent").toLowerCase();
		BusDict handlingFee_percent = null;
		for (BusDict busDict : dicts) {
			if(_min_withdrawal_amount.equals(busDict.getKey())) {
				minWithdrawalAmount = busDict;
			}
			if(_handlingFee_percent.equals(busDict.getKey())) {
				handlingFee_percent = busDict;
			}
		}
		if(minWithdrawalAmount!=null && Double.parseDouble(minWithdrawalAmount.getValue())>req.getMoney().doubleValue()) {
			throw new ServiceException("钱包提现最低额度为" + minWithdrawalAmount.getValue() + "！");
		}
		BusWeUserWallet busWeUserWallet = busWeUserWalletDao.findByBusWechatUserAndBusUserId(busWechatUser, busUserId);
		if(busWeUserWallet.getBalance().doubleValue()<req.getMoney().doubleValue()) {
			throw new ServiceException("钱包余额不足！");
		}
		BusWeWithdrawal busWeWithdrawal = req.create();
		busWeWithdrawal.setBusWechatUser(busWechatUser);
		busWeWithdrawal.setBusUserId(busUserId);
		if(req.getPaymentType()==PaymentType.UNIONPAY) {			
			Optional<SysDict> optional = sysDictDao.findById(req.getBankDict());
			if(!optional.isPresent()) {
				throw new ServiceException("选择的银行不存在！");
			}
			busWeWithdrawal.setBankDict(optional.get());
		}
		int rate = 10;
		if(handlingFee_percent!=null) {  //如果为空，默认为10%
			rate = Integer.parseInt(handlingFee_percent.getValue());
		}
		BigDecimal handlingFee = new BigDecimal(busWeWithdrawal.getMoney().doubleValue()*rate/100.0);
		handlingFee = handlingFee.setScale(2, RoundingMode.DOWN);
		busWeWithdrawal.setHandlingFee(handlingFee);
		busWeWithdrawal.setReceivedMoney(busWeWithdrawal.getMoney().subtract(handlingFee));
		busWeWithdrawalDao.save(busWeWithdrawal);
		return new BusWeWithdrawalRes().apply(busWeWithdrawal);
	}
	
	@Transactional
	public Integer busApply(Integer busUserId , SysBusApplyReq req) {
		SysBusApply sysBusApply = req.create();
		sysBusApply.setBusUserId(busUserId);
		sysBusApplyDao.save(sysBusApply);
		return sysBusApply.getId();
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
	
//	public static void main(String[] args) {
//		String ss1 =  
//				"<xml>" + 
//				"<appid>wxd930ea5d5a258f4f</appid>" + 
//				"<mch_id>10000100</mch_id>" + 
//				"<device_info>1000</device_info>" + 
//				"<body>test</body>" + 
//				"<nonce_str>ibuaiVcKdpRxkhJA</nonce_str>" + 
//				"<sign>9A0A8659F005D6984697E2CA0A9CF3B7</sign>" + 
//				"</xml>";
//		System.out.println(ss1);
//		byte[] bytes = ss1.getBytes();
//		try {
//			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//			Document document = documentBuilder.parse(new ByteArrayInputStream(bytes));
//			List<String> paramList = new LinkedList<String>();
//			Map<String, String> paramMap = new HashMap<String, String>();
//			NodeList nodeList = document.getDocumentElement().getChildNodes();
//			String sign = null;
//			for (int i = 0; i < nodeList.getLength(); i++) {
//				Node node = nodeList.item(i);
//				String name = node.getNodeName();
//				String value = node.getTextContent();
//				if("sign".equals(name)) {
//					sign = value;
//				}else {
//					if(StringUtils.isNotBlank(value)) {							
//						paramList.add(name + "=" + value);
//					}
//				}
//				paramMap.put(name, value);
//			}
//			//校验返回结果
//			paramList.sort(new AscallComparator());
//			String[] sss = paramList.toArray(new String[0]);
//			String ss = String.join("&", sss) + "&key=192006250b4c09247ec02edce69f6a2d";
//			String md5 = BaseEntity.generateMD5(ss).toUpperCase();
//			System.out.println(md5.equals(sign));
//		} catch (Exception e) {
//		 	throw new ServiceException("解析微信预支付接口API返回的xml结果异常！", e);
//		}
//	}
	
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
	
	public static class AscallComparator implements Comparator<String>{

		@Override
		public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
		
	}
	
}
