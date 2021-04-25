package com.LOLAPI.Practice.DTO;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MatchDataDTO {
	
	private boolean win;
	private int championId;
	private String championName;
	private int goldEarned;
	private int goldSpent;
	private List<Integer> itemList;
	private int kills;
	private int assists;
	private int deaths;
	
	private int spell1Casts;
	private int spell2Casts;
	private int spell3Casts;
	private int spell4Casts;
	
	
}
