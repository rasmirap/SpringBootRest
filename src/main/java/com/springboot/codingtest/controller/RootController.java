package com.springboot.codingtest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mobiquity-services")
public class RootController {

	@GetMapping("/error")
	public String welcomeMessage() {
		return new StringBuilder("<h3>").append("Welcome to Spring Boot.").append("</h3>").toString();
	}
	
	@GetMapping("/")
	public String welcomeMessages() {
		return new StringBuilder("<h3>").append("Welcome to Spring Boot.").append("</h3>").toString();
	}

}
