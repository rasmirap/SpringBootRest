package com.springboot.codingtest.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.codingtest.controller.BankMasterController;

@JsonInclude(Include.NON_NULL)
public class CommonUtility {

	static final ObjectMapper mapper = new ObjectMapper();
	static final Logger logger = LoggerFactory.getLogger(BankMasterController.class);

	public static JsonNode parseATMResponse(String data) {
		JsonNode node = null;
		try {

			if (data != null && !data.isEmpty()) {
				data = data.replaceAll("\r\n", "");
				if (data.indexOf("{\"mode\":\"full\",\"isActive\":false}") > 0) {
					data = data.substring(5, data.indexOf("{\"mode\":\"full\",\"isActive\":false}"));
				} else {
					data = data.substring(5);
				}
				node = mapper.readTree(data);
			}

		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException inside parseATMResponse");
			return getMessageAsNode(AppConstants.TECHNICAL_MSG, null);
		}
		return node;
	}

	public static ApplicationResponse getFailureResponse(String type, String errorMsg) {
		if (errorMsg != null) {
			return ApplicationResponse.getExceptionMsg(getMessageAsNode("errorDesc", errorMsg));
		} else if (type.equals(AppConstants.SERVICE_CALL_ERROR)) {
			return ApplicationResponse.getExceptionMsg(getMessageAsNode(AppConstants.TECHNICAL_MSG, null));
		} else {
			return ApplicationResponse.getExceptionMsg(getMessageAsNode(AppConstants.TECHNICAL_MSG, null));
		}

	}

	public static JsonNode getMessageAsNode(String key, String value) {
		JsonNode node = null;
		StringBuilder builder = null;
		try {
			if (value == null) {
				node = mapper.readTree(key);
			} else {
				builder = new StringBuilder();
				builder.append("{\"").append(key).append("\":\"").append(value).append("\"}");
				node = mapper.readTree(builder.toString());
			}
		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException inside getMessageAsNode");
		}
		return node;
	}

	public static String findValueFromNode(JsonNode node, String key) {
		if (node.findValue(key) != null) {
			return node.findValue(key).textValue();
		}
		return "";
	}

	public static String findKeyValueFromNode(String node, String key) {
		JsonNode jsonNode = null;
		try {
			jsonNode = mapper.readTree(node);
			if (jsonNode.findValue(key) != null) {
				return jsonNode.findValue(key).textValue();
			}
		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException inside findKeyValueFromNode");
		}
		return "";
	}

}
