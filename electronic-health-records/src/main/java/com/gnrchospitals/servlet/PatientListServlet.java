package com.gnrchospitals.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gnrchospitals.dao.PatientDao;
import com.gnrchospitals.daoimpl.PatientDaoImpl;

@WebServlet(urlPatterns = "/patient.list")
public class PatientListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PatientDao patientDao = new PatientDaoImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/views/gnrc-patient-list.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		String empCode = request.getParameter("empId") == null || request.getParameter("empId").isEmpty() ? ""
				: request.getParameter("empId");
		String action = request.getParameter("ACTION") == null || request.getParameter("ACTION").isEmpty() ? ""
				: request.getParameter("ACTION");

		try {

			if ("GET_WARD".equals(action)) {

				List<String> list = patientDao.getWardList(empCode);

				out.print(list);

			} else {
				request.getRequestDispatcher("/WEB-INF/views/gnrc-patient-list.jsp").forward(request, response);
			}

		} catch (Exception ex) {
			sendErrorReirect(request, response, "/WEB-INF/views/error.jsp", ex);
		}


	}

	protected void sendErrorReirect(HttpServletRequest request, HttpServletResponse response, String errroPageURL,
			Throwable e) throws ServletException, IOException {
		request.setAttribute("javax.servlet.jsp.jspException", e);
		getServletConfig().getServletContext().getRequestDispatcher(errroPageURL).forward(request, response);

	}

}
