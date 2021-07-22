package com.springboot.codingtest.service;

import com.springboot.codingtest.pojo.ServiceRequest;
import com.springboot.codingtest.utilities.ApplicationResponse;

public interface BankMasterService {

	public ApplicationResponse getAtmMasterData();

	public ApplicationResponse getFilterAtmData(ServiceRequest request);
}
