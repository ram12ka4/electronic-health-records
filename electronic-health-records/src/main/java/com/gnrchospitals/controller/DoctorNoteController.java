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
import com.gnrchospitals.dao.PatientDao;
import com.gnrchospitals.daoimpl.PatientDaoImpl;
import com.gnrchospitals.dto.Patient;

@WebServlet(urlPatterns = "/doctor.note")
public class DoctorNoteController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PatientDao patientDao = new PatientDaoImpl();
	private Patient patient = Patient.getInstance(); // Singleton class
	private static Logger LOGGER = LoggerFactory.getLogger(DoctorNoteController.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	/*	String token = request.getParameter("token") == null ? "" : request.getParameter("token");
		String msg = request.getParameter("msg") == null ? "" : request.getParameter("msg");*/
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setHeader("Expires", "0"); // proxies
		
		HttpSession session = request.getSession(true);
		
		 if(session.getAttribute("user") == null) {
				LOGGER.info("Session null");
				response.sendRedirect("login.do");
			} else {
				LOGGER.info("user session exists");
				session.setAttribute("moduleName", request.getParameter("moduleName"));
				request.setAttribute("ipNumber", (String) request.getParameter("ip_no"));
				request.getRequestDispatcher("/WEB-INF/views/gnrc-doctor-note.jsp").forward(request, response);
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		String action = request.getParameter("ACTION") == null ? "" : request.getParameter("ACTION");
		String patientNo = patient.getIpNumber();
		String mrd = patient.getMrdNumber();
		String visitNo = patient.getVisit();
		String refDoctorId =  request.getParameter("referDocId") == null ? "" : request.getParameter("referDocId");
		String wardNo = patient.getWardCode();
		String bedNo = patient.getBedNo();
		String advice = request.getParameter("treatment") == null || request.getParameter("treatment").isEmpty() ? ""	: request.getParameter("treatment");
		String medication = request.getParameter("medication") == null || request.getParameter("medication").isEmpty() ? ""	: request.getParameter("medication");
		String laboratory = request.getParameter("laboratory") == null || request.getParameter("laboratory").isEmpty() ? ""	: request.getParameter("laboratory");
		String diet = request.getParameter("diet") == null || request.getParameter("diet").isEmpty() ? ""	: request.getParameter("diet");
		String progress = request.getParameter("progress") == null || request.getParameter("progress").isEmpty() ? ""	: request.getParameter("progress");
		String userId = (String) session.getAttribute("user");
		String doctorOrderoNumber = request.getParameter("noteNumber") == null ? "" : request.getParameter("noteNumber");
	

		LOGGER.info("ACTION : " + action);
		LOGGER.info("PATIENT NO : " + patientNo);
		LOGGER.info("MRD : " + mrd);
		LOGGER.info("VISIT NO : " + visitNo);
		LOGGER.info("DOCTOR ID : " + refDoctorId);
		LOGGER.info("WARD NO : " + wardNo);
		LOGGER.info("BED NO : " + bedNo);
		LOGGER.info("ADVICE : " + advice);
		LOGGER.info("MEDICATION : " + medication);
		LOGGER.info("LABORATORY : " + laboratory);
		LOGGER.info("DIET : " + diet);
		LOGGER.info("PROGRESS : " + progress);
		LOGGER.info("USER ID : " + userId);
	

		try {
			
			if ("INSERT_UPDATE_DOCTOR_ORDER".equals(action)) {
				String drNumber = patientDao.insertUpdateDoctorOrder(patientNo, mrd, "VT01", refDoctorId, "", bedNo, advice, medication, laboratory, diet, progress, userId, doctorOrderoNumber);
				out.print(drNumber);
			} else if ("FETCH_DOCTOR_LIST".equals(action)) {
				List<String> list = patientDao.getDoctorList();
				ObjectMapper mapper = new ObjectMapper();
				String jsonMapper = mapper.writeValueAsString(list);
				LOGGER.info("FETCH_DOCTOR_LIST : " + jsonMapper);
				out.println(jsonMapper);
			} else if ("FETCH_PREVIOUS_DOCTOR_ORDER".equals(action)) {
				List<List<String>> list = patientDao.getPreviousDoctorNotes(patientNo);
				List<String> row = list.get(1);
				List<String> column = list.get(0);
				out.println(
						"<table id=\"example1\" class=\"table-striped table-bordered nowrap\" style=\"width:100%\">");
				out.println("<thead>");
				out.println("<tr>");
				out.println("<th>Select</th>");
				for (int i = 0; i < column.size(); i++) {
					out.println("<th>" + column.get(i) + "</th>");
				}
				out.println("<th>Action</th>");
				out.println("</tr>");
				out.println("</thead>");
				out.println("<tbody>"); // problem in this section
				int j = 0;
				int colIndex = 0;
				int rowIndex = row.size() / column.size();
				while (j < rowIndex) {
					out.println("<tr>");
					out.println("<td>" + (j + 1) + "</td>");
					String drNo = row.get(colIndex);
					// String soNo = row.get(colIndex+1);
					for (int i = 0; i < column.size(); i++) {
						out.println("<td>" + row.get(colIndex) + "</td>");
						colIndex++;
					}
					out.println(
							"<td><div class=\"view-class\"><button class=\"btn btn-warning btn-sm pat-view-btn\" dr-no=\""
									+ drNo + "\">View</button></div></td>");
					out.println("</tr>");
					// colIndex +=1;
					j++;
				}
				out.println("</tbody>");
				out.println("</table>");
			} else if ("FETCH_PATIENT_HISTORY".equals(action)) {
				List<String> list = patientDao.getDoctorNote(doctorOrderoNumber);
				LOGGER.info("Doctor Note : " + list);
				out.print(list);
			} else {
				request.getRequestDispatcher("/WEB-INF/views/gnrc-doctor-note.jsp").forward(request, response);
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
