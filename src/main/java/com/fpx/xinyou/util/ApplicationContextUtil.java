package com.fpx.xinyou.util;

import org.springframework.context.ApplicationContext;

/**
 * 操作容器的工具类
 * @author wangchaobo
 *
 */
public class ApplicationContextUtil {
	
	private static ApplicationContext mycontext;
	
	public static void regsiteContext(ApplicationContext context){
		mycontext = context;
	}
	
	/**
	 * 根据名称获取spring容器注册对象
	 * @param name
	 * @return
	 */
	public static Object getBeanByName(String name){
		if(mycontext == null) return null;
		return mycontext.getBean(name);
	}
	
	
	/**
	 * 根据类型获取
	 * @param c
	 * @return
	 */
	public static Object getBeanByClass(Class<?> c){
		if(mycontext == null) return null;
		return mycontext.getBean(c);
	}
}
