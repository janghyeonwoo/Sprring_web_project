<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- views/upload/uploadAjax.jsp -->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<style>
.fileDrop {
	width: 100%;
	height: 200px;
	border: 1px dotted blue;
}
small {
	margin-left:3px;
	font-weight: bold;
	color: gray;
}
</style>
<script>
$(function(){
	//드래그 기본 효과를 막음
	$(".fileDrop").on("dragenter dragover", function(event){
		event.preventDefault();
	});
	// event : jquery의 이벤트
	// originalEvent : 자바스크립트의 이벤트
	$(".fileDrop").on("drop",function(event){
		//drop이 될 때 기본 효과를 막음
		event.preventDefault();
		//첨부파일 배열
		var files=event.originalEvent.dataTransfer.files;  //드래그된 파일 정보
		var file=files[0]; //첫번째 첨부파일
		console.log(file);
		//ajax로 전달할 폼 객체
		var formData=new FormData(); 
		formData.append("file",file); //폼 객체에 파일 추가 , append("변수명",값)
		//processData : false => post방식
		//contentType: false => multipart/form-data로 처리됨
		//서버에 파일 업로드(백그라운드에서 실행됨)
		$.ajax({
			type: "post",
			url: "${path}/upload/uploadAjax",
			data: formData,
			dataType: "text",
			processData: false,
			contentType: false,
			success: function(data,status,req){
				console.log("data:"+data); //업로드된 파일 이름
				console.log("status:"+status); //성공,실패 여부
				console.log("req:"+req.status);//요청코드값
				var str="";
				if(checkImageType(data)){ //이미지 파일일 경우
str="<div><a href='${path}/upload/displayFile?fileName="
		+getImageLink(data)+"'>";
str+="<img src='${path}/upload/displayFile?fileName="
		+data+"'></a>"; 
				}else{ //이미지가 아닌 경우
					str="<div>";
str+="<a href='${path}/upload/displayFile?fileName="
		+data+"'>"+getOriginalName(data)+"</a>";
				}
				str+="<span data-src="+data+">[삭제]</span></div>";
				$(".uploadedList").append(str);
			}
		});
	}); //fileDrop 함수
	
	//태그.on("이벤트", "자손태그", 이벤트핸들러)
	//data : "fileName"+$(this).attr("data-src")
	//태그.attr("속성")
	//$('#userid').attr("type")
	//첨부파일 삭제 함수
	$(".uploadedList").on("click","span",function(event){
		
		var that=$(this); //현재 클릭한  span태그
		
		$.ajax({
			url: "${path}/upload/deleteFile",
			type: "post",
			data: {
				fileName: $(this).attr("data-src")
			},
			dataType: "text",
			success: function(result){
				if(result=="deleted"){
					that.parent("div").remove();  //클릭한 span태그가 속한 div제거
				}
			}
		});
	});
	
	function getOriginalName(fileName){
		if(checkImageType(fileName)){ //이미지 파일이면 skip
			return;
		}
		var idx=fileName.indexOf("_")+1; //uuid를 제외한 파일이름
		return fileName.substr(idx);
	}
	function getImageLink(fileName){
		if(!checkImageType(fileName)){//이미지 파일이 아니면 skip
			return;
		}
		//이미지 파일이면 
		var front=fileName.substr(0,12);//연월일 경로 추출
		var end=fileName.substr(14);// s_ 제거
		return front+end;
	}
	function checkImageType(fileName){
		//i : ignore case (대소문자 무관)
		var pattern=/jpg|png|jpeg/i; //정규표현식
		return fileName.match(pattern); //규칙에 맞으면 true
	}
});
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>Ajax File Upload</h2>
<!-- 파일을 업로드할 영역 -->
<div class="fileDrop"></div>
<!-- 업로드된 파일 목록을 출력할 영역 -->
<div class="uploadedList"></div>

</body>
</html>



















