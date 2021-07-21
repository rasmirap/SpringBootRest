package com.springboot.codingtest.utilities;



public class ApplicationResponse {

	private String responseCode;
	private String responseMsg;
	private Object body;
	private Object error;

	public Object getError() {
		return error;
	}

	public void setError(Object errorDesc) {
		this.error = errorDesc;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public static ApplicationResponse getSuccessMsg(Object msg) {

		ApplicationResponse response = new ApplicationResponse();

		response.setBody(msg);
		response.setResponseCode("0000");
		response.setResponseMsg("Success");

		return response;
	}

	public static ApplicationResponse getExceptionMsg(Object msg) {
		ApplicationResponse response = new ApplicationResponse();
		response.setError(msg);
		response.setResponseCode("0001");
		response.setResponseMsg("Failure");

		return response;
	}

}
