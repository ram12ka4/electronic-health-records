package com.gnrchospitals;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/pat_panel.do")
public class PatientPanelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String ipNo = request.getParameter("ip_no") == null ? "" : request.getParameter("ip_no");

		HttpSession ipNoSession = request.getSession();
		
		ipNoSession.setAttribute("ipNo", ipNo);

		/*
		 * RequestDispatcher rd = getServletContext().getRequestDispatcher(
		 * "/WEB-INF/views/gnrc-indoor-patient-pane.jsp"); rd.include(request,
		 * response);
		 */

		request.getRequestDispatcher("/WEB-INF/views/gnrc-indoor-patient-pane.jsp").forward(request, response);

	}

}
