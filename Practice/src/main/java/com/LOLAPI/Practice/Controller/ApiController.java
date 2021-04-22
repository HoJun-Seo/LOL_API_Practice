package com.LOLAPI.Practice.Controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.LOLAPI.Practice.DTO.MasterTierDTO;
import com.LOLAPI.Practice.DTO.SummonerDTO;

@Controller
public class ApiController {
	
	String developKey = "RGAPI-8df9044c-61c9-4432-95da-cd4270427064";
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
}
