package com.fpx.xinyou.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpx.xinyou.constant.ReturnData;
import com.fpx.xinyou.constant.SysConstant;
import com.fpx.xinyou.model.ReturnInfo;
import com.fpx.xinyou.model.UserLoginRecord;
import com.fpx.xinyou.service.UserService;
import com.fpx.xinyou.util.ApplicationContextUtil;


/**
 * 处理请求权限
 * @author  
 *
 */
public class AuthenticationInterceptor implements HandlerInterceptor{
	
	
	@Autowired
	UserService service;
	
	
	 @Override  
	 public boolean preHandle(HttpServletRequest request,  
	            HttpServletResponse response, Object handler) throws Exception {
		 
		 	if(service == null) service = (UserService)ApplicationContextUtil.getBeanByClass(UserService.class);
		 	String sid = request.getHeader("AuthKey");
		 	UserLoginRecord ulr = service.getLoginRord(sid);
		 	if(ulr == null || (System.currentTimeMillis() - ulr.getLastActiveTime().getTime())
		 			> SysConstant.MAX_ACTIVE_TIME){
		 		
		 		response.setCharacterEncoding("UTF-8");
		 		response.setContentType("application/json");
				ObjectMapper mapper = new ObjectMapper();
				
				mapper.writeValue(response.getWriter(),new ReturnInfo(ReturnData.ERROR_AUTH_FAILED));
				response.getWriter().flush();
				response.getWriter().close();
				return false;  
		 	}
		 	service.updateActivTime(ulr);
		 	response.addHeader("AuthKey", sid);
		 	return true;
	  }

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		response.setContentType("application/json; charset=utf-8");
	}  
}
