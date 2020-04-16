package com.example.spring02.controller.chart;

import java.io.FileOutputStream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.spring02.service.chart.JFreeChartService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

@Controller
@RequestMapping("jchart/*") //공통적인 url mapping
public class JFreeChartController {
	
	@Inject
	JFreeChartService chartService;
	
	@RequestMapping("chart1.do")
	public void createChart1(HttpServletResponse response) {  //response 화면에 바로 출력하기 위해사용
		try {
			
			JFreeChart chart =chartService.createChart(); //차트를 만들고 받아온다
			ChartUtils.writeChartAsPNG(response.getOutputStream(),chart,900,550);  //뷰필요없이 바로 출력
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("chart2.do")
	public ModelAndView createChart2(
			HttpServletResponse response) {
		
		String message="";
		try {
			//차트 객체 리턴
			JFreeChart chart=chartService.createChart();
			//pdf 문서 객체
			Document document=new Document(); //itextpdf
			//pdf 생성 객체
			PdfWriter.getInstance(document, 
					new FileOutputStream("d:/test.pdf"));
			document.open();
			//차트를 itextpdf 라이브러리에서 지원하는 이미지 형식으로
			Image png=Image.getInstance(
					ChartUtils.encodeAsPNG(
							chart.createBufferedImage(500, 500)));
			//pdf 문서에 이미지를 추가
			document.add(png);
			//pdf 문서가 저장됨
			document.close();
			message="pdf 파일이 생성되었습니다.";
		} catch (Exception e) {
			message="pdf 파일 생성 실패...";
			e.printStackTrace();
		}
		return new ModelAndView("chart/jchart02","message",message);
	}

}
