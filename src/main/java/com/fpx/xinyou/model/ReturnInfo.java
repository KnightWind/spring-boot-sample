package com.fpx.xinyou.model;

import com.fpx.xinyou.constant.ReturnData;

/**
 * 返回给客户端信息对象
 * @author Administrator
 *
 */
public class ReturnInfo {
	
	private String code;
	
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public ReturnInfo(ReturnData rd){
		this.code = rd.getCode();
		this.msg = rd.getMsg();
	}
	
	
	public ReturnInfo(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
}
