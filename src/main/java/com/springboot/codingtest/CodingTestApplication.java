package com.springboot.codingtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CodingTestApplication {

	@Bean
	public RestTemplate getRestTemplate() {
		/*
		 * HttpComponentsClientHttpRequestFactory httpFactory = new
		 * HttpComponentsClientHttpRequestFactory();
		 * httpFactory.setConnectTimeout(15000);// 15 Seconds return new
		 */		return new RestTemplate();
	}

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/mobiquity-services");
		SpringApplication.run(CodingTestApplication.class, args);
	}

}
