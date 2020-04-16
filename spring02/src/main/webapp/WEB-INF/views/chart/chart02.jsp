<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<!-- 구글차트 호출을 위한 js파일  -->
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script>

	
	//구글차트 라이브러리 로딩
	google.load('visualization','1',{
		'packages' : ['corechart']
	});
	//setOnLoadCallback는 로딩이 완료되면 drawChart 함수호출
	google.setOnLoadCallback(drawChart);
	function drawChart(){  //ajax는 비동기적 방식으로  화면이 실행중인  상태에서 백그라운드로 데이터가 왔다갔다하는것이다 
		var jsonData =$.ajax({  
			 
			url : "${path}/chart/cart_money_list.do",    //컨트롤러에서 제이슨을 동적으로 만든다
			dataType : "json",
			async : false
		}).responseText;  //제이슨을 텍스트로 읽어 들인다.
		console.log(jsonData);
		//데이터 테이블 생성
		var data
	= new google.visualization.DataTable(jsonData);  //jsonData 구글의데이터테이블형식으로 바꾼다
		//차트를 출력할 div
		//LineChart, ColumnChart, PieChart
		//var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
		var chart= new google.visualization.LineChart(document.getElementById('chart_div'));
		//var chart= new google.visualization.ColumnChart(document.getElementById('chart_div'));
	//	 차트객체.draw(데이터테이블,옵션)
	//	curveType : "function" => 곡선 처리
		chart.draw(data,{
			title : "장바구니 통계","C:/Users/hookcu/Desktop/spring소스/spring02/src/main/webapp/WEB-INF/views/chart/jchart02.jsp"
			curveType : "function",  
			width : 600,
			height : 440
		});
	}
</script>
</head>
<body>
<%@ include file="../include/admin_menu.jsp" %>
<!--차트 출력 영역  -->
	<div id ="chart_div"></div>
<!-- 차트 새로고침 버튼 -->
	<button id="btn" type="button" onlick="drawChart()">refresh</button>


</body>
</html>