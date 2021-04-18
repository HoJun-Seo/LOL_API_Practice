<%@page import="com.LOLAPI.Practice.DTO.MasterTierDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LOL API Practice</title>
<script type="text/javascript" src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	$(function () {
		$('#masterTierList').click(function () {
			console.log('jQuery');
			
			$.get("${pageContext.request.contextPath}/LOLAPI/MasterTierList.do",
					function (data) {
						$("#MasterList").html(data);
					}
			);
		});
	});
	
</script>
</head>
<body>
	<h2>LOL API Practice</h2>
	<hr>
	<h3>아시아 지역 마스터 티어 1 ~ 10위 출력</h3>
	<input type="button" value="출력" name="masterTierList" id="masterTierList">
	<br>
	<div id="MasterList">
	</div>
</body>
</html>