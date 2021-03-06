package com.gnrchospitals.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnrchospitals.dao.LoginDao;
import com.gnrchospitals.dao.PatientDao;
import com.gnrchospitals.daoimpl.LoginDaoImpl;
import com.gnrchospitals.daoimpl.PatientDaoImpl;

@WebServlet(urlPatterns = "/patient.list")
public class PatientListController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PatientDao patientDao = new PatientDaoImpl();
	private LoginDao loginDao = new LoginDaoImpl();
	private static Logger LOGGER = LoggerFactory.getLogger(PatientListController.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Expires", "0"); // proxies

		if (session.getAttribute("user") == null) {
			LOGGER.info("Session null");
			response.sendRedirect("login.do");
		} else {
			LOGGER.info(" Category Code : " + request.getParameter("catCode"));
			session.setAttribute("categoryCode", request.getParameter("catCode"));
			request.getRequestDispatcher("/WEB-INF/views/gnrc-patient-list.jsp").forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		String userID = (String) session.getAttribute("user");
		String action = request.getParameter("ACTION") == null || request.getParameter("ACTION").isEmpty() ? ""
				: request.getParameter("ACTION");
		String moduleName = request.getParameter("moduleName") == null || request.getParameter("moduleName").isEmpty()
				? ""
				: request.getParameter("moduleName");
		String wardId = request.getParameter("wardId") == null || request.getParameter("wardId").isEmpty() ? ""
				: request.getParameter("wardId");

		try {

			if ("GET_PAT_DET".equals(action)) {
				response.setContentType("application/json");
				List<String> list = patientDao.getPatientList(userID, wardId);
				ObjectMapper mapper = new ObjectMapper();
				String jsonMapper = mapper.writeValueAsString(list);
				LOGGER.info("GET_PAT_DET : " + jsonMapper);
				out.print(jsonMapper);
			} else if ("GET_WARD_LIST".equals(action)) {
				List<String> list = patientDao.getWardList(userID);
				out.print(list);
			} else if ("GET_USER_CONTEXT_MENU".equals(action)) {
				response.setContentType("application/json");
				List<String> list = loginDao.userMenu(userID, moduleName);
				ObjectMapper mapper = new ObjectMapper();
				String jsonMapper = mapper.writeValueAsString(list);
				LOGGER.info("GET_USER_CONTEXT_MENU : " + jsonMapper);
				out.print(jsonMapper);
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
