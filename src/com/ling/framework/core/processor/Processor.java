package com.ling.framework.core.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ling.framework.core.Response;

public interface Processor {

	/**
	 * 
	 * @param model
	 * @param httpResponse
	 * @param httpRequest
	 */
	public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest);
}