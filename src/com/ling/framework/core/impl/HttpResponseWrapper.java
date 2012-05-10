package com.ling.framework.core.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @see http://bianbian.sunshow.net/
 */
public class HttpResponseWrapper extends HttpServletResponseWrapper {
	private MyPrintWriter writer;
	private ByteArrayOutputStream output;

	public HttpResponseWrapper(HttpServletResponse httpServletResponse) {
		super(httpServletResponse);
		output = new ByteArrayOutputStream();
		writer = new MyPrintWriter(output);
	}

	public void finalize() throws Throwable {
		super.finalize();
		output.close();
		writer.close();
	}

	public String getContent() {
		writer.flush();
		return writer.getByteArrayOutputStream().toString();
	}

	public PrintWriter getWriter() throws IOException {
		return writer;
	}

	public void close() throws IOException {
		writer.close();
	}

	private static class MyPrintWriter extends PrintWriter {
		ByteArrayOutputStream output;

		public MyPrintWriter(ByteArrayOutputStream output) {
			super(output);
			this.output = output;
		}

		public ByteArrayOutputStream getByteArrayOutputStream() {
			return output;
		}
	}
}