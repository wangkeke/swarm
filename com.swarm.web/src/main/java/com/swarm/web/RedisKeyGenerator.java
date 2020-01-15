package com.swarm.web;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import com.swarm.base.vo.Paging;

@Component("redisKeyGenerator")
public class RedisKeyGenerator implements KeyGenerator {
	
	private SpelExpressionParser parser = new SpelExpressionParser();
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Value("${spring.cache.redis.key-prefix}")
	private String cacheRedisKeyprefix;
	
	@Override
	public Object generate(Object target, Method method, Object... params) {
		Cacheable cacheable = null;
		CacheEvict cacheEvict = null;
		for (Annotation annotation  : method.getAnnotations()) {
			if(annotation.annotationType() == Cacheable.class) {
				cacheable = method.getAnnotation(Cacheable.class);
				break;
			}
			if(annotation.annotationType() == CacheEvict.class) {
				cacheEvict = method.getAnnotation(CacheEvict.class);
				break;
			}
		}
		StringBuilder builder = new StringBuilder();
		if(cacheable!=null) {
			if(cacheable.cacheNames()!=null && cacheable.cacheNames().length>0) {				
				builder.append(cacheable.cacheNames()[0]);
			}else {
				builder.append(cacheable.value()[0]);
			}
			 for (Object p : params) {
				if(p == null) {
					builder.append(":null");
				}else
				if(p instanceof String) {
					builder.append(":" + (String)p);
				}else
				if(p instanceof Number) {
					builder.append(":" + p);
				}else
				if(p instanceof Paging || p instanceof Pageable) {
					builder.append(":" + p.toString());
				}else
				if(p instanceof Object[]) {
					builder.append(":" + Arrays.toString((Object[])p));
				}else {
					builder.append(":" + p.toString());
				}
			}
			return builder.toString();
		}
		if(cacheEvict!=null) {
			
			if(cacheEvict.cacheNames()!=null && cacheEvict.cacheNames().length>0) {				
				builder.append(cacheEvict.cacheNames()[0]);
			}else {
				builder.append(cacheEvict.value()[0]);
			}
			String key = builder.toString();
			for (int i = params.length-1; i >= 0; i--) {
				String pn = "#p"+i;
				if(key.indexOf(pn + ".")>-1) {
					String startsub = key.substring(key.indexOf(pn+".")+pn.length()+1);
					StringBuilder sb = new StringBuilder();
					for (int j = 0; j < startsub.length(); j++) {
						String c = String.valueOf(startsub.charAt(j));
						if(c.matches("\\w")) {
							sb.append(c);
						}else {
							break;
						}
					}
					Expression expression = parser.parseExpression(sb.toString());
					String value = expression.getValue(params[i], String.class);
					key = key.replace(pn+"."+sb.toString(), value);
				}else if(key.indexOf(pn)>-1) {
					String r = "null";
					if(params[i]!=null) {
						r = params[i].toString();
					}
					key = key.replace("#p"+i, r);
				}
			}
			//这里仅仅做简单实现该结果
			Set<String> keys = stringRedisTemplate.keys(cacheRedisKeyprefix + key+":*");
			stringRedisTemplate.delete(keys);
			return key;
		}
		return null;
	}
	
//	public static void main(String[] args) {
//		Object[] params = new Object[2];
//		params[0] = 1;
//		params[1] = new BusDictRes();
//		SpelExpressionParser parser = new SpelExpressionParser();
//		Expression expression = parser.parseExpression("'product:'+#p1.id");
//		String value = expression.getValue(params, String.class);
//		System.out.println(value);
//	}
	
}
