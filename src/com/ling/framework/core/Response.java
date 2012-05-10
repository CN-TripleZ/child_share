package com.ling.framework.core;

import java.io.InputStream;

public interface Response {

	public String getContent();

	public InputStream getInputStream();

	public String getStatusCode();

	public String getContentType();

	/**
	 * @param content
	 */
	public void setContent(String content);

	/**
	 * 设置状态码
	 * @param code
	 */
	public void setStatusCode(String code);

	/**
	 * @param contentType
	 */
	public void setContentType(String contentType);
}