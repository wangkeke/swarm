package com.swarm.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import javassist.expr.NewArray;

public class CommonTest {
	
	public static void main(String[] args) {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(0);
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("测试");
			}
		};
//		scheduledExecutorService.execute(runnable);
		scheduledExecutorService.scheduleAtFixedRate(runnable, 5, 10,TimeUnit.SECONDS);
	}
	
	@Test
	public void test() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)%4);
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(calendar.getTime()));
	}
	
}
