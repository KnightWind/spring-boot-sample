package com.fpx.xinyou.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpx.xinyou.components.ScansDataSender;
import com.fpx.xinyou.constant.ReturnData;
import com.fpx.xinyou.model.MqMsgData;
import com.fpx.xinyou.model.ReturnInfo;
import com.fpx.xinyou.model.ScansData;
import com.fpx.xinyou.model.User;
import com.fpx.xinyou.oprtmapper.OprtDataMapper;
import com.fpx.xinyou.service.DataProcessService;
import com.fpx.xinyou.service.OprtService;
import com.fpx.xinyou.service.ScansDataService;
import com.fpx.xinyou.service.UserService;

@RestController
@RequestMapping("/xy")
public class XYDataReciverController {
	
	
//	@Resource
//	ScanDataMqHandler scanDataMqHandler;

	@Resource
	UserService userService;
	
	@Resource
	DataProcessService dataProcessService;
	
	
	@Resource
	OprtService oprtService;
	
	
	@Resource(name="scSender")
	ScansDataSender sender;
	
	
	@Resource
	OprtDataMapper oprtDataMapper;
	
	
	
	@Resource
	JmsOperations jmsTemplate;
	
	@Resource(name="caiNiaoTrackQueue")
	Destination caiNiaoTrackQueue;
	
	
	private static final Logger logger = Logger.getLogger(XYDataReciverController.class);
	//数据接收
	 @RequestMapping(value = "/scans")
	 @SuppressWarnings("all")
	 public Object scans(@RequestBody List<ScansData> datas,@RequestHeader("Authkey") String authkey) {

		 logger.info("recive request! will process the scansdata");
		 try {
		 		User currUser = userService.getUserBySid(authkey);
		 		//mq消息异步处理
		 		MqMsgData mmd = new MqMsgData(datas,currUser);
		 		boolean flag = sender.sendMsg(mmd);
		 		//如果传递消息到mq失败，开启直接处理线程
		 		if(!flag){
		 			ScansDataService.getInstance().process(datas,currUser);
		 		}
			} catch (Exception e) {
				e.printStackTrace();
			} 
	    	return new ReturnInfo(ReturnData.SUCCESS);
	 }
	 
	 
	 /**
	  * 用户登录
	  * @param param
	  * @return
	  */
	 @RequestMapping(value = "/login")
	 public Object login(@RequestBody Map<String,Object> param, HttpServletResponse response){
		 String username = String.valueOf(param.get("username"));
		 String password = String.valueOf(param.get("password"));
		 
//		 int i = oprtDataMapper.getBgCodeNum("B13639426");
//		 System.out.println(i);
//		 String address = BaseConfig.getInstance().getString("mq.address");
//		 System.out.println("will get config file content : "+BaseConfig.getInstance().getString("mq.address"));
		 User user = userService.getUserName(username);
		 if(user == null){
			 return new ReturnInfo(ReturnData.ERROR_USER_NOT_EXIST);
		 }else if(!password.equals(user.getPassword())){
			 return new ReturnInfo(ReturnData.ERROR_USER_PWD);
		 }
		 String sid = userService.genSessionInfo(user);
		 if(StringUtils.isEmpty(sid))  return new ReturnInfo(ReturnData.ERROR_SAVE_SESSION);
		 response.addHeader("AuthKey", sid);
		 
		 return new ReturnInfo(ReturnData.SUCCESS);
	 }
	 
	 
	 @RequestMapping(value = "/test")
	 public Object login() throws Exception{
		 List<Map<String,Object>> mqData = new  ArrayList<Map<String,Object>>();
		 
		 Map<String,Object> data = new HashMap<String, Object>();
         data.put("tkCode", "AAA");
         data.put("tpTrackLocation", "SG");
         data.put("tpTrackDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
         data.put("tpCreateDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
         data.put("tpTrackDescription", "新加坡");
         data.put("tsvCode", "ABC");
         data.put("tpServeHawbCode", "123465654");
         data.put("tpCustomerHawbCode", "XXXXX");
         mqData.add(data);
         
         ObjectMapper mapper = new ObjectMapper();
         final String strMsg = mapper.writeValueAsString(mqData);
         
		 jmsTemplate.send(caiNiaoTrackQueue,  new MessageCreator() {
	            @Override
	            public Message createMessage(Session session) throws JMSException {
	                Message textMessage = session.createTextMessage(strMsg);
	                return textMessage;
	            }
	     });
		 
		 return new ReturnInfo(ReturnData.SUCCESS);
	 }
	 
}
