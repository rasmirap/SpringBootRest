package com.springboot.codingtest.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestUtility {

	@Autowired
	private RestTemplate client;

	public String getService(String url) {
		String response = null;
		try {
			response = client.getForObject(url, String.class);
		} catch (RestClientException e) {
			response = AppConstants.ERROR;
		}
		return response;
	}

	public String postService() {
		String response = "";

		return response;
	}

}
