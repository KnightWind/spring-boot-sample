package com.fpx.xinyou.oprtmapper;

import java.util.List;

import com.fpx.xinyou.model.ScansData;
import com.fpx.xinyou.util.MyOprtMapper;


/**
 * 扫面数据数据库操作接口
 * @author wangchaobo
 *
 */
public interface OprtDataMapper extends MyOprtMapper<ScansData>{
	
	int getBgCodeNum(String bgCode);
	
	
	/**
	 * 根据bgCode查询bgid
	 * @param bgCode
	 * @return
	 */
	Long getBgIdByCode(String bgCode);
	
	
	
	/**
	 * 查询bgId下面的bsid
	 * @param bgId
	 * @return
	 */
	List<Long> getBsIds(long bgId);
	
	/**
	 * 根据bagCode查询袋子id
	 * @param bgCode
	 * @return
	 */
	Long getSgBagIdByCode(String bgCode);
	
	
	/**
	 * 查询sg bs id集合
	 * @param bgId
	 * @return
	 */
	List<Long> getSgBsIds(long bgId);
	
	
	/**
	 * 混装袋id
	 * @param mixBgId
	 * @return
	 */
	List<Long> getSubBagIds(long mixBgId);
	
}
