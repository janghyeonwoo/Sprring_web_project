package com.example.spring02.controller.chart;

import javax.inject.Inject;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.service.chart.GoogleChartService;


//보통 controller는 페이지로넘어간다 @RestController를 사용하는경우는 데이터자체를 받아서 jason형식으로 출력하고싶은경우  @RestController를 쓴다 
//아니면 @Responsebody를 쓴다
@RestController  //json을 리턴하는 method가 있는경우
@RequestMapping("/chart/*")
public class GoogleChartController {
		@Inject
		GoogleChartService googleChartService;
		@RequestMapping("chart1.do")
		public ModelAndView chart1() {
			return new ModelAndView("chart/chart01");
		}
		@RequestMapping("chart2.do")
		public ModelAndView chart2() {
			return new ModelAndView("chart/chart02");
		}
		
		//@ResponseBody  화면으로 넘어가는 것이 아닌 데이터를 리턴하는 경우
		@RequestMapping("cart_money_list.do")  
		public JSONObject cart_money_list() {  //화면으로 넘어가는 return이 아니라 데이터를 돌려주는 리턴이다. 그렇기때문에 @RestController 를사용 아니면 그냥 컨트롤러를 사용하는경우 아니면 @Responsebody를 붙여준다 
			return googleChartService.getChartData();
		}

}
