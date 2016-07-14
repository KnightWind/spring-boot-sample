package com.fpx.xinyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fpx.xinyou.interceptors.AuthenticationInterceptor;
import com.fpx.xinyou.util.ApplicationContextUtil;

/**
 */
@Controller
@EnableWebMvc
@SpringBootApplication
@ComponentScan("com.fpx")
public class Application extends WebMvcConfigurerAdapter {
	
	
    public static void main(String[] args) {
    	final ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
    	ApplicationContextUtil.regsiteContext(applicationContext);
    }
    
    /**
     * 添加拦截器
     */
    public void addInterceptors(InterceptorRegistry registry) {  
    	 registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/xy/scans");  
    }  
    
    
    
    @RequestMapping("/")
    String home() {
//    	jmsTemplate.send(caiNiaoTrackQueue, messageCreator);
        return "";
    }

}
