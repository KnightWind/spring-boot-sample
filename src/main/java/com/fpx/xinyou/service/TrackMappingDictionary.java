package com.fpx.xinyou.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.util.StringUtils;

import com.fpx.xinyou.model.XtdTrackMappingItem;


/**
 * 
 * @author Administrator
 *
 */
public class TrackMappingDictionary {
	
	public static final String SG_TK_CODE = "AAA";
	
	List<XtdTrackMappingItem> datas;
	
	
	public boolean trackMappingExist(String cmId){
		if(StringUtils.isEmpty(cmId)) return false;
		if(datas == null || datas.isEmpty()) return false;
		for (Iterator iterator = datas.iterator(); iterator.hasNext();) {
			XtdTrackMappingItem xtdTrackMappingItem = (XtdTrackMappingItem) iterator.next();
			
			if(xtdTrackMappingItem.getTk_code().equals(SG_TK_CODE) && xtdTrackMappingItem.getCm_id().equals(cmId)) return true;
		}
    	return false;
	}


	public List<XtdTrackMappingItem> getDatas() {
		return datas;
	}


	public void setDatas(List<XtdTrackMappingItem> datas) {
		this.datas = datas;
	}
	
	
	
}
