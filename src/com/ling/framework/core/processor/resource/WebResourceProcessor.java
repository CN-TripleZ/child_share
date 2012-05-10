package com.ling.framework.core.processor.resource;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ling.framework.core.ContextType;
import com.ling.framework.core.Response;
import com.ling.framework.core.impl.InputStreamResponse;
import com.ling.framework.core.processor.Processor;


/**
 * web资源处理，如果以/_global/开头，则认为是加载ClassPath下的资源
 */
public class WebResourceProcessor implements Processor {
	private static Logger logger = Logger.getLogger(WebResourceProcessor.class.getName());

	public Response process(int mode, HttpServletResponse httpResponse, HttpServletRequest httpRequest) {
		String path = httpRequest.getServletPath();
		path = path.replaceAll("/_global/", "");
		try {
			InputStream in = getClass().getClassLoader().getResourceAsStream(path);
			Response response = new InputStreamResponse(in);
			if (path.toLowerCase().endsWith(".js")) {
				response.setContentType(ContextType.JAVASCRIPT);
			} else if (path.toLowerCase().endsWith(".css")) {
				response.setContentType(ContextType.CSS);
			} else if (path.toLowerCase().endsWith(".jpg")) {
				response.setContentType(ContextType.JPG);
			} else if (path.toLowerCase().endsWith(".gif")) {
				response.setContentType(ContextType.GIF);
			} else if (path.toLowerCase().endsWith(".png")) {
				response.setContentType(ContextType.PNG);
			}
			return response;
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, String.format("can't find resource[%s]", path), e);
		}
		return null;
	}
}