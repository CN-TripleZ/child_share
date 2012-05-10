package com.ling.framework.core.processor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ling.framework.core.Response;



public class DispatcherFilter implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest   = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		try {
			String uri = httpRequest.getServletPath();
			Processor processor = ProcessorFactory.getProcessor(uri, httpRequest);
			if (processor == null) {
				chain.doFilter(request, response);
			} else {
				Response res = processor.process(0, httpResponse, httpRequest);
				InputStream is = res.getInputStream();
				if (is != null) {
					BufferedInputStream bis = new BufferedInputStream(is);// 输入缓冲流
					response.setContentType(res.getContentType() + "; charset=UTF-8");
					OutputStream output = response.getOutputStream();
					BufferedOutputStream bos = new BufferedOutputStream(output);// 输出缓冲流

					byte data[] = new byte[4096];// 缓冲字节数
					int size = 0;

					if (bis != null) {
						size = bis.read(data);
						while (size != -1) {
							bos.write(data, 0, size);
							size = bis.read(data);
						}
					}
					bis.close();
					bos.flush();
					bos.close();
				} else {
					chain.doFilter(request, response);
				}
			}
		} catch (RuntimeException exception) {
			exception.printStackTrace();
		}
	}

	public void destroy() {
	}
}