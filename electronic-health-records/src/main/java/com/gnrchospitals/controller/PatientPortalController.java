package com.gnrchospitals.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = "/patient.portal")
public class PatientPortalController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(PatientPortalController.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info("");
		request.getRequestDispatcher("/WEB-INF/views/gnrc-patient-portal.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		
	}

}
