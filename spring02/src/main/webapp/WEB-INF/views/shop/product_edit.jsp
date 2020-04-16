<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script src="${path}/ckeditor/ckeditor.js"></script>  <!-- servlet.xml에 매밍추가함 -->
<script>
function product_update(){
	document.form1.action ="${path}/shop/product/update.do";
	document.form1.submit();
}
function product_delete(){
	if(confirm("삭제하시겠습니까?")){
		document.form1.action ="${path}/shop/product/delete.do";
		document.form1.submit();
	}

}
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>상품정보편집</h2>
<form id="form1" name="form1" method="post" enctype="multipart/form-data">
<table>
	<tr>
		<td>상품명</td>
		<td><input name ="product_name" value="${dto.product_name }"></td>
	</tr>
	<tr>
		<td>가격</td>
		<td><input name="price" type="number" value="${dto.price}"></td>
	</tr>
	<tr>
		<td>상품설명</td>
		<td><textarea rows="5" cols="60" name="description" id="description">${dto.description}</textarea></td>
	</tr>
	<script>
//id가 descriptrion인 태그에 ckeditor 적용 
CKEDITOR.replace("description",{
	filebrowserUploadUrl : "${path}/imageUpload.do"  //이미지 속성에 탭을 보면 파일업로드기능이 추가된다.
});
</script>
	
	<tr>
		<td>상품이미지</td>
		<td>
			<img src="${path}/images/${dto.picture_url}" width="300px" height="300px">
			<br>
			<input type="file" name="file1">
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<input type="hidden" name="product_id" value="${dto.product_id}">
			<input type="button" value="수정" onclick="product_update()">
			<input type="button" value="삭제" onclick="product_delete()">
			<input type="button" value="목록" onclick="location.href='${path}/shop/product/list.do}'">
		</td>
	</tr>
</table>
</form>

</body>
</html>