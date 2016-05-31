package com.fpx.xinyou.util;

import java.io.Serializable;
import java.util.Arrays;

/**
 * User: poplar
 * 在App和RabbitMQ之间转送的消息
 */
public class EventMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -829810223104536289L;

	private String queueName;
	
	private String exchangeName;
	
	private byte[] eventData;

	public EventMessage(String queueName, String exchangeName, byte[] eventData) {
		this.queueName = queueName;
		this.exchangeName = exchangeName;
		this.eventData = eventData;
	}

	public EventMessage() {
	}	

	public String getQueueName() {
		return queueName;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public byte[] getEventData() {
		return eventData;
	}

	@Override
	public String toString() {
		return "EopEventMessage [queueName=" + queueName + ", exchangeName="
				+ exchangeName + ", eventData=" + Arrays.toString(eventData)
				+ "]";
	}
}
