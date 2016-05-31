package com.fpx.xinyou.model;

import java.io.Serializable;
import java.util.List;

public class MqMsgData implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 3256793336914375131L;

	MqMsgData(){
		
	}
	
	public MqMsgData(List<ScansData> datas,User user){
		this.scdatas = datas;
		this.currnetUser = user;
	}
	
	private List<ScansData> scdatas;
	
	
	private User currnetUser;


	public List<ScansData> getScdatas() {
		return scdatas;
	}


	public void setScdatas(List<ScansData> scdatas) {
		this.scdatas = scdatas;
	}


	public User getCurrnetUser() {
		return currnetUser;
	}


	public void setCurrnetUser(User currnetUser) {
		this.currnetUser = currnetUser;
	}
	
	
	
}
