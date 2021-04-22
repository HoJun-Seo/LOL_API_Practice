package com.LOLAPI.Practice.DTO;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SummonerDTO {
	private String accountId;
	private int profileIconId;
	private Date revisionDate;
	private String name;
	private String id;
	private String puuid;
	private long summonerLevel;
}
