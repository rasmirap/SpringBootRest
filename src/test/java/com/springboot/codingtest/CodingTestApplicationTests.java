package com.springboot.codingtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.springboot.codingtest.controller.BankMasterController;
import com.springboot.codingtest.pojo.ServiceRequest;
import com.springboot.codingtest.utilities.RestUtility;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { CodingTestApplication.class,
		BankMasterController.class,
		RestUtility.class })
@TestPropertySource(locations = "classpath:test.properties")
class CodingTestApplicationTests {
	Logger logger = LoggerFactory.getLogger(CodingTestApplicationTests.class);

	@LocalServerPort
	int port;

	@Value("${myserver.host.name}")
	String hostname;

	@Value("${server.servlet.context-path}")
	String appContext;

	@Value("${get.atm.master.url}")
	String getAtmMasterUrl;

	@Value("${get.atmlocation.based.city.url}")
	String getAtmFilterUrl;

	@Test
	void callGetAtmMasterData() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Basic dXNlcjptb2JpcXVpdHlAMDAwOQ==");
		ResponseEntity<String> entity = new TestRestTemplate().exchange(generateUrl(hostname + port, getAtmMasterUrl),
				HttpMethod.GET, new HttpEntity<Void>(headers), String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody().contains("Success"));

	}

	@Test
	void callGetAtmMasterFilterData() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Basic dXNlcjptb2JpcXVpdHlAMDAwOQ==");
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		ServiceRequest request = new ServiceRequest();
		request.setCityName("Best");
		HttpEntity<ServiceRequest> requestEntity = new HttpEntity<ServiceRequest>(request, headers);
		ResponseEntity<String> entity = new TestRestTemplate().exchange(generateUrl(hostname + port, getAtmFilterUrl),
				HttpMethod.POST, requestEntity, String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(entity.getBody().contains("Success"));
		
	}

	public String generateUrl(String... args) {
		StringBuilder builder = new StringBuilder();
		for (String arg : args) {
			builder.append(arg);
		}
		return builder.toString();
	}

}
