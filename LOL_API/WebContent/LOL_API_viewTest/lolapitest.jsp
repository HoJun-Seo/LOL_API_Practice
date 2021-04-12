<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="../jQuery/jquery-3.6.0.min.js"></script>
</head>
<body>
	<script type="text/javascript">
		$(function () {
			console.log('jQuery Ok..');
			
			var name = "hideonbush"
			var id=""
			var client = new XMLHttpRequest();
			client.open('GET', 'http://localhost:8100/LOL_API/LOL_API_viewTest/lolapitest.jsp', true);
			client.setRequestHeader('Access-Control-Allow-Origin', '*');
			client.send();
			$.ajax({
				type: "GET",
				url:"https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+name+"?api_key=RGAPI-6e0cd6eb-3501-4568-a362-7a74b6beff9b",
				dataType:'json',
				data :{},
				success:function(res){
					/* console.log(res.id)
					id = res.id.toString() */
					/* $.ajax({
						headers: {
						    'Access-Control-Allow-Credentials' : true,
						    'Access-Control-Allow-Origin':'*',
						    'Access-Control-Allow-Methods':'GET',
						    'Access-Control-Allow-Headers':'application/javascript',
						  },
						url:"https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/"+id+"?api_key=RGAPI-6e0cd6eb-3501-4568-a362-7a74b6beff9b",
						dataType:"jsonp",
						success:function(res2){
							console.log(res.name);
							for (var i = 0; i < res2.length; i++) {
								console.log(res2[i].tier, res2[i].rank)
							}
						},
						error:function(req,stat,err){
							console.log(err);
						}
					}); */
				},
				error:function(req,stat,err){
					console.log(err);
				}
			});
		});
	</script>
</body>
</html>