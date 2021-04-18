<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<hr>
	<c:forEach var= "masterList" items="${masterTierList}" begin="0" end="9">
		<p>이름 : <c:out value="${masterList.name}"></c:out></p>
		<p>순위 : <c:out value="${masterList.rank}"></c:out></p>
		<p>lp : <c:out value="${masterList.lp}"></c:out></p>
		<hr>
	</c:forEach>
</body>
</html>