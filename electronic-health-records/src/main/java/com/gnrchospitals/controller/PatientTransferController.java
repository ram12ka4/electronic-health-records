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

@WebServlet(urlPatterns = "/patient.transfer")
public class PatientTransferController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PatientDao patientDao = new PatientDaoImpl();
	private Patient patient = Patient.getInstance(); // Singleton class
	private static Logger LOGGER = LoggerFactory.getLogger(PatientTransferController.class);

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
			LOGGER.info("user session exists");
			session.setAttribute("moduleName", request.getParameter("moduleName"));
			request.setAttribute("ipNumber", (String) request.getParameter("ip_no"));
			request.getRequestDispatcher("/WEB-INF/views/gnrc-patient-transfer.jsp").forward(request, response);
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
		String refDoctorId = request.getParameter("referDocId") == null ? "" : request.getParameter("referDocId");
		String fromWardNumber = patient.getWardCode();
		String fromBedNumber = patient.getBedNo();
		String bedNo = patient.getBedNo();
		String toWardNumber = request.getParameter("toWard") == null || request.getParameter("toWard").isEmpty() ? ""
				: request.getParameter("toWard");
		String recommDcotorId = request.getParameter("recommDcotorId") == null
				|| request.getParameter("recommDcotorId").isEmpty() ? "" : request.getParameter("recommDcotorId");
		String remark = request.getParameter("remark") == null || request.getParameter("remark").isEmpty() ? ""
				: request.getParameter("remark");
		String vacanctWardBed = request.getParameter("vacanctWardBed") == null
				|| request.getParameter("vacanctWardBed").isEmpty() ? "" : request.getParameter("vacanctWardBed");
		LOGGER.info("Vacant Ward Bed " + vacanctWardBed);
		String[] arr = vacanctWardBed.split("@", -1);
		String toRoomNumber = "";
		String toBedNumber = "";
		String toBedType = "";

		LOGGER.info("Array Length : " + arr.length);
		LOGGER.info("Array value : " + arr[0]);

		if (arr.length > 1) {
			toRoomNumber = arr[0];
			toBedNumber = arr[1];
			toBedType = arr[2];
			LOGGER.info("To Room Number : " + toRoomNumber);
			LOGGER.info("To Bed Number : " + toBedNumber);
			LOGGER.info("To Bed Number : " + toBedType);

		}
		String bedTypeFlag = "Y";
		String userId = (String) session.getAttribute("user");
		String patientTransferNumber = request.getParameter("ptNo") == null ? "" : request.getParameter("ptNo");

		LOGGER.info("ACTION : " + action);
		LOGGER.info("patientNo : " + patientNo);
		LOGGER.info("mrd: " + mrd);
		LOGGER.info("visitNo : " + visitNo);
		LOGGER.info("doctorId : " + refDoctorId);
		LOGGER.info("wardNo : " + fromWardNumber);
		LOGGER.info("bedNo : " + bedNo);
		LOGGER.info("userId : " + userId);
		LOGGER.info("Ward Code : " + toWardNumber);

		try {

			if ("INSERT_UPDATE_PATIENT_TRANSFER".equals(action)) {
				String ptNumber = patientDao.insertUpdatePatientTransfer(patientNo, mrd, fromWardNumber, fromBedNumber,
						toWardNumber, toRoomNumber, toBedNumber, toBedType, recommDcotorId, remark, userId,
						bedTypeFlag);
				out.print(ptNumber);
			} else if ("FETCH_DOCTOR_LIST".equals(action)) {
				response.setContentType("application/json");
				List<String> list = patientDao.getDoctorList();
				ObjectMapper mapper = new ObjectMapper();
				String jsonMapper = mapper.writeValueAsString(list);
				LOGGER.info("FETCH_DOCTOR_LIST : " + jsonMapper);
				out.println(jsonMapper);
			} else if ("FETCH_WARD_LIST".equals(action)) {
				List<String> list = patientDao.getWardNameList();
				LOGGER.info("FETCH_WARD_LIST : " + list);
				out.println(list);
			} else if ("FETCH_WARD_BED_LIST".equals(action)) {
				response.setContentType("application/json");
				List<String> list = patientDao.getWardBedNameList(toWardNumber);
				ObjectMapper mapper = new ObjectMapper();
				String jsonMapper = mapper.writeValueAsString(list);
				LOGGER.info("FETCH_WARD_BED_LIST : " + jsonMapper);
				out.println(jsonMapper);
			} else if ("FETCH_PREVIOUS_PATIENT_TRANSFER".equals(action)) {
				List<List<String>> list = patientDao.getPatientTransfer(patientNo);
				List<String> column = list.get(0);
				List<String> row = list.get(1);
				out.println(
						"<table id=\"example1\" class=\"table-striped table-bordered nowrap\" style=\"width:100%\">");
				out.println("<thead>");
				out.println("<tr>");
				out.println("<th>Select</th>");
				for (int i = 0; i < column.size(); i++) {
					out.println("<th>" + column.get(i) + "</th>");
				}
				/* out.println("<th>Action</th>"); */
				out.println("</tr>");
				out.println("</thead>");
				out.println("<tbody>"); // problem in this section
				int j = 0;
				int colIndex = 0;
				int rowIndex = row.size() / column.size();
				while (j < rowIndex) {
					out.println("<tr>");
					out.println("<td>" + (j + 1) + "</td>");
					// String ptNo = row.get(colIndex);
					for (int i = 0; i < column.size(); i++) {
						out.println("<td>" + row.get(colIndex) + "</td>");
						colIndex++;
					}
					/*
					 * out.println(
					 * "<td><div class=\"view-class\"><button class=\"btn btn-warning btn-sm pat-view-btn\" nr-no=\""
					 * + ptNo + "\">View</button></div></td>");
					 */
					out.println("</tr>");
					j++;
				}
				out.println("</tbody>");
				out.println("</table>");
			} else if ("FETCH_NURSE_NOTE_HISTORY".equals(action)) {
				List<String> list = patientDao.getNurseNote(patientTransferNumber);
				LOGGER.info("Nurse Note-{} : " + list);
				out.print(list);
			} else {
				request.getRequestDispatcher("/WEB-INF/views/gnrc-patient-transfer.jsp").forward(request, response);
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
