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

@WebServlet(urlPatterns = "/doctor.note")
public class DoctorNoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PatientDao patientDao = new PatientDaoImpl();
	private Patient patient = Patient.getInstance(); // Singleton class

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String token = request.getParameter("token") == null ? "" : request.getParameter("token");
		String msg = request.getParameter("msg") == null ? "" : request.getParameter("msg");
		
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		try {

			

			session.setAttribute("moduleName", request.getParameter("moduleName"));
			request.setAttribute("ipNumber", request.getParameter("ip_no"));
			request.setAttribute("token", token);
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/WEB-INF/views/gnrc-doctor-note.jsp").forward(request, response);

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
		String wardNo = patient.getWardNo();
		String bedNo = patient.getBedNo();
		String advice = request.getParameter("treatment") == null || request.getParameter("treatment").isEmpty() ? ""	: request.getParameter("treatment");
		String medication = request.getParameter("medication") == null || request.getParameter("medication").isEmpty() ? ""	: request.getParameter("medication");
		String laboratory = request.getParameter("laboratory") == null || request.getParameter("laboratory").isEmpty() ? ""	: request.getParameter("laboratory");
		String diet = request.getParameter("diet") == null || request.getParameter("diet").isEmpty() ? ""	: request.getParameter("diet");
		String progress = request.getParameter("progress") == null || request.getParameter("progress").isEmpty() ? ""	: request.getParameter("progress");
		String userId = (String) session.getAttribute("user");
		String doctorOrderoNumber = request.getParameter("noteNumber") == null ? "" : request.getParameter("noteNumber");
	

		System.out.println("ACTION : " + action);
		System.out.println("patientNo : " + patientNo);
		System.out.println("mrd: " + mrd);
		System.out.println("visitNo : " + visitNo);
		System.out.println("doctorId : " + refDoctorId);
		System.out.println("wardNo : " + wardNo);
		System.out.println("bedNo : " + bedNo);
		System.out.println("advice : " + advice);
		System.out.println("medication : " + medication);
		System.out.println("laboratory : " + laboratory);
		System.out.println("diet : " + diet);
		System.out.println("progress : " + progress);
		System.out.println("userId : " + userId);
	

		try {
			
			if ("INSERT_UPDATE_DOCTOR_ORDER".equals(action)) {
				
				String soNumber = patientDao.insertDoctorOrderData(patientNo, mrd, "VT01", refDoctorId, "", bedNo, advice, medication, laboratory, diet, progress, userId, doctorOrderoNumber);
				out.print(soNumber);
			
			//	out.print(soNumber);
			} else if ("FETCH_DOCTOR_LIST".equals(action)) {
				List<String> list = patientDao.getDoctorList();
				ObjectMapper mapper = new ObjectMapper();
				String jsonMapper = mapper.writeValueAsString(list);
				System.out.println("Service Rate List : " + jsonMapper);
				out.println(jsonMapper);
			} else if ("FETCH_PREVIOUS_SERVICE_ORDER".equals(action)) {
				/*List<List<String>> list = patientDao.getPrevServiceOrderList(patientNo);
				List<String> row = list.get(1);
				List<String> column = list.get(0);
				out.println(
						"<table id=\"example1\" class=\"table-striped table-bordered nowrap\" style=\"width:100%\">");
				out.println("<thead>");
				out.println("<tr>");
				out.println("<th>Select</th>");
				for (int i = 0; i < column.size()-1; i++) {
					out.println("<th>" + column.get(i+1) + "</th>");
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
					out.println("<td>" + (j+1) + "</td>");
					String ipNo= row.get(colIndex);
					String soNo = row.get(colIndex+1);
						for (int i=0; i<column.size()-1; i++) {
							out.println("<td>" + row.get(colIndex+1) + "</td>");
							colIndex++;
						}
						out.println("<td><div class=\"delete-btn\"><button class=\"btn btn-warning btn-sm view-btn\" so-no=\""+soNo+"\" ip-no=\""+ipNo+"\">View</button></div></td>");	
					out.println("</tr>");
					colIndex +=1;
					j++;
				}
				out.println("</tbody>");
				out.println("</table>");*/
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
