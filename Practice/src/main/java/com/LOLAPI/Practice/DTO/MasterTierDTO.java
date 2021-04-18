package com.LOLAPI.Practice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MasterTierDTO {
	
	private String name;
	private int rank;
	private int lp;
	
	@Override
	public String toString() {
		return "MasterTierDTO [name=" + name + ", rank=" + rank + ", lp=" + lp + "]";
	}
	
	
}
