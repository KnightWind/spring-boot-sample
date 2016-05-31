package com.fpx.xinyou.constant;

public enum ScansDataStatus {
	
	//待处理
	WAIT_PROCESS(0),
	//通过验证
	VAILDATED(1),
	//回写轨迹ok
	TRACKED(2),
	//验证失败
	VAILDATE_FAILED(3);
	
	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	ScansDataStatus(int state){
		this.code = state;
	}
	
}
