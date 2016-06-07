package com.fpx.xinyou;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpx.xinyou.constant.ReturnData;
import com.fpx.xinyou.model.ReturnInfo;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(HttpServletRequest req,HttpServletResponse response,Exception e) throws Exception {
		e.printStackTrace();
		response.setCharacterEncoding("UTF-8");
 		response.setContentType("application/json");
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.writeValue(response.getWriter(),new ReturnInfo(ReturnData.ERROR_SYS_EXCEPTION));
		response.getWriter().flush();
		response.getWriter().close();
    }

}
