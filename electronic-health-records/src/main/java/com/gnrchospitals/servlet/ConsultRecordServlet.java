package com.gnrchospitals.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gnrchospitals.dao.PatientDao;
import com.gnrchospitals.dao.SequenceNumberDao;
import com.gnrchospitals.daoimpl.PatientDaoImpl;
import com.gnrchospitals.daoimpl.SequenceNumberDaoImpl;
import com.gnrchospitals.dto.Emr;
import com.gnrchospitals.dto.Patient;
import com.gnrchospitals.dto.SequenceNumber;

@WebServlet(urlPatterns = "/consult.do")
public class ConsultRecordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Patient patient = null;
	private Emr emr = null;
	private SequenceNumberDao sequenceNumberDao = new SequenceNumberDaoImpl();
	private PatientDao patientDao = new PatientDaoImpl();
	private Map<String, String> keyValue = new HashMap<>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();
			PrintWriter out = response.getWriter();
			String token = request.getParameter("token") == null ? "" : request.getParameter("token");
			String msg = request.getParameter("msg") == null ? "" : request.getParameter("msg");
			String ipNo = (String) session.getAttribute("ipNo") == null ? "" : (String) session.getAttribute("ipNo");
			String emr_det = request.getParameter("emrDetNo") == null ? "" : request.getParameter("emrDetNo");
			String userName = (String) session.getAttribute("user") == null ? ""
					: (String) session.getAttribute("user");
			String action = request.getParameter("ACTION") == null ? "" : request.getParameter("ACTION");

			if ("PREVIOUS_CONSULT_RECORDS".equals(action)) {

				List<List<String>> list = patientDao.getDoctorPreviousData(ipNo, "PREVIOUS_CONSULT_RECORDS");
				List<String> col = list.get(0);
				List<String> row = list.get(1);
				System.out.println("Doctor list in modal " + list);

				int rowCount = row.size() / col.size();
				int i = 0;
				int indexStart = 0;

				out.println("<div class=\"pagination-container\" >");

				while (i < rowCount) {

					indexStart = i * col.size();

					if (i == 0) {
						// out.println("<div class=\"text-center\">");
						out.println("<div class=\"pagination pagination-centered\">");
						out.println("<ul class=\"pagination \">");
						out.println("<li data-page=\"-\" ><a href=\"#\" >&lt;</a></li>");

						for (int k = 0; k < rowCount; k++) {
							out.println("<li data-page=\"" + (k + 1) + "\"><a href=\"#\" >" + (k + 1) + "</a></li>");
						}

						out.println("<li data-page=\"+\"><a href=\"#\" >&gt;</a></li>");
						out.println("</ul>");
						out.println("</div>");
						// out.println("</div>");
					}

					/*
					 * Start of div data page
					 */
					if (i == 0) {
						out.println("<div data-page=\"" + (i + 1) + "\">");
					} else {
						out.println("<div data-page=\"" + (i + 1) + "\" style=\"display:none;\">");
					}
					out.println("<p>");

					/*
					 * out.println("<p>"); out.println("Content for Div Number "+ i+1);
					 * out.println("</p>");
					 */

					out.println("<div class=\"row\">");
					out.println("<div class=\"column\">");

					out.println("<div class=\"column-header clearfix\">");
					out.println("<h5 class=\"pull-left\">" + row.get(indexStart + 2) + "</h5>");
					out.println("<h5 class=\"pull-right\">" + row.get(indexStart) + "</h5>");
					out.println("</div>");
					out.println("<hr>");
					out.println("<p>");
					out.println("<div class=\"form-horizontal\">");

					out.println("<div class=\"form-group\">");
					out.println(
							"<label class=\"control-label col-xs-3\" for=\"ref-by\"> <span class=\"required-label\" id=\"ref-by\"> Ref-by</span> :</label>");
					out.println("<div class=\"col-xs-6\">");
					out.println("<input type=\"text\"	value=\"" + row.get(indexStart + 2)
							+ "\" class=\"form-control input-sm ref-by\" placeholder=\"Date\" readonly>");
					out.println("</div>");
					out.println("</div>");
					out.println("<div class=\"form-group\">");
					out.println(
							"<label class=\"control-label col-xs-3\" for=\"medicine\"> <span class=\"required-label\" id=\"medicine\"> Consultant</span> :</label>");
					out.println("<div class=\"col-xs-6\">");
					out.println("<input type=\"text\"	value=\"" + row.get(indexStart + 4)
							+ "\" class=\"form-control input-sm ref-by\" placeholder=\"Date\" readonly>");
					out.println("</div>");
					out.println("</div>");
					out.println("<div class=\"form-group\">");
					out.println(
							"<label class=\"control-label col-xs-7\" for=\"clinical-notes\"> <span class=\"required-label\" id=\"clinical-notes\"> Brief clinical notes and reasons for refferal</span> :</label>");
					out.println("</div>");
					out.println("<div class=\"form-group\">");
					out.println("<div class=\"col-xs-12\">");
					out.println(
							"<textarea class=\"form-control input-sm ref-by\" placeholder=\"clinical-notes\" readonly>"
									+ row.get(indexStart + 6) + "</textarea>");
					out.println("</div>");
					out.println("</div>");

					out.println("</div>");
					out.println("</p>");

					out.println("</div>");

					out.println("<div class=\"column\">");
					out.println("<div class=\"column-header clearfix\">");
					out.println("<h5 class=\"pull-right\">" + row.get(indexStart + 1) + "</h5>");
					out.println("</div>");
					out.println("<hr>");
					out.println("<p>");
					out.println("<div class=\"form-horizontal\">");

					out.println("<div class=\"form-group\">");
					out.println(
							"<label class=\"control-label col-xs-3\" for=\"ref-by\"> <span class=\"required-label\" id=\"ref-by\"> Ref-to</span> :</label>");
					out.println("<div class=\"col-xs-6\">");
					out.println("<input type=\"text\"	value=\"" + row.get(indexStart + 3)
							+ "\" class=\"form-control input-sm ref-by\" placeholder=\"Date\" readonly>");
					out.println("</div>");
					out.println("</div>");
					out.println("<div class=\"form-group\">");
					out.println(
							"<label class=\"control-label col-xs-3\" for=\"medicine\"> <span class=\"required-label\" id=\"medicine\"> Consultant</span> :</label>");
					out.println("<div class=\"col-xs-6\">");
					out.println("<input type=\"text\"	value=\"" + row.get(indexStart + 5)
							+ "\" class=\"form-control input-sm ref-by\" placeholder=\"Date\" readonly>");
					out.println("</div>");
					out.println("</div>");
					out.println("<div class=\"form-group\">");
					out.println(
							"<label class=\"control-label col-xs-7\" for=\"clinical-notes\"> <span class=\"required-label\" id=\"clinical-notes\"> Consultant's opinion and recommendations </span> :</label>");
					out.println("</div>");
					out.println("<div class=\"form-group\">");
					out.println("<div class=\"col-xs-12\">");
					out.println(
							"<textarea class=\"form-control input-sm ref-by\" placeholder=\"clinical-notes\" readonly>"
									+ row.get(indexStart + 7) + "</textarea>");
					out.println("</div>");
					out.println("</div>");

					out.println("</div>");
					out.println("</p>");

					out.println("</div>");

					out.println("<div class=\"form-group\">");
					out.println("<div style=\"padding-right: 16px;\" class=\"pull-right\">");
					out.println("<a data-toggle=\"modal\" class=\"btn btn-warning btn-sm edit-link\" data-id='"
							+ row.get(indexStart + 1) + "'>edit</a>");
					out.println("<a data-toggle=\"modal\" class=\"btn btn-success btn-sm del-link\" data-id='"
							+ row.get(indexStart + 1) + "'>del</a>");
					out.println("</div>");
					out.println("</div>");

					out.println("</div>");

					/*
					 * Show content in element <p>
					 */
					out.println("</p>");

					/*
					 * end div data-page
					 */

					out.println("</div>");

					i++;
				}

				out.println("</div>");

			} else {
				request.setAttribute("token", token);
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("/WEB-INF/views/gnrc-consult-record.jsp").forward(request, response);

			}

		} catch (Exception e) {
			System.out.println("Going to sendErrorRidirect method");
			sendErrorReirect(request, response, "/WEB-INF/views/error.jsp", e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			HttpSession session = request.getSession();
			PrintWriter out = response.getWriter();
			String ipNo = (String) session.getAttribute("ipNo") == null ? "" : (String) session.getAttribute("ipNo");
			String emr_det = request.getParameter("emrDetNo") == null ? "" : request.getParameter("emrDetNo");
			String userName = (String) session.getAttribute("user") == null ? ""
					: (String) session.getAttribute("user");
			String action = request.getParameter("ACTION") == null ? "" : request.getParameter("ACTION");

			String referToDoctor = request.getParameter("CR001") == null ? "" : request.getParameter("CR001");
			String referToSpeciality = request.getParameter("CR003") == null ? "" : request.getParameter("CR003");
			String referByDoctor = request.getParameter("CR002") == null ? "" : request.getParameter("CR002");
			String referBySpeciality = request.getParameter("CR004") == null ? "" : request.getParameter("CR004");
			String date = request.getParameter("CR005") == null ? "" : request.getParameter("CR005");
			String clinicalNotes = request.getParameter("CR006") == null ? "" : request.getParameter("CR006");
			String consultantOpinion = request.getParameter("CR007") == null ? "" : request.getParameter("CR007");

			if ("DEL_CONSULTANT".equals(action)) {

				System.out.println("EMD DET NO" + emr_det);
				boolean status = deleteDoctorData(emr_det);
				System.out.println("DELETE STATUS : " + status);
				out.print(status);

			} else {

				long start = System.currentTimeMillis();

				System.out.println("REFER TO DOCTOR : " + referToDoctor);
				System.out.println("REFER TO SPECIALITY : " + referToSpeciality);
				System.out.println("REFER BY DOCTOR : " + referByDoctor);
				System.out.println("REFER BY SPECIALITY : " + referBySpeciality);
				System.out.println("DATE : " + date);
				System.out.println("CLINICAL NOTES : " + clinicalNotes);
				System.out.println("CONSULTANT OPINION : " + consultantOpinion);

				keyValue.put("CR001", referToDoctor);
				keyValue.put("CR002", referToSpeciality);
				keyValue.put("CR003", referByDoctor);
				keyValue.put("CR004", referBySpeciality);
				keyValue.put("CR005", date);
				keyValue.put("CR006", clinicalNotes);
				keyValue.put("CR007", consultantOpinion);

				patient = Patient.getInstance();
				emr = Emr.getInstance();

				System.out.println("Patient Data" + patient);
				System.out.println("EMR Data " + emr);

				boolean isIpPresent = patientDao.getValidatedIp(ipNo);

				System.out.println("IS IP PRESENT : " + isIpPresent);

				String emrNo = "";

				if (!isIpPresent) {

					SequenceNumber emrSeqNum = new SequenceNumber("EMR", userName);
					emrNo = sequenceNumberDao.getSequenceNumber(emrSeqNum);
					emr = Emr.getInstance();
					System.out.println("EMR Instance ID before insertEmrClinicalData " + emr);

					emr.setEmrNo(emrNo);
					emr.setVisitNo("VT01");
					emr.setEncounterNo("1");
					emr.setCreateUser(userName);
					emr.setUpdateUser(userName);
					patient.setEmr(emr);

					boolean isEmrClinicalDataInsert = patientDao.insertEmrClinicalData(patient);

					if (!isEmrClinicalDataInsert) {
						response.sendRedirect("/consult.do?token=fail&msg=Something went wrong");
					}

				} else {
					boolean emrStatus = patientDao.findEmrByIpNumber(ipNo);
					System.out.println("Find Emr status : " + emrStatus);
					emrNo = patient.getEmr().getEmrNo();
				}

				SequenceNumber emrDetSeqNum = new SequenceNumber("EMRDET", userName);

				System.out.println("EMRDET Sequence Number : " + emrDetSeqNum);

				String emrDetNo = sequenceNumberDao.getSequenceNumber(emrDetSeqNum);

				emr.setCreateUser(userName);
				emr.setUpdateUser(userName);
				emr.setKeyValue(keyValue);
				emr.setEmrDetNo(emrDetNo);
				patient.setEmr(emr);

				boolean isEmrHealthDataInsert = patientDao.insertEmrHealthData(patient);

				System.out.println("Emr Health Data : " + isEmrHealthDataInsert);

				long end = System.currentTimeMillis();

				System.out.println("Time takes to process this : " + (end - start) + " ms");

				if (isEmrHealthDataInsert) {
					response.sendRedirect("/consult.do?token=success&msg=Data have been added successfully");
				} else {
					response.sendRedirect("/consult.do?token=fail&msg=Something went wrong");
				}
			}

		} catch (Exception e) {
			System.out.println("Going to sendErrorRidirect method");
			sendErrorReirect(request, response, "/WEB-INF/views/error.jsp", e);
		}

	}

	protected void sendErrorReirect(HttpServletRequest request, HttpServletResponse response, String errroPageURL,
			Throwable e) throws ServletException, IOException {
		request.setAttribute("javax.servlet.jsp.jspException", e);
		getServletConfig().getServletContext().getRequestDispatcher(errroPageURL).forward(request, response);
	}

	public boolean deleteDoctorData(String emrDetNumber) throws SQLException {
		return patientDao.deleteDoctorData(emrDetNumber);
	}

}
