package com.fpx.xinyou.components;

import java.util.List;

import com.fpx.xinyou.model.ScansData;
import com.fpx.xinyou.util.CodecFactory;
import com.fpx.xinyou.util.EventProcesser;

public class ScansDataMQProcess implements EventProcesser {
	
	private CodecFactory codec;
	
	public ScansDataMQProcess(CodecFactory codec){
		this.codec = codec;
	}
	
	@Override
	public void process(byte[] msgbody) {
		try {
			Object data = codec.deSerialize(msgbody);
			if(data instanceof List){
				List<ScansData> scds = (List<ScansData>)data;
				System.out.println(scds);
			}else{
				System.out.println("=======the msg is======"+String.valueOf(data));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
