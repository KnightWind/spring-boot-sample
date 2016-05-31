package com.fpx.xinyou.components;


import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.fpx.xinyou.util.EventMessage;
import com.fpx.xinyou.util.EventProcesser;



public class MessageAdapterHandler {

	private static final Logger logger = Logger.getLogger(MessageAdapterHandler.class);
	
	private EventProcesser msgProcess;
	 
	public MessageAdapterHandler(EventProcesser msgProcess){
		this.msgProcess = msgProcess;
	}
	
	public void handleMessage(EventMessage eem) {
		logger.debug("Receive an EventMessage: [" + eem + "]");
		// 先要判断接收到的message是否是空的，在某些异常情况下，会产生空值
		if (eem == null) {
			logger.warn("Receive an null EventMessage, it may product some errors, and processing message is canceled.");
			return;
		}
		
		if (StringUtils.isEmpty(eem.getQueueName()) || StringUtils.isEmpty(eem.getExchangeName())) {
			logger.warn("The EventMessage's queueName and exchangeName is empty, this is not allowed, and processing message is canceled.");
			return;
		}
		 
		try {
			msgProcess.process(eem.getEventData());
		} catch (Exception e) {
			logger.error("Event content can not be Deserialized, check the provided CodecFactory.",e);
			return;
		}
	}

}
