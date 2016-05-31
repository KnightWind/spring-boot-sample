package com.fpx.xinyou.constant;

public enum ReturnData {
	
	SUCCESS("S","处理成功"),
	ERROR_USER_NOT_EXIST("E01","用户不存在"),
	ERROR_SAVE_SESSION("E03","生成用户会话失败"),
	ERROR_AUTH_FAILED("E04","请登录"),
	ERROR_SYS_EXCEPTION("E09","系统异常"),
	ERROR_USER_PWD("E02","用户密码不正确");
	
	private String code;
	
	private String msg;
	
	ReturnData(String code,String msg){
		this.code = code;
		this.msg = msg;
	}

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
	
	
	public String toString(){
		
		return "{\"code\":\""+this.code+"\",\"msg\":\""+this.msg+"\"}";
	}
	
}
