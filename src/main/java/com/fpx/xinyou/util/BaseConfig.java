package com.fpx.xinyou.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @author wangchaobo
 *
 */
public class BaseConfig {
	private final  Logger logger = Logger.getLogger(BaseConfig.class);
	private Properties prop;
	
	private BaseConfig(){
		prop = new Properties();
		try {
			InputStream in = BaseConfig.class.getResourceAsStream("config.properties");
			if(in == null){
				in =  BaseConfig.class.getResourceAsStream("config/config.properties");
			}
			if(in == null){
				in =  BaseConfig.class.getResourceAsStream("/config/config.properties");
			}
			if(in == null){
				in =  BaseConfig.class.getResourceAsStream("*/config.properties");
			}
			if(in == null){
				in = this.getClass().getClassLoader().getSystemClassLoader().getResourceAsStream("config.properties");
			}
			if(in == null){
				in = this.getClass().getClassLoader().getSystemClassLoader().getResourceAsStream("config/config.properties");
			}
			prop.load(in);
		} catch (IOException e) {
			logger.error("load the config fail!");
			e.printStackTrace();
		}
	}
	
	private static class GetObject{
		
		public static BaseConfig config = new BaseConfig();
	}
	
	
	public static BaseConfig getInstance(){
		
		return GetObject.config;
	}
	
	public  String getString(String key,String defValue){
		if(prop.getProperty(key)!=null){
			return prop.getProperty(key);
		}else{
			return defValue;
		}
	}
	
	public  String getString(String key){
		return getString(key,"");
	}
	
	
	public int getInt(String key,int defValue){
		if(prop.getProperty(key)!=null){
			try{
				return  Integer.parseInt(prop.getProperty(key));
			}catch (Exception e) {
				// TODO: handle exception
				return defValue;
			}
		}else{
			return defValue;
		}
	}
	
	public int getInt(String key){
		return getInt(key,0);
	}
}

