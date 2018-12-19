package com.gnrchospitals.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnrchospitals.dao.PatientDao;
import com.gnrchospitals.daoimpl.PatientDaoImpl;
import com.gnrchospitals.dto.Patient;

@WebServlet(urlPatterns = "/nurse.note")
public class NurseNoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PatientDao patientDao = new PatientDaoImpl();
	private Patient patient = Patient.getInstance(); // Singleton class

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String token = request.getParameter("token") == null ? "" : request.getParameter("token");
		String msg = request.getParameter("msg") == null ? "" : request.getParameter("msg");
		
		HttpSession session = request.getSession();

		try {
			session.setAttribute("moduleName", request.getParameter("moduleName"));
			request.setAttribute("ipNumber", request.getParameter("ip_no"));
			request.setAttribute("token", token);
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/WEB-INF/views/gnrc-nurse-note.jsp").forward(request, response);

		} catch (Exception e) {
			sendErrorReirect(request, response, "/WEB-INF/views/error.jsp", e);
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
		String nurseNote = request.getParameter("note") == null || request.getParameter("note").isEmpty() ? ""	: request.getParameter("note");
		String userId = (String) session.getAttribute("user");
		String nurseNoteNumber = request.getParameter("noteNo") == null ? "" : request.getParameter("noteNo");
	

		System.out.println("ACTION : " + action);
		System.out.println("patientNo : " + patientNo);
		System.out.println("mrd: " + mrd);
		System.out.println("visitNo : " + visitNo);
		System.out.println("doctorId : " + refDoctorId);
		System.out.println("wardNo : " + wardNo);
		System.out.println("bedNo : " + bedNo);
		System.out.println("userId : " + userId);
		System.out.println("Nurse Note Id : " + nurseNoteNumber);
		System.out.println("Nurse Note : " + nurseNote);
	

		try {
			
			if ("INSERT_UPDATE_NURSE_NOTE".equals(action)) {
				String drNumber = patientDao.insertUpdateNurseNote(patientNo, mrd, "VT01", "NA", "", bedNo, nurseNote, userId, nurseNoteNumber);
				out.print(drNumber);
			} else if ("FETCH_DOCTOR_LIST".equals(action)) {
				List<String> list = patientDao.getDoctorList();
				ObjectMapper mapper = new ObjectMapper();
				String jsonMapper = mapper.writeValueAsString(list);
				System.out.println("Service Rate List : " + jsonMapper);
				out.println(jsonMapper);
			} else if ("FETCH_PREVIOUS_NURSE_NOTE".equals(action)) {
				List<List<String>> list = patientDao.getPreviousNurseNotes(patientNo);
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
					String nrNo = row.get(colIndex);
					for (int i = 0; i < column.size(); i++) {
						out.println("<td>" + row.get(colIndex) + "</td>");
						colIndex++;
					}
					out.println(
							"<td><div class=\"view-class\"><button class=\"btn btn-warning btn-sm pat-view-btn\" nr-no=\""
									+ nrNo + "\">View</button></div></td>");
					out.println("</tr>");
					j++;
				}
				out.println("</tbody>");
				out.println("</table>");
			} else if ("FETCH_NURSE_NOTE_HISTORY".equals(action)) {
				List<String> list = patientDao.getNurseNote(nurseNoteNumber);
				System.out.println("Nurse Note : " + list);
				out.print(list);
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
