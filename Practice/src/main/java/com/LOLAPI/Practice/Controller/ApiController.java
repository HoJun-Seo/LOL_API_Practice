package com.LOLAPI.Practice.Controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.LOLAPI.Practice.DTO.MasterTierDTO;
import com.LOLAPI.Practice.DTO.MatchDataDTO;
import com.LOLAPI.Practice.DTO.SummonerDTO;

@Controller
public class ApiController {
	
	String developKey = "RGAPI-0d729973-77ce-492f-8454-df90934550d0";
	String apiURL = "";
	URL riotURL = null;
	HttpURLConnection urlConnection = null;
	BufferedReader br = null;
	
	@GetMapping("/")
	public String main() {
		return "main";
	}
	
	// 아시아 지역 마스터 티어 순위 1 ~ 10위 출력
	@GetMapping("/LOLAPI/MasterTierList.do")
	public String printMasterList(Model model) {
		try {
			apiURL = "https://asia.api.riotgames.com/lor/ranked/v1/leaderboards?api_key="+developKey;
			riotURL = new URL(apiURL);
			urlConnection = (HttpURLConnection)riotURL.openConnection();
			urlConnection.setRequestMethod("GET");
			
			br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			String result="";
			String line="";
			while((line=br.readLine()) != null) {
				result += line;
			}
			
			List<MasterTierDTO> masterTierList = new ArrayList<MasterTierDTO>();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray players = jsonObject.getJSONArray("players");
			for(int i = 0; i < players.length(); i++) {
				JSONObject object = players.getJSONObject(i);
				masterTierList.add(new MasterTierDTO(object.getString("name"), object.getInt("rank"), object.getInt("lp")));
			}

			model.addAttribute("masterTierList", masterTierList);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return "masterTierList";
	}
	
	@GetMapping("/LOLAPI/SearchSummonerName.do")
	public String searchSummoner(@RequestParam(value="summonerName") String summonerName,
			Model model) {
		try {
			apiURL = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+summonerName+"?api_key="+developKey;
			riotURL = new URL(apiURL);
			urlConnection = (HttpURLConnection)riotURL.openConnection();
			urlConnection.setRequestMethod("GET");
			
			br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			String result="";
			String line="";
			while((line=br.readLine()) != null) {
				result += line;
			}
			
			JSONObject jsonObject = new JSONObject(result);
			Date revisionDate = new Date((long)jsonObject.getLong("revisionDate"));
			
			SummonerDTO summonerDTO = new SummonerDTO(jsonObject.getString("accountId"),
											jsonObject.getInt("profileIconId"), revisionDate,
											jsonObject.getString("name"), jsonObject.getString("id"),
											jsonObject.getString("puuid"), jsonObject.getLong("summonerLevel"));
			
			String profileIconURL = "http://ddragon.leagueoflegends.com/cdn/10.11.1/img/profileicon/"+summonerDTO.getProfileIconId()+".png";
			
			model.addAttribute("summonerDTO", summonerDTO);
			model.addAttribute("icon", profileIconURL);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "searchSummoner";
	}
	
	// 소환사 전적 검색
	@GetMapping("/LOLAPI/MatchHistory.do")
	public String printMatchHistory(@RequestParam("puuid") String puuid, Model model) {
		try {
			apiURL = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/"+puuid+"/ids?start="+0+"&count="+20+"&api_key="+developKey;
			riotURL = new URL(apiURL);
			urlConnection = (HttpURLConnection)riotURL.openConnection();
			urlConnection.setRequestMethod("GET");
			
			br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
			String result="";
			String line="";
			while((line=br.readLine()) != null) {
				result += line;
			}
			
			// 최근 20경기중 가장 최근 경기의 데이터만 가져온다.(실험용)
			JSONArray matchList = new JSONArray(result);
			String matchId = (String) matchList.get(0);
			try {
				apiURL = "https://asia.api.riotgames.com/lol/match/v5/matches/"+matchId+"?api_key="+developKey;
				riotURL = new URL(apiURL);
				urlConnection = (HttpURLConnection)riotURL.openConnection();
				urlConnection.setRequestMethod("GET");
				
				br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
				result="";
				line="";
				while((line=br.readLine()) != null) {
					result += line;
				}
				JSONObject jsonObject = new JSONObject(result);
				JSONObject metadata = (JSONObject) jsonObject.get("metadata");
				JSONArray participantsPuuid = metadata.getJSONArray("participants");
				
				JSONObject info = (JSONObject)jsonObject.get("info");
				JSONArray participantData = info.getJSONArray("participants");
				
				for(int i = 0; i < participantData.length(); i++) {
					JSONObject jsonMatchData = (JSONObject) participantData.get(i);
					
					 if(!jsonMatchData.getString("puuid").equals(puuid))
						 continue;
					 else { 
						 
						 MatchDataDTO matchData = new MatchDataDTO();
						 // 선택한 챔피언 id 값 및 이름
						 matchData.setChampionId(jsonMatchData.getInt("championId"));
						 matchData.setChampionName(jsonMatchData.getString("championName"));
						 System.out.println("선택한 챔피언 id : " + matchData.getChampionId());
						 System.out.println("선택한 챔피언 이름 : " + matchData.getChampionName());
						 
						 // K/D/A 출력
						 matchData.setKills(jsonMatchData.getInt("kills"));
						 matchData.setDeaths(jsonMatchData.getInt("deaths"));
						 matchData.setAssists(jsonMatchData.getInt("assists"));
						 System.out.println("K/D/A : "+matchData.getKills()+"/"
								 	+matchData.getDeaths()+"/"+matchData.getAssists());
						 
						 // 승패 출력
						 matchData.setWin(jsonMatchData.getBoolean("win"));
						 if(matchData.isWin()) System.out.println("경기결과 : 승리!");
						 else System.out.println("경기결과 : 패배...");
						 
						 // 획득한 골드 및 소모한 골드 값 출력
						 matchData.setGoldEarned(jsonMatchData.getInt("goldEarned"));
						 matchData.setGoldSpent(jsonMatchData.getInt("goldSpent"));
						 System.out.println("획득한 골드 : " + matchData.getGoldEarned());
						 System.out.println("소모한 골드 : " + matchData.getGoldSpent());
						 
						 // 소환사 스펠 출력
						 matchData.setSpell1Casts(jsonMatchData.getInt("spell1Casts"));
						 matchData.setSpell2Casts(jsonMatchData.getInt("spell2Casts"));
						 matchData.setSpell3Casts(jsonMatchData.getInt("spell3Casts"));
						 matchData.setSpell4Casts(jsonMatchData.getInt("spell4Casts"));
						 System.out.println("소환사 스펠 id 값(1) : " + matchData.getSpell1Casts());
						 System.out.println("소환사 스펠 id 값(2) : " + matchData.getSpell2Casts());
						 System.out.println("소환사 스펠 id 값(3) : " + matchData.getSpell3Casts());
						 System.out.println("소환사 스펠 id 값(4) : " + matchData.getSpell4Casts());
						 
						 // 구매한 아이템 리스트 출력
						 List<Integer> itemList = new ArrayList<>();
						 
						 itemList.add(jsonMatchData.getInt("item0"));
						 itemList.add(jsonMatchData.getInt("item1"));
						 itemList.add(jsonMatchData.getInt("item2"));
						 itemList.add(jsonMatchData.getInt("item3"));
						 itemList.add(jsonMatchData.getInt("item4"));
						 itemList.add(jsonMatchData.getInt("item5"));
						 itemList.add(jsonMatchData.getInt("item6"));
						 matchData.setItemList(itemList);
						 System.out.println("최종 아이템 리스트 : " + matchData.getItemList().toString());
						 
						 break; 
					}	 
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
			
		return "matchHistory";
	}
}
