package com.fpx.xinyou.service;

import java.util.Map;

public class StSnMappingDictionary {
	
	/**
	 * 数据
	 */
	private Map<String,String> mappingDatas;
	
	
	
	public String getTsvCodeBySnId(String snId){
		if(mappingDatas.get(snId) != null) return mappingDatas.get(snId);
		return "unknow";
	}



	public Map<String, String> getMappingDatas() {
		return mappingDatas;
	}



	public void setMappingDatas(Map<String, String> mappingDatas) {
		this.mappingDatas = mappingDatas;
	}
	
	
	
}
