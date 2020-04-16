package com.example.spring02.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
//@Controller @Service @Repository
@Component //스프링에서 관리하는 bean    -> 콘트롤러면 콘트롤러 서비스면 서비스 를 붙였지만 이건 aop여서 전체를 통칭하는 componet를 쓴것이다
@Aspect  // 스르링에서 관리하는 aop bean  , 횡단관심(공통업무)를 지원하는 클래스

public class LogAdvice {
	
	private static final Logger logger =  LoggerFactory.getLogger(LogAdvice.class);
	// Aspect : 공통코드 
	// pointcut : 적용시킬 대상 메소드 
	// joinpoint : method가 호출되는 시점
	// advice : 핵심코드? 이건 확인해봐야함
	// Before(전처리) ,After(후처리) ,Around(전후처리) 
	// 컨트롤러 , 서비스 , dao 의 모든 method 실행 전후에 logPrint method 가 호출됨
	// execution(리턴 자료형  class.method(매개변수))
	
	//밑에 뜻은 컨트롤러의 모든 하위(..) 패지키에서  Controller로 끝나는 클래스 중 모든메소드를 말하는것이다. 즉 그중 어느 한 메소드에서 호출하면 밑에 logPrint가 호출이된다   
//	 @Around("execution(* com.example.spring02.controller..*Controller.*(..))"
//		+ " or execution(* com.example.spring02.service..*Impl.*(..))"
//	  + " or execution(* com.example.spring02.model..dao.*Impl.*(..))")
		public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable{
		 //요청 전에 처리할 코드 
		 long start =System.currentTimeMillis();  //이 메소드를 호출한시간
		 //메인코드 (핵심업무, 종단관심)
		 Object result = joinPoint.proceed();  // proceed를 호출한 기준으로  위는  호출전, 이 뒤로는 호출후 around이니깐 양쪽이다
		 //메인코드가 끝난 후에 처리할 코드
		 //핵심업무를 실행한 클래스와 method의 정보확인
		 String type= joinPoint.getSignature().getDeclaringTypeName();  //호출한 클래스의 이름
		 String name ="";
		 if(type.indexOf("Controller")>-1) {
			 name = "Controller \t: ";
		}else if(type.indexOf("Service")>-1) {
			name = "ServiceImpl \t:";
		}else if(type.indexOf("DAO")>-1) {
			name = "DAOImpl \t:";
		}
		 //method name
		 logger.info(name+type+"."+joinPoint.getSignature().getName()+"()");  //호출한 메소드의 이름 
		 //매개변수
		 logger.info(Arrays.toString(joinPoint.getArgs()));  //매개변수이름
		 //핵심로직으로 이동
		 long end =System.currentTimeMillis();
		 long time = end-start;
		logger.info("실행시간  : "+time);
		return result;
	 }
		



}
