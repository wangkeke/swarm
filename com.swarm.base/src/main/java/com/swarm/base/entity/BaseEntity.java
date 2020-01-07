package com.swarm.base.entity;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;


@MappedSuperclass
@Getter
@Setter
@Log4j2
public abstract class BaseEntity implements Serializable{
	
	private static String IPADDRESS = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4540195547274204684L;
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	/**
	 * 更新日期
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	/**
	 * 创建日期
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	/**
	 * encode Base64 string
	 * @return
	 */
	public static String encodeBase64String(String source) {
		return Base64Utils.encodeToString(source.getBytes());
	}
	
	/**
	 * decode Base64 string
	 * @param encodeBase64
	 * @return
	 */
	public static String decodeBase64String(String encodeBase64) {
		return new String(Base64Utils.decodeFromString(encodeBase64));
	}
	
	/**
	 * 
	 * @param source
	 * @return
	 */
	public static String generateMD5(String source) {
		return DigestUtils.md5DigestAsHex(source.getBytes());
	}
	
	/**
	 * 生成随机码
	 * @return
	 */
	public static String generateRandom() {
		String uuidStr = UUID.randomUUID().toString();
		uuidStr = uuidStr.replace("-", "");
		return Integer.toHexString(uuidStr.hashCode());
	}
	
	public static String getIPAddress() {
		if(IPADDRESS==null) {
			try {				
				IPADDRESS = java.net.InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				log.error("获取本机IP地址异常！",e);
				throw new RuntimeException(e);
			}
		}
		return IPADDRESS;
	}
	
}
