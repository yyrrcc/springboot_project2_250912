package com.mycompany.p2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringbootProject2250912Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootProject2250912Application.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringbootProject2250912Application.class);
		
	}
	

}
