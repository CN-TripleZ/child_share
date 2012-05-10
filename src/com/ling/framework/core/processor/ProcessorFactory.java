package com.ling.framework.core.processor;

import javax.servlet.http.HttpServletRequest;

import com.ling.framework.core.processor.resource.WebResourceProcessor;

public abstract class ProcessorFactory {

	public static Processor getProcessor(String uri, HttpServletRequest httpRequest) {
		if (uri.startsWith("/_global/")) {
			return new WebResourceProcessor();
		}
		if (isExinclude(uri)) {
			return null;
		}
		return null;
	}

	private static boolean isExinclude(String uri) {
		String[] exts = new String[] { "jpg", "gif", "js", "png", "css", "doc", "xls", "swf" };
		for (String ext : exts) {
			if (uri.toUpperCase().endsWith(ext.toUpperCase())) {
				return true;
			}
		}
		return false;
	}
}