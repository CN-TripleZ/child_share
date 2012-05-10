package com.ling.framework.core.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ling.framework.core.ContextType;
import com.ling.framework.core.Response;

public class StringResponse implements Response {
	private Logger logger = Logger.getLogger(StringResponse.class.getName());

	private String content;
	private String contentType;

	public StringResponse() {
		contentType = ContextType.HTML;
	}

	public void finalize() throws Throwable {

	}

	public String getContent() {
		return content;
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
		this.content = content;
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
		try {
			InputStream in = new ByteArrayInputStream(this.content.getBytes("UTF-8"));
			return in;
		} catch (UnsupportedEncodingException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}
}