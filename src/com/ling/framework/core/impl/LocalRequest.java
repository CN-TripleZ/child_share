package com.ling.framework.core.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ling.framework.core.Request;
import com.ling.framework.core.Response;
import com.ling.framework.util.JSPUtil;

public class LocalRequest implements Request {

	public LocalRequest() {

	}

	public void setExecuteParams(Map<String, String> params) {

	}

	/**
	 * @param uri
	 * @param httpResponse
	 * @param httpRequest
	 */
	public Response execute(String uri, HttpServletResponse httpResponse, HttpServletRequest httpRequest) {
		String content = JSPUtil.includeJspOutput(uri, httpRequest, httpResponse);
		Response response = new StringResponse();
		response.setContent(content);
		return response;
	}

	/**
	 * @param uri
	 */
	public Response execute(String uri) {
		return null;
	}
}