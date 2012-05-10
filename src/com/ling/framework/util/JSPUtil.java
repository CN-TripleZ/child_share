package com.ling.framework.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ling.framework.core.impl.HttpResponseWrapper;

public abstract class JSPUtil {
	private static Logger logger = Logger.getLogger(JSPUtil.class.getName());

	public static String includeJspOutput(String jsppath, HttpServletRequest request, HttpServletResponse response) {
		HttpResponseWrapper wrapperResponse = new HttpResponseWrapper(response);
		try {
			request.getRequestDispatcher(jsppath).include(request, wrapperResponse);
		} catch (ServletException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return wrapperResponse.getContent();
	}

	public static String forwardJspOutput(String jsppath, HttpServletRequest request, HttpServletResponse response) {
		HttpResponseWrapper wrapperResponse = new HttpResponseWrapper(response);
		try {
			request.getRequestDispatcher(jsppath).forward(request, wrapperResponse);
		} catch (ServletException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return wrapperResponse.getContent();
	}
}