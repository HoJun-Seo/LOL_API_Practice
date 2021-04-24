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
			var puuid = "${summonerDTO.puuid}";
			
			$.ajax({
				type : 'get',
				url : '${pageContext.request.contextPath}/LOLAPI/MatchHistory.do?puuid='+puuid,
				data : '',
				dataType : '',
				error:function(xhr, status, error){
					alert(error);
				},
				success:function(data){
					$('#MatchHistory').html(data)
				}
			});
			
		});
	});
</script>
</head>
<body>
	<hr>
	��ȯ�� �� : ${summonerDTO.name} <br>
	������ : <img alt="" src="${icon }"> <br>
	�ֱ� ���� ���� ��¥ : ${summonerDTO.revisionDate} <br>
	
	�ֱ� ���� Ȯ���ϱ� : <input type="button" value="���� �˻�" name="matchHistory" id="matchHistory">
	<hr>
	<div id="MatchHistory"></div>
</body>
</html>