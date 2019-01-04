package com.gnrchospitals.servlet.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*@WebFilter("/RequestLoggingFilter")*/
public class RequestLoggingFilter implements Filter {

	private ServletContext context;
	private static Logger LOGGER = LoggerFactory.getLogger(RequestLoggingFilter.class);

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		//this.context.log("RequestLogginFilter initialized");
		this.context.log("RequestLogginFilter initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		Enumeration<String> params = req.getParameterNames();
		
		this.context.log("RequestLogginFilter step 1");
		
		while (params.hasMoreElements()) {
			String name = params.nextElement();
			String value = request.getParameter(name);
			//this.context.log(req.getRemoteAddr() + "::Request Params::{" + name + "=" + value + "}");
			this.context.log(req.getRemoteAddr() + "::Request Params::{" + name + "=" + value + "}");
		}
		
		this.context.log("RequestLogginFilter step 2");

		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				this.context.log(req.getRemoteAddr() + "::Cookie::{" + cookie.getName() + "," + cookie.getValue() + "}");
			}
		}
		
		this.context.log("RequestLogginFilter step 3");
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
