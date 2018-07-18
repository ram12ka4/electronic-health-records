package com.gnrchospitals;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/invest.do")
public class InvestigationSheetServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("ipName", request.getParameter("ip_no"));

		request.getRequestDispatcher("/WEB-INF/views/gnrc-invst-sheet.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		

		request.getRequestDispatcher("/WEB-INF/views/gnrc-invst-sheet.jsp").forward(request, response);

	}
}
