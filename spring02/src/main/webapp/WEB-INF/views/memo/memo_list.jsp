<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script>
function memo_view(idx){
	location.href="${path}/memo/view/"+idx  //기존에는 view.do?idx=5 이런식으로 파리미터로 던졌다면 이거 글하나가 고유한 페이지가 되는것이다
											//받는쪽에서는 @pathVariable로 받으면된다
}
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>한줄 메모장</h2>
<form method="post" action="${path}/memo/insert.do">
	이름 : <input name="writer" size="10">
	메모 : <input name="memo" size="40">
	<input type="submit" value="확인">
</form>
<table border="1" style="width:500px;">
	<tr>
		<th>번호</th>
		<th>이름</th>
		<th>메모</th>
		<th>날짜</th>
	</tr>
<c:forEach var="row" items="${list}">
	<tr>
		<td>${row.idx}</td>
		<td>${row.writer}</td>
		<td><a href="#" onclick="memo_view('${row.idx}')">${row.memo}</a></td>
		<td><fmt:formatDate value="${row.post_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</c:forEach>	
</table>

</body>
</html>