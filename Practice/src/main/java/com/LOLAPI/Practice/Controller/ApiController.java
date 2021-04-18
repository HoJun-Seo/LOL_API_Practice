package com.LOLAPI.Practice.Controller;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.LOLAPI.Practice.DTO.MasterTierDTO;

@Controller
public class ApiController {
	
	String developKey = "RGAPI-6cbb7763-4fef-4059-bb31-8e1c55e86808";
	String apiURL = "";
	URL riotURL = null;
	HttpURLConnection urlConnection = null;
	BufferedReader br = null;
	
	@GetMapping("/")
	public String main() {
		return "main";
	}
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return "masterTierList";
	}
}
