package com.fpx.xinyou.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpx.xinyou.constant.SysConstant;
import com.fpx.xinyou.mapper.ScansDataMapper;
import com.fpx.xinyou.mapper.TrackAttachMapper;
import com.fpx.xinyou.mapper.TrackMapper;
import com.fpx.xinyou.model.ScansData;
import com.fpx.xinyou.model.TrackAttach;
import com.fpx.xinyou.model.TrackInfo;
import com.fpx.xinyou.model.User;
import com.fpx.xinyou.oprtmapper.OprtDataMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * 处理接收的扫面数据
 * @author wangchaobo
 *
 */
@Service
public class TrackProcessService {
	
	
	@Resource
	OprtDataMapper oprtDataMapper;
	
	@Resource
	JmsOperations jmsTemplate;
	
	@Resource(name="caiNiaoTrackQueue")
	Destination caiNiaoTrackQueue;
	
	
	@Resource(name="stSnMapping")
	StSnMappingDictionary stSnMappingDictionary;
	
	
	@Resource(name="xtdTrackMapping")
	TrackMappingDictionary trackMappingDictionary;
	
	
	private static final Logger logger = Logger.getLogger(TrackProcessService.class);
	
	
	/**
	 * 发送轨迹信息到MQ
	 * @param bsIds
	 */
	public void sendTrackInfoToMq(List<Long> bsIds){
		
		try{
			 List<Map<String,Object>> mqData = new  ArrayList<Map<String,Object>>();
			 
			 for (Iterator<Long> iterator = bsIds.iterator(); iterator.hasNext();) {
				 Long bsId =  iterator.next();
				 String cmId = oprtDataMapper.getItemCmId(bsId);
				 if(! trackMappingDictionary.trackMappingExist(cmId)) continue;
				 String snId = oprtDataMapper.getSnIdByBsId(bsId);
				 Map<String,Object> hawbCode = oprtDataMapper.getCarogHawbCodeMapByBsId(bsId);
				 
				 Map<String,Object> data = new HashMap<String, Object>();
		         data.put("tag", "SINGAPORE PDA SCAN");
		         data.put("tkCode", SysConstant.XY_TK_CODE);
		         data.put("tpTrackLocation", SysConstant.XY_TK_DEST);
		         data.put("tpTrackDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		         data.put("tpCreateDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		         data.put("tpTrackDescription", SysConstant.XY_TK_CODE_NAME);
		         data.put("tsvCode", stSnMappingDictionary.getTsvCodeBySnId(snId));
		         
		         if(hawbCode != null && hawbCode.get("SHAWBCODE") != null){
                     data.put("tpServeHawbCode", hawbCode.get("SHAWBCODE"));
                 }else{
                	 logger.error(bsId + " tpServeHawbCode not found ");
                     data.put("tpServeHawbCode", "not found");
                 }
                 
                 if(hawbCode != null && hawbCode.get("CHAWBCODE") != null){
                     data.put("tpCustomerHawbCode", hawbCode.get("CHAWBCODE"));
                 }else{
                	 logger.error(bsId + " tpCustomerHawbCode not found ");
                     data.put("tpCustomerHawbCode", "not found");
                 }
		         mqData.add(data);
			 }
	         
			 
			 if(!mqData.isEmpty()){
		         ObjectMapper mapper = new ObjectMapper();
		         final String strMsg = mapper.writeValueAsString(mqData);
		         
		         logger.info("the track info will send to mq is: "+strMsg);
				 jmsTemplate.send(caiNiaoTrackQueue,  new MessageCreator() {
			            @Override
			            public Message createMessage(Session session) throws JMSException {
			                Message textMessage = session.createTextMessage(strMsg);
			                return textMessage;
			            }
			     });
			 }
		}catch(Exception e){
			e.printStackTrace();

			logger.error("sendTrackInfoToMq exception ! " + e.getMessage());
		}
	}
	
	
	
}
