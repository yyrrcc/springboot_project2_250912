package com.mycompany.p2;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping(value = "/")
	public String index(Principal principal) {
		return "index";
	}	

	@GetMapping(value = "/map")
	public String map() {
		return "map";
	}
}
