package com.swarm.web.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.swarm.base.dao.BusMnprogramDao;
import com.swarm.base.dao.BusMnprogramStatDao;
import com.swarm.base.dao.BusWeApiInfoDao;
import com.swarm.base.dao.BusWechatUserDao;
import com.swarm.base.entity.BaseEntity;
import com.swarm.base.entity.BusMnprogram;
import com.swarm.base.entity.BusMnprogramStat;
import com.swarm.base.entity.BusWeApiInfo;
import com.swarm.base.entity.BusWechatUser;
import com.swarm.base.service.ServiceException;
import com.swarm.base.vo.Paging;
import com.swarm.base.vo.VO;
import com.swarm.web.CurrentUser;
import com.swarm.web.vo.BusWeApiInfoRes;
import com.swarm.web.vo.BusWechatUserRes;
import com.swarm.web.vo.busMnprogramStatRes;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * 微信小程序API接口调用
 */
@Getter
@Log4j2
@Service
@Transactional
public class BusWeApiInfoService{
	
	/**
	 * GET 获取ACCESS_TOKEN
	 */
	public static final String WECHAT_API_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&"; 
	
	/**
	 * GET 登录验证
	 */
	public static final String WECHAT_API_CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code&";
	
	/**
	 * POST 获取用户访问小程序日留存
	 */
	public static final String WECHAT_API_DAILYRETAIN_URL = "https://api.weixin.qq.com/datacube/getweanalysisappiddailyretaininfo?access_token=";
	
	/**
	 * POST 获取用户访问小程序数据概况
	 */
	public static final String WECHAT_API_DAILYSUMMARY_URL = "https://api.weixin.qq.com/datacube/getweanalysisappiddailysummarytrend?access_token=";
	
	/**
	 * POST 不受限制的二维码接口
	 */
	public static final String WECHAT_API_WXACODEUNLIMIT_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=";
	
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd");
	
	@Value("${file.upload.dir}")
	private String rootDir;
	
	@Value("${wechat.material.path.qrcodebg}")
	private String qrcodebgPath;
	
	@Value("${wechat.material.path.paybg}")
	private String paybgPath;
	
	@Value("${wechat.mnprogram.url.payment}")
	private String paymentURL;
	
	@Value("${wechat.material.qrcode.width}")
	private Integer qrcodeWidth;
	
	
	@Autowired
	private BusWeApiInfoDao dao;
	
	@Autowired
	private BusMnprogramDao busMnprogramDao;
	
	@Autowired
	private BusWechatUserDao busWechatUserDao;
	
	@Autowired
	private BusMnprogramStatDao busMnprogramStatDao;
	
	@Autowired
	private ScheduledExecutorService scheduledExecutorService;
	
	private final RestTemplate restTemplate;
	
	@Autowired
	public BusWeApiInfoService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	private BusWeApiInfo refreshAccessToken(Integer busUserId , BusMnprogram busMnprogram , BusWeApiInfo busWeApiInfo) {
		if(busWeApiInfo==null || StringUtils.isBlank(busWeApiInfo.getAccess_token()) || 
				(busWeApiInfo.getErrcode()!=null && busWeApiInfo.getErrcode()!=0) ||
				System.currentTimeMillis()-busWeApiInfo.getTakeTime().getTime()>=busWeApiInfo.getExpires_in()*1000){
					String param = "appid="+busMnprogram.getAppID() + "&secret=" + busMnprogram.getAppSecret();
					String result = restTemplate.getForObject(WECHAT_API_ACCESS_TOKEN_URL+param, String.class);
					JSONObject jsonObject = JSONObject.parseObject(result);
					if(busWeApiInfo==null) {
						busWeApiInfo = new BusWeApiInfo();
						busWeApiInfo.setUpdateDate(new Date());
						busWeApiInfo.setCreateDate(new Date());
						busWeApiInfo.setBusUserId(busUserId);
						busWeApiInfo.setBusMnprogram(busMnprogram);
					}
					busWeApiInfo.setUpdateDate(new Date());
					if(jsonObject.containsKey("access_token")) {
						busWeApiInfo.setAccess_token(jsonObject.getString("access_token"));
						busWeApiInfo.setTakeTime(new Date());
						busWeApiInfo.setErrcode(null);
						busWeApiInfo.setExpires_in(jsonObject.getInteger("expires_in"));
					}else {
						busWeApiInfo.setErrcode(jsonObject.getInteger("errcode"));
					}
					dao.save(busWeApiInfo);
				}
		return busWeApiInfo;
	}
	
	private BusWeApiInfo refreshAccessToken(Integer busUserId) {
		BusMnprogram busMnprogram = busMnprogramDao.findFirstByBusUserId(busUserId);
		if(busMnprogram==null || StringUtils.isBlank(busMnprogram.getAppID()) || StringUtils.isBlank(busMnprogram.getAppSecret())) {
			throw new ServiceException("请先配置小程序信息！");
		}
		BusWeApiInfo busWeApiInfo = dao.findByBusMnprogramAndBusUserId(busMnprogram, busUserId);
		return refreshAccessToken(busUserId, busMnprogram, busWeApiInfo);
	}
	
	
	@Transactional(readOnly = true)
	public Page<VO> stat(Paging paging){
		Pageable pageable = PageRequest.of(paging.getPage(), paging.getSize(), Sort.by(Order.desc("id")));
		Page<BusMnprogramStat> page = busMnprogramStatDao.findAll(pageable);
		return page.map(new busMnprogramStatRes());
	}
	
	
	//appid=APPID&secret=SECRET&js_code=JSCODE&
	public VO authCode2Session(Integer busUserId , String js_code , Integer busWechatUserId) {
		if(busUserId==null || js_code==null) {
			throw new ServiceException("参数不正确！");
		}
		BusMnprogram busMnprogram = busMnprogramDao.findFirstByBusUserId(busUserId);
		if(busMnprogram==null || StringUtils.isBlank(busMnprogram.getAppID()) || StringUtils.isBlank(busMnprogram.getAppSecret())) {
			throw new ServiceException("请先配置小程序信息！");
		}
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
		if(busWechatUser==null) {
			busWechatUser = new BusWechatUser();
			busWechatUser.setCreateDate(new Date());
			busWechatUser.setBusUserId(busUserId);
			busWechatUser.setOpenId(openid);
			busWechatUser.setUnionId(unionid);
			if(busWechatUserId!=null) {
				BusWechatUser parent = busWechatUserDao.findByIdAndBusUserId(busWechatUserId, busUserId);
				if(parent!=null) {
					busWechatUser.setParent(parent);
				}
			}
		}
		busWechatUser.setUpdateDate(new Date());
		busWechatUser.setSessionKey(session_key);
		busWechatUser.setNickname(BusWechatUser.randomNickname());
		busWechatUserDao.save(busWechatUser);
		return new BusWechatUserRes().apply(busWechatUser);
	}
	
	/**
	 * 从微信服务拉去统计信息
	 * @param busUserId
	 */
	public void pullPrevDailyStat(Integer busUserId) {
		if(busUserId==null) {
			throw new ServiceException("参数不正确！");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
		String statDate = sdf.format(calendar.getTime());
		BusMnprogramStat busMnprogramStat = busMnprogramStatDao.findByStatDateAndBusUserId(statDate, busUserId);
		if(busMnprogramStat==null) {			
			BusWeApiInfo busWeApiInfo = refreshAccessToken(busUserId);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("begin_date", statDate);
			jsonObject.put("end_date", statDate);
			String request = jsonObject.toString();
			String result = restTemplate.postForObject(WECHAT_API_DAILYSUMMARY_URL+busWeApiInfo.getAccess_token(),request, String.class);
			JSONObject summary = JSONObject.parseObject(result);
			result = restTemplate.postForObject(WECHAT_API_DAILYRETAIN_URL+busWeApiInfo.getAccess_token(),request, String.class);
			JSONObject retain = JSONObject.parseObject(result);
			busMnprogramStat = new BusMnprogramStat();
			busMnprogramStat.setUpdateDate(new Date());
			busMnprogramStat.setCreateDate(new Date());
			busMnprogramStat.setBusUserId(busUserId);
			busMnprogramStat.setMnprogram(busWeApiInfo.getBusMnprogram());
			busMnprogramStat.setStatDate(statDate);
			JSONObject summaryObject = summary.getJSONArray("list").getJSONObject(0);
			busMnprogramStat.setSharePv(summaryObject.getInteger("share_pv"));
			busMnprogramStat.setShareUv(summaryObject.getInteger("share_uv"));
			busMnprogramStat.setVisitTotal(summaryObject.getInteger("visit_total"));
			JSONObject visit_uv_new = retain.getJSONArray("visit_uv_new").getJSONObject(0);
			busMnprogramStat.setVisitUvNew(visit_uv_new.getInteger("value"));
			JSONObject visit_uv = retain.getJSONArray("visit_uv").getJSONObject(0);
			busMnprogramStat.setVisitUv(visit_uv.getInteger("value"));
			busMnprogramStatDao.save(busMnprogramStat);
		}
	}
	
	/**
	 * 获取商家版小程序二维码
	 * @param busUserId
	 */
	public VO getWxaCode() {
		Integer busUserId = CurrentUser.getBusUserId();
		BusMnprogram busMnprogram = busMnprogramDao.findFirstByBusUserId(busUserId);
		if(busMnprogram==null || StringUtils.isBlank(busMnprogram.getAppID()) || StringUtils.isBlank(busMnprogram.getAppSecret())) {
			throw new ServiceException("请先配置微信小程序信息！");
		}
		BusWeApiInfo busWeApiInfo = dao.findByBusMnprogramAndBusUserId(busMnprogram, busUserId);
		if(busWeApiInfo!=null && StringUtils.isNotBlank(busWeApiInfo.getMnqrcode())) {
			return new BusWeApiInfoRes().apply(busWeApiInfo);
		}
		busWeApiInfo = refreshAccessToken(busUserId , busMnprogram , busWeApiInfo);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("scene", "busUserId=" + busUserId);
		jsonObject.put("width", qrcodeWidth);
		String request = jsonObject.toString();
		FileOutputStream fos = null;
		try {
			//收成小程序首页二维码
			ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(WECHAT_API_WXACODEUNLIMIT_URL+busWeApiInfo.getAccess_token(), request , byte[].class);
			if(responseEntity.getHeaders().getContentType().getType().startsWith("image")) {				
				byte[] qrcodeBytes = responseEntity.getBody();
				String dir = "/" + busUserId + "/image";
				String filename = BaseEntity.generateRandom() + ".png";
				fos = new FileOutputStream(new File(rootDir + dir + "/" + filename));
				fos.write(qrcodeBytes);
				busWeApiInfo.setMnqrcode(dir + "/" + filename);
				//生成店铺二维码素材
				String merfilename = filename.substring(0,filename.lastIndexOf("."))+"_m.png";
				_createQRCodeMaterial(qrcodeBytes, "png",rootDir + dir, merfilename , qrcodebgPath);
				busWeApiInfo.setMnqrcodeMater(dir + "/" + merfilename);
			}else {
				String result = new String(responseEntity.getBody());
				log.error(result);
				JSONObject error = JSONObject.parseObject(result);
				if(error.containsKey("errmsg")) {					
					throw new ServiceException(error.getString("errmsg"));
				}else {
					throw new ServiceException(result);
				}
			}
			
			//生成小程序付款页面的二维码
			JSONObject jsonObject1 = new JSONObject();
			jsonObject1.put("scene", "busUserId=" + busUserId);
			jsonObject1.put("page", paymentURL);
			jsonObject.put("width", qrcodeWidth);
			request = jsonObject1.toString();
			responseEntity = restTemplate.postForEntity(WECHAT_API_WXACODEUNLIMIT_URL+busWeApiInfo.getAccess_token(), request , byte[].class);
			if(responseEntity.getHeaders().getContentType().getType().startsWith("image")) {				
				byte[] qrcodeBytes = responseEntity.getBody();
				String dir = "/" + busUserId + "/image";
				String filename = BaseEntity.generateRandom() + ".png";
				fos = new FileOutputStream(new File(rootDir + dir + "/" + filename));
				fos.write(qrcodeBytes);
				busWeApiInfo.setPayqrcode(dir + "/" + filename);
				//生成店铺付款页面的二维码素材
				String merfilename = filename.substring(0,filename.lastIndexOf("."))+"_m.png";
				_createQRCodeMaterial(qrcodeBytes, "png",rootDir + dir, merfilename , paybgPath);
				busWeApiInfo.setPayqrcodeMater(dir + "/" + merfilename);
				busWeApiInfo.setUpdateDate(new Date());
				dao.save(busWeApiInfo);
			}else {
				String result = new String(responseEntity.getBody());
				log.error(result);
				JSONObject error = JSONObject.parseObject(result);
				if(error.containsKey("errmsg")) {					
					throw new ServiceException(error.getString("errmsg"));
				}else {
					throw new ServiceException(result);
				}
			}
			//调用微信小程序统计定时任务
			scheduledExecutorService.scheduleAtFixedRate(new WechatStatTask(busUserId, this), getNextDaily(), 24*60*60*1000, TimeUnit.MILLISECONDS);
			return new BusWeApiInfoRes().apply(busWeApiInfo);
		} catch (IOException e) {
			throw new ServiceException("生成商家小程序二维码异常！", e);
		}finally {
			if(fos!=null) {
				try {					
					fos.close();
				} catch (Exception e2) {
					throw new ServiceException("生成商家小程序二维码异常！", e2);
				}
			}
		}
	}
	
	private long getNextDaily() {
		Calendar calendar = Calendar.getInstance();
		long currentms = calendar.getTime().getTime();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)%4);
		return calendar.getTimeInMillis()-currentms;
	}
	

	
	private void _createQRCodeMaterial(byte[] qrcodeBytes , String formatName, String dir , String filename , String bgimagePath) throws IOException{
		BufferedImage bgBufferedImage = ImageIO.read(new File(rootDir + bgimagePath));
		int bgWidth = bgBufferedImage.getWidth();
		int bgheight = bgBufferedImage.getHeight();
		BufferedImage srcodeBufferedImage = ImageIO.read(new ByteArrayInputStream(qrcodeBytes));
		int yp = 700;
		Graphics2D graphics2d = bgBufferedImage.createGraphics();
		graphics2d.drawImage(srcodeBufferedImage, (bgWidth-qrcodeWidth)/2, (bgheight-yp)/2,null);
		graphics2d.dispose();
		ImageIO.write(bgBufferedImage, formatName, new File(dir + "/" + filename));
	}

//	public static void main(String[] args) throws Exception{
//		RestTemplate restTemplate = new RestTemplate();
//		String param = "appid="+"wx7170f5edaccd6421" + "&secret=" + "96abd56f6da45ceda14b91ce1b94ed3e";
//		String result = restTemplate.getForObject(WECHAT_API_ACCESS_TOKEN_URL+param, String.class);
//		JSONObject jsonObject = JSONObject.parseObject(result); 
//		System.out.println(result);
//		String access_token = jsonObject.getString("access_token");
////		String access_token = "28_VNHq5IvFSnokhfy6_cC4VS65-YZHEwyLfmCwWBPly2pzAO3NQQ7sjYVIy5RbxrQIeqI2KTBAuNJwCta3gFHosfemnels_W-0rbGzk4mt3VfPO0k9Z-8AKDEnjdKAIdSu-RVPDYXv2OWCmtUKEVObAEAARK";
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd");
//		String statDate = sdf.format(calendar.getTime());
//		String s = "access_token=" + access_token;
//		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//		params.add("begin_date", "20191230");
//		params.add("end_date", "20191230");
//		params.add("access_token", access_token);
//		JSONObject jsonObject1 = new JSONObject();
//		jsonObject1.put("begin_date", "20191229");
//		jsonObject1.put("end_date", "20191229");
//		String request = jsonObject1.toString();
//		byte[] bytess = restTemplate.postForObject(WECHAT_API_DAILYSUMMARY_URL+access_token,request, byte[].class);
//		System.out.println(new String(bytess));
//		
//		
//		//小程序码
//		JSONObject jsonObject2 = new JSONObject();
//		jsonObject2.put("scene", "id=1");
//		String request2 = jsonObject2.toString();
//		ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(WECHAT_API_WXACODEUNLIMIT_URL+access_token, request2 , byte[].class);
//		if(responseEntity.getHeaders().getContentType().getType().startsWith("image")) {			
//			FileOutputStream fos = new FileOutputStream(new File("/二维码1.png"));
//			fos.write(responseEntity.getBody());
//			fos.close();
//		}else {
//			System.out.println(new String(responseEntity.getBody()));
//		}
//	}
	
//	public static void main(String[] args) throws Exception{
//		BufferedImage bgBufferedImage = ImageIO.read(new File("/二维码背景图.png"));
//		int bgWidth = bgBufferedImage.getWidth();
//		int bgheight = bgBufferedImage.getHeight();
//		BufferedImage srcodeBufferedImage = ImageIO.read(new File("/二维码1.png"));
//		Graphics2D graphics2d = bgBufferedImage.createGraphics();
//		graphics2d.drawImage(srcodeBufferedImage, (bgWidth-srcodeNewWidth)/2, (bgheight-700)/2, null);
//		System.out.println(graphics2d.getBackground().getRed()+ "," + graphics2d.getColor().getGreen() + "," + graphics2d.getColor().getBlue());
//		graphics2d.dispose();
//		ImageIO.write(bgBufferedImage, "png", new File("/二维码合成1.png"));
//		System.out.println("完成！");
//	}
	
	public static class WechatStatTask implements Runnable{
		
		private Integer busUserId;
		
		private BusWeApiInfoService service;
		
		public WechatStatTask(Integer busUserId , BusWeApiInfoService service) {
			this.busUserId = busUserId;
			this.service = service;
		}

		@Override
		public void run() {
			try {				
				service.pullPrevDailyStat(busUserId);
			}catch (ServiceException e) {
				log.info("定时任务-拉取微信统计信息业务异常提示---" + e.getMessage());
			} 
			catch (Exception e) {
				log.error(e);
			}
		}
	
	} 
	
}
