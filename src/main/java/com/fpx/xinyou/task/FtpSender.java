package com.fpx.xinyou.task;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.fpx.xinyou.controller.XYDataReciverController;
import com.fpx.xinyou.model.ScansData;
import com.fpx.xinyou.util.XmlGenerator;

public class FtpSender implements Runnable {
	
	List<ScansData> datas;
	
	
	private static final Logger logger = Logger.getLogger(XYDataReciverController.class);
	
	public FtpSender(List<ScansData> datas) {

		this.datas = datas;
	}
	
	
	@Override
	public void run() {
		String fileName = "";
		org.dom4j.Document doc = XmlGenerator.gen(datas, fileName);
		try {
			InputStream  in = new ByteArrayInputStream(doc.asXML().getBytes("utf-8")) ;
			
			
			FTPClient  ftp = new FTPClient();      
	        int reply;      
	        ftp.connect("",21);      
	        ftp.login("","");      
	        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);      
	        reply = ftp.getReplyCode();      
	        if (!FTPReply.isPositiveCompletion(reply)) {      
	            ftp.disconnect(); 
	            //说明ftp没有链接 ，ftp connecting failed
	            logger.error("ftp connecting failed,  check pls");    
	        }
	        //切换工作目录
	        ftp.changeWorkingDirectory("");    
	        
	        
	        ftp.storeFile(fileName, in);      
	        in.close();     
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("upload to sg server failed! pls check!");
		}
	}

}
