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
		// 마스터 티어 순위 출력
		$('#masterTierList').click(function () {
			$("div").empty();
			
			$.get("${pageContext.request.contextPath}/LOLAPI/MasterTierList.do",
					function (data) {
						$("#MasterList").html(data);
					}
			);
		});
	});
	
	// 소환사 명 검색
	function getSummonerData() {
		$(function () {
			$("div").empty();
			
			var searchForm = $("form[name=searchForm]").serialize();
			
			$.ajax({
				type : 'get',
				url : '${pageContext.request.contextPath}/LOLAPI/SearchSummonerName.do',
				data : searchForm,
				dataType : '',
				error:function(xhr, status, error){
					alert(error);
				},
				success:function(json){
					$('#SearchSummoner').html(json);
				}
			});
		});
	}
		
	
</script>
</head>
<body>
	<h2>LOL API Practice</h2>
	<hr>
	<h3>현재 까지 테스트한 기능</h3>
	<ol>
		<li>
			아시아 지역 마스터 티어 1 ~ 10위 출력 <br>
			<input type="button" value="마스터 티어 출력" name="masterTierList" id="masterTierList">
		</li>
		<li>
			소환사 명 검색을 통한 소환사의 각종 정보 출력 <br>
			<form name="searchForm" id="searchForm">
				<input type="text" name="summonerName" id="summonerName">
				<input type="button" value="검색" name="searchSummonerName" 
					id="searchSummonerName" onclick="getSummonerData();">
			</form>
		</li>
	</ol>
	
	<br>
	<div id="MasterList"></div>
	<div id="SearchSummoner"></div>
	
</body>
</html>