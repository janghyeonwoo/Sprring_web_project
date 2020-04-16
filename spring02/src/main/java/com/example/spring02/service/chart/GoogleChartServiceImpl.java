package com.example.spring02.service.chart;

import java.util.List;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.example.spring02.model.shop.dto.CartDTO;
import com.example.spring02.service.shop.CartService;

@Service
public class GoogleChartServiceImpl implements GoogleChartService {
	
	@Inject
	CartService cartService;
	
	// {"변수명" :[{},{}.{}], "변수명":"값"}
	
	
	@Override
	public JSONObject getChartData() {  //디비로 부턴 받은값을 제이슨형식으로 만들어서 리턴한다
		List<CartDTO> items =cartService.cartMoney();  //db에서 arraylist를 받아온다
		
		//리턴할 json 객체
		JSONObject data =new JSONObject();
		//json의 컬럼 객체
		JSONObject col1 =new JSONObject();
		JSONObject col2 =new JSONObject();
		// json 배열 객체
		JSONArray title = new JSONArray();
		col1.put("label","상품명");
		col1.put("type","string");
		col2.put("label","금액");
		col2.put("type","number");
		//타이틀행 컬럼 추가
		title.add(col1);
		title.add(col2);
		// json객체에 타이틀행 추가
		data.put("cols", title);
		//{"cols":[{"lable":"상품명" , "type":"srting"},{"label":"금액","type":"number}]}
		
		JSONArray body = new JSONArray();	//rows
		for( CartDTO dto : items) {
			JSONObject name  = new JSONObject();
			name.put("v", dto.getProduct_name());//상품명
			JSONObject money  = new JSONObject();
			money.put("v", dto.getMoney());//금액
			JSONArray row = new JSONArray();
			row.add(name);
			row.add(money);
			JSONObject cell = new JSONObject();
			cell.put("c", row);
			body.add(cell); //레코드 1개 추가
		}
		data.put("rows",body);
		return data;
	}

}
