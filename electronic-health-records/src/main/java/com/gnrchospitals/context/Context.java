package com.gnrchospitals.context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Context implements AutoCloseable {

	private static ThreadLocal<Context> instance = new ThreadLocal<>();
	private HttpServletRequest request;
	private HttpServletResponse response;

	private Context(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public static Context newInstance(HttpServletRequest request, HttpServletResponse response) {
		Context context = new Context(request, response);
		instance.set(context);
		return context;
	}

	public static Context getCurrentinstance() {
		return instance.get();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public HttpSession getSession() {
		return request.getSession();
	}

	public ServletContext getServletContext() {
		return request.getServletContext();
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		instance.remove();
	}

}
