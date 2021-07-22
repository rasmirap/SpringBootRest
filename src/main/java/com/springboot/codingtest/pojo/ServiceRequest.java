package com.springboot.codingtest.pojo;

import java.io.Serializable;

public class ServiceRequest implements Serializable{
	private String cityName;
	private String requestId;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
