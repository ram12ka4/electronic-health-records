package com.gnrchospitals.servlet.filters;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/*@WebFilter("/AuthenticationFilter")*/
public class AuthenticationFilter implements Filter {

	private ServletContext context;
	private static Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		//this.context.log("AuthenticationFilter initialized");
		this.context.log("AuthenticationFilter initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String uri = req.getRequestURI();
		//this.context.log("Requested Resource::" + uri);
		this.context.log("Requested Resource::" + uri);

		HttpSession session = req.getSession(false);
		
		this.context.log("Authentication Session : " + session);
		

		if (session == null) {
			this.context.log("Unauthorized access request");
			PrintWriter out = res.getWriter();
		    out.write("<h1>Unauthorised Access</h1>");
			//res.sendRedirect("login.do");

		} else {
			// pass the request along the filter chain
			this.context.log("Authorised access request");
			chain.doFilter(request, response);
		}
		

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
