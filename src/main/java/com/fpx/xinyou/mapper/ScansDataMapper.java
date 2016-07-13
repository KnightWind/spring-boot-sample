package com.fpx.xinyou.mapper;

import java.util.Map;

import com.fpx.xinyou.model.ScansData;
import com.fpx.xinyou.util.MyMapper;


/**
 * 扫面数据数据库操作接口
 * @author wangchaobo
 *
 */
public interface ScansDataMapper extends MyMapper<ScansData>{
	
//	int getBgCodeNum(String bgCode);
	
	
	int getBgCodeRdNum(String bgCode);
	
	
	
	int insertScansData(ScansData data);
	
	
	/**
	 * 根据bgCode查询bgid
	 * @param bgCode
	 * @return
	 */
//	Long getBgIdByCode(String bgCode);
	
	
	
	/**
	 * 查询bgId下面的bsid
	 * @param bgId
	 * @return
	 */
//	List<Long> getBsIds(long bgId);
	
	/**
	 * 根据bagCode查询袋子id
	 * @param bgCode
	 * @return
	 */
//	Long getSgBagIdByCode(String bgCode);
	
	
	/**
	 * 查询sg bs id集合
	 * @param bgId
	 * @return
	 */
//	List<Long> getSgBsIds(long bgId);
	
	
	
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
	
	
	
	
	/**
	 * 更新 tak_trackingbusinessattach
	 * @param param
	 */
	public void updateTrakBussinAttach(Map<String,Object> param);
	
	
	
	/**
	 * 插入跟踪号码表
	 * @param tbsId
	 * @param code
	 */
	public void insertTrackHawbCode(Map<String,Object> param);
	
	
	/**
	 * 查询TAK_TRACKINGBUSINESS中数据是否存在
	 * @param bsId
	 * @return
	 */
	public Long getTrakBussinBsId(String bsId);
	
	
	/**
	 * 查询跟踪号码表中是否已存在该关联记录
	 * @param param
	 * @return
	 */
	public int  getTrackHawbCodeCount(Map<String,Object> param);
	

}
