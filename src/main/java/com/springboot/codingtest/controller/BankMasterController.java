package com.springboot.codingtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bank-master")
public class BankMasterController {

	@Autowired
	private RestTemplate client;

	@GetMapping("/get-atm-master-data")
	public String getCatalog() {
		String response = client.getForObject("https://www.ing.nl/api/locator/atms/", String.class);
		
		
		return response;
	}
}
