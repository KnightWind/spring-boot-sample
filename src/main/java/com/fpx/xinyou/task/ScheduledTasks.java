package com.fpx.xinyou.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fpx.xinyou.model.ScansData;
import com.fpx.xinyou.service.DataProcessService;
import com.fpx.xinyou.util.ApplicationContextUtil;
import com.fpx.xinyou.util.XmlGenerator;

@Component
@Configurable
@EnableScheduling
public class ScheduledTasks{

//    @Scheduled(fixedRate = 1000 * 30)
//    public void reportCurrentTime(){
//        System.out.println ("Scheduling Tasks Examples: The time is now " + dateFormat ().format (new Date ()));
//    }

    //每1天执行一次
//    @Scheduled(cron = "0 52 10  * * * ")
//    public void reportCurrentByCron(){
//        System.out.println ("Scheduling Tasks Examples By Cron: The time is now " + dateFormat ().format (new Date ()));
//   
//        Calendar calendar=Calendar.getInstance();
//		calendar.setTime(new Date());
////		calendar.add(Calendar.DATE, 1);
//		calendar.set(Calendar.MILLISECOND, 0);
//		calendar.set(Calendar.HOUR_OF_DAY, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		Date endDate = calendar.getTime();
//		
//		calendar.add(Calendar.DATE, -1);
//		calendar.set(Calendar.MILLISECOND, 0);
//		calendar.set(Calendar.HOUR_OF_DAY, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		Date startDate = calendar.getTime();
//		
//		DataProcessService sevice = (DataProcessService) ApplicationContextUtil
//				.getBeanByClass(DataProcessService.class);
//		
//		List<ScansData> datas = sevice.queryScansDatas(startDate, endDate);
//		
//		String fileName = "AWB_BAG_"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"_"+new SimpleDateFormat("HHmmss").format(new Date())+".XML";
//		XmlGenerator.genXml(datas, fileName);
//    }

    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat ("HH:mm:ss");
    }
    
}
