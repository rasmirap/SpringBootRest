package com.springboot.codingtest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.codingtest.pojo.ServiceRequest;
import com.springboot.codingtest.service.BankMasterService;
import com.springboot.codingtest.utilities.AppConstants;
import com.springboot.codingtest.utilities.ApplicationResponse;
import com.springboot.codingtest.utilities.CommonUtility;

@RestController
@RequestMapping("/bank-master")
public class BankMasterController {
	static final Logger logger = LoggerFactory.getLogger(BankMasterController.class);
	@Autowired
	BankMasterService service;

	@GetMapping("/get-atm-master-data")
	public ApplicationResponse getAtmMasterData() {
		return service.getAtmMasterData();
	}

	@PostMapping(path = "/get-atm-filter-data", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ApplicationResponse getCatalog(@RequestBody ServiceRequest cityRequest) {

		String cityName = cityRequest.getCityName();
		if (cityName == null || cityName.isEmpty()) {
			return CommonUtility.getFailureResponse(AppConstants.SERVICE_CALL_ERROR, AppConstants.INVALID_REQUEST);
		}
		return service.getFilterAtmData(cityRequest);
	}

}
