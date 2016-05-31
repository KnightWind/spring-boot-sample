package com.fpx.xinyou.mapper;

import java.util.List;
import java.util.Map;

import com.fpx.xinyou.model.ScansData;
import com.fpx.xinyou.util.MyMapper;


/**
 * 扫面数据数据库操作接口
 * @author wangchaobo
 *
 */
public interface ScansDataMapper extends MyMapper<ScansData>{
	
	int getBgCodeNum(String bgCode);
	
	
	int getBgCodeRdNum(String bgCode);
	
	
	
	int insertScansData(ScansData data);
	
	/**
	 * 根据bgCode查询bgid
	 * @param bgCode
	 * @return
	 */
	Integer getBgIdByCode(String bgCode);
	
	
	
	/**
	 * 查询bgId下面的bsid
	 * @param bgId
	 * @return
	 */
	List<Long> getBsIds(int bgId);
	
	
	
	/**
	 * 插入一条轨迹信息
	 * @param param
	 * @return
	 */
	public int insertTrack(Map<String,Object> param);
	
	
	
	/**
	 * 插入trackattach 内容
	 * @param param
	 * @return
	 */
	public int insertTrackAttach(Map<String,Object> param);
	
	
	/**
	 * 获取轨迹表主键
	 * @return
	 */
	public long getTrkId();
	
	
	/**
	 * 更新trckBussiness表信息
	 * @param param
	 */
	public void updateTrakBussin(Map<String,Object> param);
}
