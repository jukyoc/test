package com.speed.management.quartz.util;

import org.springframework.web.context.WebApplicationContext;

public class SpringUtils {
	
	private WebApplicationContext context;
	
	private static SpringUtils instance;
	
	private SpringUtils(){}
	
	public synchronized static SpringUtils getInstancs(){
		if(instance == null);
			instance = new SpringUtils();
		return instance;
	}
	
	public static synchronized void setContext(WebApplicationContext c){
		instance.context = c;
		try {
			instance.notifyAll();
		} catch (Exception e) {
		}
		
	}
	public static synchronized WebApplicationContext getContext(){
		if(instance == null || instance.context == null){
			try {
				instance.wait();
			} catch (InterruptedException e) {
			}
		}
		return instance.context;
	}
	
}
