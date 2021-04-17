package com.LOLAPI.Practice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiController {
	
	@GetMapping("/")
	public String main() {
		return "main";
	}
}
