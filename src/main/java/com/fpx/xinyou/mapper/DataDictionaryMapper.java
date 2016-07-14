package com.fpx.xinyou.mapper;

import java.util.List;
import java.util.Map;

import com.fpx.xinyou.model.XtdTrackMappingItem;


/**
 * 初始化数据常量使用
 * @author Administrator
 *
 */
public interface DataDictionaryMapper {
	
	
	List<Map<String,Object>> getAllTsvMapping();
	
	
	
	
	List<XtdTrackMappingItem> getXtdTrackMappings();
}
