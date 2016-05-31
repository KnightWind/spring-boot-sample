package com.fpx.xinyou.task;

import java.util.Iterator;
import java.util.List;

import com.fpx.xinyou.constant.ScansDataStatus;
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

	public ProcessDataTask(List<ScansData> datas) {

		this.datas = datas;
	}
	
	public ProcessDataTask(List<ScansData> datas,User user) {
		this.datas = datas;
		this.user = user;
	}
	
	@Override
	public void run() {
		DataProcessService sevice = (DataProcessService) ApplicationContextUtil
				.getBeanByClass(DataProcessService.class);
		
		for (Iterator iterator = datas.iterator(); iterator.hasNext();) {
			ScansData ssd = (ScansData) iterator.next();
			
			//if the bgCode has been processed, skip it
			if(sevice.bgCodeExist(ssd.getBgCode())) continue;
			
			int bgId = sevice.getBgId(ssd.getBgCode());
			if(bgId > 0){
				ssd.setStatus(ScansDataStatus.VAILDATED);
				boolean flag = sevice.updateTrackInfo(bgId,ssd,user);
				if(flag){
					ssd.setStatus(ScansDataStatus.TRACKED);
				}
			}else{
				ssd.setStatus(ScansDataStatus.VAILDATE_FAILED);
			}
			sevice.saveScansData(ssd);
		}
	}

}
