package com.fpx.xinyou.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fpx.xinyou.constant.SysConstant;
import com.fpx.xinyou.mapper.ScansDataMapper;
import com.fpx.xinyou.mapper.TrackAttachMapper;
import com.fpx.xinyou.mapper.TrackMapper;
import com.fpx.xinyou.model.ScansData;
import com.fpx.xinyou.model.TrackAttach;
import com.fpx.xinyou.model.TrackInfo;
import com.fpx.xinyou.model.User;

import tk.mybatis.mapper.entity.Example;

/**
 * 处理接收的扫面数据
 * @author wangchaobo
 *
 */
@Service
public class DataProcessService {
	
	
	@Autowired
	ScansDataMapper scansDataMapper;
	
	@Autowired
	TrackMapper trackMapper;
	
	
	@Autowired
	TrackAttachMapper trackAttachMapper;
	
	@Resource
	OprtService oprtService;
	
	
	private static final Logger logger = Logger.getLogger(DataProcessService.class);
	
	/**
	 * 根据袋号查询该袋号在数据库是否存在
	 * @param bgCode
	 * @return
	 */
	public boolean checkBgCode(String bgCode){
		int count = oprtService.getBgCodeNum(bgCode);
		return count > 0;
	}
	
	
	/**
	 * 根据bgCode查询tms_bag的bg_id
	 * @param bgCode
	 * @return
	 */
	public long getBgId(String bgCode){
		if(bgCode == null || "".equals(bgCode)) return 0l;
		bgCode = bgCode.trim();
		Long id = oprtService.getBgIdByCode(bgCode);
		logger.info("will search bgCode from tms_bg and sg_bag: "+bgCode);
		logger.info("getBgIdByCode: "+id);
		if(id == null || id == 0) {
			id = oprtService.getSgBagIdByCode(bgCode); 
			logger.info("getSgBagIdByCode: "+id);
		}
		return (id == null)?0:id;
	}
	
	
	public void saveBatch(List<ScansData> datas){
		
		scansDataMapper.insertList(datas);
	}
	
	
	public List<ScansData> queryScansDatas(Date startDate, Date endDate){
		
		Example example = new Example(ScansData.class);
		example.createCriteria().andLessThanOrEqualTo("sendTime", endDate)
		.andGreaterThanOrEqualTo("sendTime", startDate).andLessThan("state", 3);
		return scansDataMapper.selectByExample(example);
	}
	
	/**
	 * 
	 * @param bgCode
	 * @return
	 */
	public boolean bgCodeExist(String bgCode){
		return scansDataMapper.getBgCodeRdNum(bgCode) > 0;
	}
	
	/**
	 * 保存数据
	 * @param datas
	 */
	public void saveScansData(ScansData data){
		//scansDataMapper.insertSelective(data);
		try{
			if(data.getAwbCode().length() > 32){data.setAwbCode(data.getAwbCode().substring(0, 29)+"**");}
			if(data.getBgCode().length() > 32){data.setBgCode(data.getBgCode().substring(0, 29)+"**");}
			scansDataMapper.insertScansData(data);
		}catch(Exception e){
			e.printStackTrace();
			logger.info("save sansdata failed  exception is "+e.getMessage());
		}
	}
	
	
	public List<Long> getBsIdsByBgCode(String bgCode){
		Long id = oprtService.getBgIdByCode(bgCode);
		if(id != null && id > 0){
			return oprtService.getBsIds(id);
		}else{
			id = oprtService.getSgBagIdByCode(bgCode);
			if(id != null && id > 0) return oprtService.getSgBsIds(id);
		}
		return null;
	}
	
	/**
	 * 更新轨迹信息
	 * @param bgId
	 * @param data
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean updateTrackInfo(ScansData data,User user){
		try{
			List<Long> bsIds = getBsIdsByBgCode(data.getBgCode());
			if(bsIds == null || bsIds.isEmpty()) return false;
			logger.info("getBsIdsByBgCode :"+bsIds);
			for (Iterator iterator = bsIds.iterator(); iterator.hasNext();) {
					Long bsId = (Long) iterator.next();
					
					Long takBusId = scansDataMapper.getTrakBussinBsId(String.valueOf(bsId));
					//更新TAK_TRACKINGBUSINESS  先判断TAK_TRACKINGBUSINESS是否有该单数据
					
					if(takBusId != null && takBusId > 0){
						
						logger.info("will update  :"+bsId+" Track Info");
						
						Long trkId = scansDataMapper.getTrkId();
						//
						//添加track信息
						TrackInfo tk = new TrackInfo();
						tk.setTrkId(trkId);
						tk.setTbsId(takBusId);
						tk.setTkCode(SysConstant.XY_TK_CODE);
						tk.setMstCode("");
						tk.setTrkOccurdate(data.getScanTime());
						tk.setTrkCreateperson(user.getUsername());
						tk.setTrkCreatedate(new Date());
						trackMapper.insertSelective(tk);
						logger.info("add  :"+bsId+" track info success");
						
						//添加trackAttch
						TrackAttach tka = new TrackAttach();
						tka.setTrkId(trkId);
						trackAttachMapper.insertSelective(tka);
						logger.info("add  :"+bsId+" track attach info success");
						
						Map<String,Object> tkBusiness = new HashMap<String,Object>();
						tkBusiness.put("tbsId", takBusId);
						tkBusiness.put("ctCode", SysConstant.COUNTRY_CODE_SINGAPORE);
						tkBusiness.put("operationDate", new Date()); 
						tkBusiness.put("tkCode", SysConstant.XY_TK_CODE); 
						tkBusiness.put("tkTime", data.getScanTime());
						tkBusiness.put("tkLocation", SysConstant.XY_TK_DEST);
						scansDataMapper.updateTrakBussin(tkBusiness);
						logger.info("update   :"+bsId+" track business info success");
						
						
						Map<String,Object> battach = new HashMap<String,Object>();
						battach.put("tbsId", takBusId);
						battach.put("content", "Arrive at Airport");
						scansDataMapper.updateTrakBussinAttach(battach);
//						int count = scansDataMapper.getTrackHawbCodeCount(hawbCode);
//						logger.info("getTrackHawbCodeCount :"+count);
//						if(count < 1){
//							scansDataMapper.insertTrackHawbCode(hawbCode);
//							logger.info("add   :"+bsId+" track  business HawbCode info success");
//						} 
					}else{
						logger.fatal("bs_id "+bsId+" can't find in TAK_TRACKINGBUSINESS ");
					}
			}
			//批量保存
		}catch(Exception e){
			e.printStackTrace();
			logger.error("insert track info failed "+e.getLocalizedMessage());
			return false;
		}
		
		return true;
	}
	
	
	
}
