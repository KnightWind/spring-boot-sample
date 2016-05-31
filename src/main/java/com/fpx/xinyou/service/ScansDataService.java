package com.fpx.xinyou.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpx.xinyou.model.MqMsgData;
import com.fpx.xinyou.model.ScansData;
import com.fpx.xinyou.model.User;
import com.fpx.xinyou.task.ProcessDataTask;

public class ScansDataService {
	
	private static final ScansDataService instance = new ScansDataService();
	
	private ScansDataService(){
		super();
	}
	
	public static synchronized ScansDataService getInstance(){
		
		return instance;
	}
	
	private static final ExecutorService service=Executors.newCachedThreadPool();
	
	//开启处理线程
	public synchronized void process(List<ScansData> datas, User user){
		ProcessDataTask pdt = new ProcessDataTask(datas,user);
		service.execute(pdt);
	}
	
	public synchronized void process(String data){
		
		ObjectMapper mapper = new ObjectMapper();
		try {
//			Map<String, Object> msgMap = mapper.readValue(data, Map.class);
//			List<ScansData> datas = (List<ScansData>)msgMap.get("scdatas");
//			System.out.println(datas);
//			Map<String,Object> userMap = (Map)msgMap.get("currnetUser");
//			User user = new User();
//			if(userMap != null){
//				user.setId(userMap.get("id") == null?0:(Integer)userMap.get("id"));
//				user.setUsername(userMap.get("username") == null?"":String.valueOf(userMap.get("username")));
//				user.setNickName(userMap.get("nickName") == null?"":String.valueOf(userMap.get("nickName")));
//			}
			MqMsgData mmd = mapper.readValue(data, MqMsgData.class);
			ProcessDataTask pdt = new ProcessDataTask(mmd.getScdatas(),mmd.getCurrnetUser());
			service.execute(pdt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
