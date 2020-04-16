<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include  file="../include/header.jsp" %>
<script src="${path}/include/js/common.js"></script>
<script src="${path}/ckeditor/ckeditor.js"></script>
<script>
$(function(){
	
	//댓글 쓰기 
	$("#btnReply").click(function(){
		reply();	
	});
	//목록 버튼 클릭
	$("#btnList").click(function(){
		location.href="${path}/board/list.do";	
	});
	//댓글 목록 출력 
	listReply("1");  //responseRext 방식 
	
	//드래그 기본효과 막음
	$(".fileDrop").on("dragenter dragover" ,function(e){
		e.preventDefault();
	});
	$(".fileDrop").on("drop",function(e){
		e.preventDefault();
		//드롭한 파일을 폼데이터에 추가함
		var files =e.originalEvent.dataTransfer.files;
		var file = files[0];
		var formData = new FormData();
		//폼데이터 추가 
		formData.append("file",file);
		//processData : false - header가 아닌 body 로 전송 
		$.ajax({
			url:"${path}/upload/uploadAjax",
			data : formData,
			dataType: "text",
			processData : false,
			contentType:false,
			type: "post",
			success :function(data){ //콜백함수
				var fileInfo =getFileInfo(data); //첨부파일 정보
				var html = "<a href='"+fileInfo.getLink+"'>"+fileInfo.fileName+"</a><br>"; //첨부파일 링크
				html+="<input type='hidden' class='file' value='"+fileInfo.fullName+"'>"; //hidden 태그 추가
				$("#uploadedList").append(html); // div에 추가 
				
			}
		});
	});
	
	
	$("#btnUpdate").click(function(){
		var str="";
		$("#uploadedList .file").each(function(i){
			str+="<input type='hidden' name='files["+i+"]' value='"+$(this).val()+"'>";
		});
		//폼에 hidden 태그들을 추가 
		$("#form1").append(str);
		
		document.form1.action="${path}/board/update.do";
		document.form1.submit();
		
	});
	listAttach();
	//첨부 파일 삭제
	$("#uploadedList").on("click",".file_del",function(e){ //id가 uploadedList에서 클래스가 file_del인 태그를 클릭하면 
		var that =$(this);  //this 클릭한 태그 
		$.ajax({
			type:"post",
			url:"${path}/upload/deleteFile",
			data:{fileName:$(this).attr("data-src")}, //data-src에는  파일이름이적혀있다. 
			dataType:"text",
			success: function(result){
				if(result=="deleted"){
					that.parent("div").remove();   //that은 클릭한 태그이다. 이 태그의 부모 div를 삭제한다
				}
			}
		});
	});
	$("#btnDelete").click(function(){
		if(confirm("삭제하시겠습니까?")){
			document.form1.action="${path}/board/delete.do";
			document.form1.submit();
		}
	});
	
	
});


function listAttach(){
	$.ajax({
		type: "post",
		url: "${path}/board/getAttach/${dto.bno}",
		success : function(list){
			$(list).each(function(){
				
					var fileInfo=getFileInfo(this);
					var html ="<div><a href='"+fileInfo.getLink+"'>"+fileInfo.fileName+"</a>&nbsp;&nbsp;";
					html+="<a href='#' class='file_del' data-src='"+ this+"'>[삭제]</a></div>";
					$("#uploadedList").append(html);
				
			});
		}
	});
}

function reply(){
	
	var replytext=$("#replytext").val();  //댓글내용
	var bno="${dto.bno}";  //게시물번호
	/* var param = "replytext="+replytext+"&bno="+bno */
	var param={ "replytext": replytext , "bno":bno};
	console.log(replytext);
	console.log(bno);
	console.log(param);
	 $.ajax({
		type:"post",
		url:"${path}/reply/insert.do",
		data: param,
		success:function(){
			alert("댓글이 등록되었습니다.");
			listReply("1");
			
		}
	});  
}
function listReply(num){
	$.ajax({
		type:"post",
		url : "${path}/reply/list.do?bno=${dto.bno}&curPage="+num,
		success:function(result){
			console.log(result);
			$("#listReply").html(result);
		}				

	});
}
</script>


<style>
.fileDrop{
 	width:600px;
 	height:100px;
 	board:1px dotted grey;
 	background-color :grey;
}
</style>
</head>
<body>
<%@ include  file="../include/menu.jsp" %>
<h2>게시물보기</h2>
<form id="form1" name="form1" method="post">
	<div>작성일자 : <fmt:formatDate value="${dto.regdate}" pattern="yyyy-MM-dd a HH:mm:ss "/></div>
	<div>조회수 : ${dto.viewcnt}</div>
	<div>이름 : ${dto.name}</div>
	<div>제목: <input name ="title" value="${dto.title}"></div>
	<div style="width:80%;">내용 : <textarea rows="3" cols="80" name="content" id="content">${dto.content }</textarea></div>
	<script>
	CKEDITOR.replace("content",{
		filebrowserUploadUrl:"${path}/imageUpload.do", 
		height:"500px"
	});
	</script>
	<div id="uploadedList"></div>
	<div class="fileDrop"></div>
	<div>
		<input type="hidden" name="bno" value="${dto.bno}">
		<c:if test="${sessionScope.userid == dto.writer}">
			<button type="button" id="btnUpdate">수정</button>
			<button type="button" id="btnDelete">삭제</button>
			
		</c:if>
		<button type="button" id="btnList">목록</button>
	</div>
</form>
<!-- 댓글쓰기  -->
<div style="width:700px; text-align:center">
<c:if test="${sessionScope.userid != null }">
	<textarea rows="5" cols="80" id="replytext" placeholder="댓글을 작성하세요"></textarea>
	<br>
	<button type="button" id ="btnReply">댓글쓰기</button>
	
</c:if>
</div>
<div id="listReply"></div>

</body>
</html>