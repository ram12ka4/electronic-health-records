package com.gnrchospitals.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns= "/vital.chart")
public class VitalChartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession session = request.getSession();
		
		
		session.setAttribute("moduleName", request.getParameter("moduleName"));
		request.setAttribute("ipNumber", (String) session.getAttribute("ip_no"));
		request.getRequestDispatcher("/WEB-INF/views/gnrc-neuro-vital-chart.jsp").forward(request, response);
	}

}
