package com.springboot.codingtest;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CodingTestApplication {

	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.setConnectTimeout(Duration.ofMillis(15000)).setReadTimeout(Duration.ofMillis(35000))
				.build();
	}

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/mobiquity-services");
		SpringApplication.run(CodingTestApplication.class, args);
	}

}
