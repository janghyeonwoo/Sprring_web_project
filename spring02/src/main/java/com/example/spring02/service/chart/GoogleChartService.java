package com.example.spring02.service.chart;

import org.json.simple.JSONObject; //pom.xml에 추가한 라이브러리

public interface GoogleChartService {
	public JSONObject getChartData();

}
