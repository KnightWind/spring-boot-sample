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
		
		for (Iterator iterator = datas.iterator(); iterator.hasNext();) {
			ScansData ssd = (ScansData) iterator.next();
			logger.info("the scansdata is "+ssd);
			//if the bgCode has been processed, skip it
			if(sevice.bgCodeExist(ssd.getBgCode())) continue;
			
			long bgId = sevice.getBgId(ssd.getBgCode());
			if(bgId > 0){
				ssd.setStatus(ScansDataStatus.VAILDATED);
				boolean flag = sevice.updateTrackInfo(ssd,user);
				if(flag){
					ssd.setStatus(ScansDataStatus.TRACKED);
				}
			}else{
				ssd.setStatus(ScansDataStatus.VAILDATE_FAILED);
			}
			if(ssd.getSendTime() == null) ssd.setSendTime(new Date());
			logger.info("will save scans data ");
			sevice.saveScansData(ssd);
		}
	}

}
