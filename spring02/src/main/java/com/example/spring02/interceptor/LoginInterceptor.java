package com.example.spring02.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//세션객체생성
		HttpSession session = request.getSession();
		//세션 변수 검사
		if(session.getAttribute("userid") ==null) {
			//세션이 없으면 로그인 페이지로 이동
			response.sendRedirect(request.getContextPath()+"/member/login.do?message=nologin");
			return false;//계속진행하지않음
		
		}else {
			return true; //계속진행함
		}
		
	}
}
