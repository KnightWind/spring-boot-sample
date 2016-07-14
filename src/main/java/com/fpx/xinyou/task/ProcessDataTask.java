package com.fpx.xinyou.task;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.fpx.xinyou.constant.ScansDataStatus;
import com.fpx.xinyou.controller.XYDataReciverController;
import com.fpx.xinyou.model.ScansData;
import com.fpx.xinyou.model.User;
import com.fpx.xinyou.service.DataProcessService;
import com.fpx.xinyou.service.TrackProcessService;
import com.fpx.xinyou.util.ApplicationContextUtil;


/**
 * 扫描的数据处理任务
 * @author Administrator
 *
 */
public class ProcessDataTask implements Runnable {

	List<ScansData> datas;
	
	User user;
	
	private static final Logger logger = Logger.getLogger(XYDataReciverController.class);
	
	public ProcessDataTask(List<ScansData> datas) {

		this.datas = datas;
	}
	
	public ProcessDataTask(List<ScansData> datas,User user) {
		this.datas = datas;
		this.user = user;
	}
	
	@Override
	public void run() {
		
		logger.info("will start the process task! ");
		
		DataProcessService sevice = (DataProcessService) ApplicationContextUtil
				.getBeanByClass(DataProcessService.class);
		
		
		TrackProcessService trackSevice = (TrackProcessService) ApplicationContextUtil
				.getBeanByClass(TrackProcessService.class);
		
		for (Iterator iterator = datas.iterator(); iterator.hasNext();) {
			ScansData ssd = (ScansData) iterator.next();
			logger.info("the scansdata is "+ssd);
			//if the bgCode has been processed, skip it
			try{
				if(sevice.bgCodeExist(ssd.getBgCode())) continue;
				
				//查询袋子下的单票信息
				long bgId = sevice.getBgId(ssd.getBgCode());
				List<Long> bsIds = sevice.getBsIdsByBgCode(ssd.getBgCode());
				
				if(bgId > 0){
					ssd.setStatus(ScansDataStatus.VAILDATED);
					boolean flag = sevice.updateTrackInfo(ssd,user);
					if(flag){
						ssd.setStatus(ScansDataStatus.TRACKED);
					}
				}else{
					logger.info("the bg_code "+ssd.getBgCode()+"get bgid: "+bgId+" mean the bg_code is not belong 4px");
					ssd.setStatus(ScansDataStatus.VAILDATE_FAILED);
				}
				if(ssd.getSendTime() == null) ssd.setSendTime(new Date());
				logger.info("will save scans data ");
				sevice.saveScansData(ssd);
				
				logger.info("will send track to mq  if it's needed ");
				trackSevice.sendTrackInfoToMq(bsIds);
			}catch(Exception e){
				e.printStackTrace();
				logger.error("process data "+ssd.getBgCode()+" failed!");
				
				continue;
				
			}
		}
	}

}
