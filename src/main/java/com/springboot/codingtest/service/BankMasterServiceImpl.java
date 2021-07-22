package com.springboot.codingtest.service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.springboot.codingtest.pojo.ServiceRequest;
import com.springboot.codingtest.utilities.AppConstants;
import com.springboot.codingtest.utilities.ApplicationResponse;
import com.springboot.codingtest.utilities.CommonUtility;
import com.springboot.codingtest.utilities.RestUtility;

@Service
public class BankMasterServiceImpl implements BankMasterService {
	static final Logger logger = LoggerFactory.getLogger(BankMasterServiceImpl.class);
	@Value("${atm.master.url}")
	String atmURL;
	@Autowired
	RestUtility restclient;

	@Override
	public ApplicationResponse getAtmMasterData() {
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

	@Override
	public ApplicationResponse getFilterAtmData(ServiceRequest request) {
		JsonNode node = null;
		ApplicationResponse appResp;
		String response = null;
		String cityName = request.getCityName();
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
