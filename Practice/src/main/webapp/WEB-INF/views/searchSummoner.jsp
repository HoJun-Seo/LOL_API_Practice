<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script type="text/javascript" src="/webjars/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">
	$(function () {
		$('#matchHistory').click(function () {
			console.log("${summonerDTO.puuid}");
		});
	});
</script>
</head>
<body>
	<hr>
	소환사 명 : ${summonerDTO.name} <br>
	프로필 : <img alt="" src="${icon }"> <br>
	최근 정보 갱신 날짜 : ${summonerDTO.revisionDate} <br>
	
	최근 전적 확인하기 : <input type="button" value="전적 검색" name="matchHistory" id="matchHistory">
</body>
</html>