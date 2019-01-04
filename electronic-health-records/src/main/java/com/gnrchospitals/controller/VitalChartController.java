package com.gnrchospitals.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnrchospitals.dao.PatientDao;
import com.gnrchospitals.daoimpl.PatientDaoImpl;
import com.gnrchospitals.dto.Patient;

@WebServlet(urlPatterns = "/vital.chart")
public class VitalChartController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PatientDao patientDao = new PatientDaoImpl();
	private Patient patient = Patient.getInstance(); // Singleton class
	private static Logger LOGGER = LoggerFactory.getLogger(VitalChartController.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		LOGGER.info("Session ID : " + session.getAttribute("user"));
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Expires", "0"); // proxies

		if (session.getAttribute("user") == null) {
			LOGGER.info("Session null");
			response.sendRedirect("login.do");
		} else {
			LOGGER.info("user session exists");
			session.setAttribute("moduleName", request.getParameter("moduleName"));
			request.setAttribute("ipNumber", (String) request.getParameter("ip_no"));
			request.getRequestDispatcher("/WEB-INF/views/gnrc-vital-chart.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Map<String, String> map = new HashMap<String, String>();

		String action = request.getParameter("ACTION") == null ? "" : request.getParameter("ACTION");
		String patientNo = patient.getIpNumber();
		String mrd = patient.getMrdNumber();
		String visitNo = patient.getVisit();
		String refDoctorId = request.getParameter("referDocId") == null ? "" : request.getParameter("referDocId");
		String wardNo = patient.getWardCode();
		String bedNo = patient.getBedNo();
		String userId = (String) session.getAttribute("user");
		String vitalChartNumber = request.getParameter("vitalNo") == null ? "" : request.getParameter("vitalNo");

		String eyop = request.getParameter("EYOP") == null ? "" : request.getParameter("EYOP");
		String eyesOpen = request.getParameter("eyesOpen") == null ? "" : request.getParameter("eyesOpen");
		String vere = request.getParameter("VERE") == null ? "" : request.getParameter("VERE");
		String verbalResponse = request.getParameter("verbalResponse") == null ? ""
				: request.getParameter("verbalResponse");
		String more = request.getParameter("MORE") == null ? "" : request.getParameter("MORE");
		String motorResponse = request.getParameter("motorResponse") == null ? ""
				: request.getParameter("motorResponse");
		String blps = request.getParameter("BLPS") == null ? "" : request.getParameter("BLPS");
		String systolic = request.getParameter("systolic") == null ? "" : request.getParameter("systolic");
		String blpd = request.getParameter("BLPD") == null ? "" : request.getParameter("BLPD");
		String diastolic = request.getParameter("diastolic") == null ? "" : request.getParameter("diastolic");
		String pupl = request.getParameter("PUPL") == null ? "" : request.getParameter("PUPL");
		String leftPupilCloseOPen = request.getParameter("leftPupilCloseOPen") == null ? ""
				: request.getParameter("leftPupilCloseOPen");
		String leftPupilSize = request.getParameter("leftPupilSize") == null ? ""
				: request.getParameter("leftPupilSize");
		String pupr = request.getParameter("PUPR") == null ? "" : request.getParameter("PUPR");
		String rightPupilCloseOPen = request.getParameter("rightPupilCloseOPen") == null ? ""
				: request.getParameter("rightPupilCloseOPen");
		String rightPupilSize = request.getParameter("rightPupilSize") == null ? ""
				: request.getParameter("rightPupilSize");

		map.put(eyop, eyesOpen);
		map.put(vere, verbalResponse);
		map.put(more, motorResponse);
		map.put(blps, systolic);
		map.put(blpd, diastolic);
		map.put(pupl, leftPupilCloseOPen + leftPupilSize);
		map.put(pupr, rightPupilCloseOPen + rightPupilSize);

		LOGGER.info("ACTION : " + action);
		LOGGER.info("patientNo : " + patientNo);
		LOGGER.info("mrd: " + mrd);
		LOGGER.info("visitNo : " + visitNo);
		LOGGER.info("doctorId : " + refDoctorId);
		LOGGER.info("wardNo : " + wardNo);
		LOGGER.info("bedNo : " + bedNo);
		LOGGER.info("userId : " + userId);
		LOGGER.info("Nurse Note Id : " + vitalChartNumber);
		if (map != null) {
			LOGGER.info("Map Value : " + map);
		}

		try {

			if ("INSERT_UPDATE_VITAL_CHART".equals(action)) {
				String vcNumber = patientDao.insertUpdateVitalChart(patientNo, mrd, "VT01", "NA", "", bedNo, map,
						userId, vitalChartNumber);
				out.print(vcNumber);
			} else if ("FETCH_DOCTOR_LIST".equals(action)) {
				List<String> list = patientDao.getDoctorList();
				ObjectMapper mapper = new ObjectMapper();
				String jsonMapper = mapper.writeValueAsString(list);
				LOGGER.info("Service Rate List : " + jsonMapper);
				out.println(jsonMapper);
			} else if ("FETCH_PREVIOUS_VITAL_CHART".equals(action)) {
				List<String> list = patientDao.getPreviousVitalChart(patientNo);
				ObjectMapper mapper = new ObjectMapper();
				String jsonMapper = mapper.writeValueAsString(list);
				LOGGER.info("Service Rate List : " + jsonMapper);
				out.println(jsonMapper);
			} else {
				request.getRequestDispatcher("/WEB-INF/views/gnrc-nurse-note.jsp").forward(request, response);
				return;
			}
		} catch (Exception e) {
			sendErrorReirect(request, response, "/WEB-INF/views/error.jsp", e);
		}
	}

	protected void sendErrorReirect(HttpServletRequest request, HttpServletResponse response, String errroPageURL,
			Throwable e) throws ServletException, IOException {
		request.setAttribute("javax.servlet.jsp.jspException", e);
		getServletConfig().getServletContext().getRequestDispatcher(errroPageURL).forward(request, response);
	}

}
