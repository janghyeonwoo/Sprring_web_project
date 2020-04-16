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
			//제이슨파일은 일반 파일로 만들면된다. 그렇게만든 제이슨파일을 구글서버에보내면 그걸 처리하고 차트를 그려서 보내주게된다. 
			url : "${path}/json/sampleData2.json",  //우리는 미리만들어진 json파일을 사용한다 , 이떄 매핑작업을 해줘야한다 안그러면 서블릿인지안다. servle.context 파일에서 리소스 매핑추가한다.
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
			title : "차트예제",
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