package com.example.spring02.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


//aop는 메소드에 한정되어있다면 interceptors  클래스에 한정이라고 봐도된다 특정 url을 입력전후에 실행시킬수가있다.
public class SampleInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger =LoggerFactory.getLogger(SampleInterceptor.class);
	
	

	//preHandle은 요청전에 경우

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("pre handler");  
		return true;  //ture하면 계속진행 false 하면 멈춘다
		
	}
	
	//postHandle은 요청 후에 경유(메인요청이 끝난후에 들리는곳)	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		logger.info("post handler");
	}
	
	

}
