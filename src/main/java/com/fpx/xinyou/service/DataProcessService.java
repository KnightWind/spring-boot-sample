package com.fpx.xinyou.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpx.xinyou.constant.SysConstant;
import com.fpx.xinyou.mapper.ScansDataMapper;
import com.fpx.xinyou.mapper.TrackAttachMapper;
import com.fpx.xinyou.mapper.TrackMapper;
import com.fpx.xinyou.model.ScansData;
import com.fpx.xinyou.model.TrackAttach;
import com.fpx.xinyou.model.TrackInfo;
import com.fpx.xinyou.model.User;

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
		Long id = oprtService.getBgIdByCode(bgCode);
		if(id == null || id == 0) id = oprtService.getSgBagIdByCode(bgCode); 
		return (id == null)?0:id;
	}
	
	
	public void saveBatch(List<ScansData> datas){
		
		scansDataMapper.insertList(datas);
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
		scansDataMapper.insertScansData(data);
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
	public boolean updateTrackInfo(ScansData data,User user){
		try{
//			List<Long> bsIds = new ArrayList<Long>();
//			List<Long> ids = scansDataMapper.getBsIds(bgId);
//			List<Long> sgIds = scansDataMapper.getSgBsIds(bgId);
//			if(ids != null)bsIds.addAll(ids);
//			if(sgIds != null)bsIds.addAll(bsIds);
			List<Long> bsIds = getBsIdsByBgCode(data.getBgCode());
			if(bsIds == null || bsIds.isEmpty()) return false;
			
			for (Iterator iterator = bsIds.iterator(); iterator.hasNext();) {
					Long bsId = (Long) iterator.next();
					
					Long trkId = scansDataMapper.getTrkId();
					//添加track信息
					TrackInfo tk = new TrackInfo();
					tk.setTrkId(trkId);
					tk.setTbsId(bsId);
					tk.setTkCode(SysConstant.XY_TK_CODE);
					tk.setMstCode("");
					tk.setTrkOccurdate(data.getScanTime());
					tk.setTrkCreateperson(user.getUsername());
					tk.setTrkCreatedate(new Date());
					trackMapper.insertSelective(tk);
					
					//添加trackAttch
					TrackAttach tka = new TrackAttach();
					tka.setTrkId(trkId);
					trackAttachMapper.insertSelective(tka);
					
					
					//更新TAK_TRACKINGBUSINESS
					Map<String,Object> tkBusiness = new HashMap<String,Object>();
					tkBusiness.put("bsId", String.valueOf(bsId));
					tkBusiness.put("ctCode", SysConstant.COUNTRY_CODE_SINGAPORE);
					tkBusiness.put("operationDate", new Date());
					tkBusiness.put("tkCode", SysConstant.XY_TK_CODE);
					tkBusiness.put("tkTime", data.getScanTime());
					tkBusiness.put("tkLocation", SysConstant.XY_TK_DEST);
					scansDataMapper.updateTrakBussin(tkBusiness);
			}
			//批量保存
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	
}
