package com.swarm.app;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.swarm.base.service.ActivityNode;
import com.swarm.base.vo.Paging;

@Component("redisKeyGenerator")
public class RedisKeyGenerator implements KeyGenerator {

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
			 builder.append(cacheable.cacheNames()[0]);
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
		}
		if(cacheEvict!=null) {
			builder.append(cacheable.cacheNames());
		}
		return builder.toString();
	}

	public static void main(String[] args) {
		Integer[] ids = new Integer[2];
		ids[0] = 1;
		ids[1] = 2;
		String s = "dsadasd";
		ActivityNode[] nodes = new ActivityNode[3];
		nodes[0] = ActivityNode.APPLY_REFUND;
		nodes[1] = ActivityNode.APPLY_WITHDRAWAL;
		nodes[2] = ActivityNode.CONFIRMED;
		System.out.println(Arrays.toString(nodes));
		System.out.println(s instanceof Object);
		System.out.println(Arrays.toString(ids));
		System.out.println(ActivityNode.APPLY_REFUND.toString());
//		System.out.println(ids.);
	}
	
}
