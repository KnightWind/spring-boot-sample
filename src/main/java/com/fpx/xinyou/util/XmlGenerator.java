package com.fpx.xinyou.util;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.fpx.xinyou.model.ScansData;

public class XmlGenerator {
	
	
//	public static List<ScansData> scDatas;
	
	public static Map<Integer,String> status = new HashMap<Integer,String>();
	
	static{
		status.put(1, "A");
		status.put(2, "S");
		status.put(0, "A");
	}
	
	public static Document gen(List<ScansData> scDatas,String fileName){
		
		Document document = DocumentHelper.createDocument();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			//创建根节点  
	        Element root = document.addElement("IMS");  
	        Element nFileName = root.addElement("FILE_NAME");
	        nFileName.addText(fileName);
	        
	        
	        Element nExtractDate = root.addElement("EXTRACT_DATETIME");
	        nExtractDate.addText(sdf.format(new Date()));
	       
	        Map<String,Element> cacheNodes = new HashMap<String,Element>(); 
	        for (Iterator<ScansData> iterator = scDatas.iterator(); iterator.hasNext();) {
				ScansData scansData =  iterator.next();
				Element ele = cacheNodes.get(scansData.getAwbCode());
				if(ele == null) {
					ele = root.addElement("AWB");
					ele.addAttribute("AWB_NO", scansData.getAwbCode());
				}
				Element eBag = ele.addElement("BAG");
				eBag.addAttribute("BAG_ID", scansData.getBgCode());
				eBag.addElement("OPT_TIME").addText(sdf.format(scansData.getScanTime()));
				eBag.addElement("BAG_STATUS").addText(String.valueOf(status.get(scansData.getState())));
				
			} 
	        
//	        OutputFormat format = OutputFormat.createPrettyPrint();// 创建文件输出的时候，自动缩进的格式                    
//	        format.setEncoding("UTF-8");//设置编码  
//	        XMLWriter writer = new XMLWriter(new FileWriter("output.xml"),format);  
//	        writer.write(document);  
//	        writer.close(); 
	        return document;
//	        InputStream  in = new ByteArrayInputStream(document.asXML().getBytes("utf-8")) ;
	        
		}catch(Exception e){
			
			e.printStackTrace();
		}
		return null;
		 
	}
	
	
	public static void genXml(List<ScansData> scDatas,String fileName){
		
		Document document = DocumentHelper.createDocument();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			//创建根节点  
			Element root = document.addElement("IMS");  
			Element nFileName = root.addElement("FILE_NAME");
			nFileName.addText(fileName);
			
			
			Element nExtractDate = root.addElement("EXTRACT_DATETIME");
			nExtractDate.addText(sdf.format(new Date()));
			
			Map<String,Element> cacheNodes = new HashMap<String,Element>(); 
			for (Iterator<ScansData> iterator = scDatas.iterator(); iterator.hasNext();) {
				ScansData scansData =  iterator.next();
				Element ele = cacheNodes.get(scansData.getAwbCode());
				if(ele == null) {
					ele = root.addElement("AWB");
					ele.addAttribute("AWB_NO", scansData.getAwbCode());
					cacheNodes.put(scansData.getAwbCode(), ele);
				}
				Element eBag = ele.addElement("BAG");
				eBag.addAttribute("BAG_ID", scansData.getBgCode());
				eBag.addElement("OPT_TIME").addText(sdf.format(scansData.getScanTime()));
				eBag.addElement("BAG_STATUS").addText(String.valueOf(status.get(scansData.getState())));
			} 
			
			
			String dirPath = BaseConfig.getInstance().getString("dir.output");
			File dir = new File(dirPath);
			if(!dir.exists() || !dir.isDirectory()){
				dir.mkdir();
			}
			File xmlFile = new File(dir.getPath()+File.separator+fileName);
	        OutputFormat format = OutputFormat.createPrettyPrint();// 创建文件输出的时候，自动缩进的格式                    
	        format.setEncoding("UTF-8");//设置编码  
	        XMLWriter writer = new XMLWriter(new FileWriter(xmlFile),format);  
	        writer.write(document);  
	        writer.close(); 
//	        InputStream  in = new ByteArrayInputStream(document.asXML().getBytes("utf-8")) ;
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
	}
}
