<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="include/header.jsp" %>
</head>
<body>
<%@ include file="include/menu.jsp" %>
<!--sessionScope는 세션변수를 의미한다, 세션영역에 저장된변수  -->
<c:if test="${sessionScope.userid != null }">
<h2>
	${sessionScope.name}(${sessionScope.userid})
	님의 방문을 환영합니다.
</h2>
</c:if>
<P>  The time on the server is ${serverTime}. </P>

<!-- 배포디렉토리 확인 , application은  서버에서 통용되는 모슨 사용자가 공유하루있는 변수이다. -->
<!--개발디렉토리는 이클립스에 있는 왼쪽에보이는 프로젝트 익스플로어를 말하고 배포디렉토리는 톰캣이 개발디렉토리를 카피해서 새로운 디렉토리에 돌리고 잇는데 그것이 배포디렉토리이다.  -->
<%=application.getRealPath("/") %>  
</body> 
</html>
