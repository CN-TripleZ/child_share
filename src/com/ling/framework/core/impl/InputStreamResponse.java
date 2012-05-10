package com.ling.framework.core.impl;

import java.io.InputStream;

import com.ling.framework.core.Response;

public class InputStreamResponse implements Response {
	private String contentType;
	private InputStream inputStream;

	public InputStreamResponse(InputStream in) {
		this.inputStream = in;
	}

	public String getContent() {
		return "";
	}

	public String getStatusCode() {
		return "";
	}

	public String getContentType() {
		return this.contentType;
	}

	/**
	 * @param content
	 */
	public void setContent(String content) {

	}

	/**
	 * @param code
	 */
	public void setStatusCode(String code) {

	}

	/**
	 * @param contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public InputStream getInputStream() {
		return this.inputStream;
	}
}