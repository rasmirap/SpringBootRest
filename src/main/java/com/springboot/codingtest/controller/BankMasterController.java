package com.springboot.codingtest.controller;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.springboot.codingtest.utilities.AppConstants;
import com.springboot.codingtest.utilities.ApplicationResponse;
import com.springboot.codingtest.utilities.CommonUtility;
import com.springboot.codingtest.utilities.RestUtility;

@RestController
@RequestMapping("/bank-master")
public class BankMasterController {

	@Value("${atm.master.url}")
	String atmURL;

	static final Logger logger = LoggerFactory.getLogger(BankMasterController.class);
	@Autowired
	RestUtility restclient;

	@GetMapping("/get-atm-master-data")
	public ApplicationResponse getCatalog() {
		JsonNode node = null;
		ApplicationResponse appResp;
		String response = restclient.getService(atmURL);

		if (response.equals(AppConstants.ERROR)) {
			appResp = CommonUtility.getFailureResponse(AppConstants.SERVICE_CALL_ERROR, response);
		} else {
			node = CommonUtility.parseATMResponse(response);

			if (node.findValue("errorDesc") != null) {
				appResp = ApplicationResponse.getExceptionMsg(node);
			} else {
				appResp = ApplicationResponse.getSuccessMsg(node);
			}
		}
		return appResp;
	}

	@PostMapping("/get-atm-filter-data")
	public ApplicationResponse getCatalog(@RequestParam(value = "cityName") String cityName) {
		JsonNode node = null;
		ApplicationResponse appResp;
		String response = null;
		if (cityName == null || cityName.isEmpty()) {
			return CommonUtility.getFailureResponse(AppConstants.SERVICE_CALL_ERROR, AppConstants.INVALID_REQUEST);
		}
		response = restclient.getService(atmURL);

		if (response.equals(AppConstants.ERROR)) {
			appResp = CommonUtility.getFailureResponse(AppConstants.SERVICE_CALL_ERROR, response);
		} else {
			node = CommonUtility.parseATMResponse(response);
			List<JsonNode> data = StreamSupport.stream(node.spliterator(), true).filter(filterAtmWrtCity(cityName))
					.collect(Collectors.toList());
			if (node.findValue("errorDesc") != null) {
				appResp = ApplicationResponse.getExceptionMsg(node);
			} else {
				appResp = ApplicationResponse.getSuccessMsg(data);
			}
		}
		return appResp;
	}

	Predicate<JsonNode> filterAtmWrtCity(String city) {
		return atmNode -> CommonUtility.findValueFromNode(atmNode, "city").equalsIgnoreCase(city);
	};

}
