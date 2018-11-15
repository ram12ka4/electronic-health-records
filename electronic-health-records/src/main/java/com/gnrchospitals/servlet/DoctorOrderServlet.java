package com.gnrchospitals.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
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

@WebServlet(urlPatterns = "/docorder.do")
public class DoctorOrderServlet extends HttpServlet {

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
			String action = request.getParameter("ACTION") == null ? "" : request.getParameter("ACTION");

			if ("DOCTOR_PREVIOUS_ORDERS".equals(action)) {

				List<List<String>> list = patientDao.getDoctorPreviousData(ipNo, "DOCTORE_PREVIOUS_ORDERS");
				List<String> col = list.get(0);
				List<String> row = list.get(1);
				System.out.println("Doctor list in modal " + list);

				int rowCount = row.size() / col.size();
				int i = 0;
				int indexStart = 0;

				while (i < rowCount) {

					indexStart = i * col.size();

					String[] medicine = row.get(indexStart + 3).replace("[", "").replace("]", "").split(",");
					String[] laboratory = row.get(indexStart + 6).replace("[", "").replace("]", "").split(",");

					out.println("<div class=\"row\">");
					out.println("<div class=\"column\">");
					out.println("<div class=\"column-header clearfix\">");
					out.println("<h5 class=\"pull-left\">" + row.get(indexStart + 2) + "</h5>");
					out.println("</div>");
					out.println("<hr>");
					out.println("<p>");
					out.println("<div class=\"form-horizontal\">");

					out.println("<div class=\"form-group\">");
					out.println(
							"<label class=\"control-label col-xs-3\" for=\"treatment\"> <span class=\"required-label\" id=\"treatment\"> Treatment</span> :</label>");
					out.println("<div class=\"col-xs-3\">");
					out.println(row.get(indexStart + 4) == null ? "NO DATA" : row.get(indexStart + 4));
					out.println("</div>");
					out.println("</div>");
					out.println("<div class=\"form-group\">");
					out.println(
							"<label class=\"control-label col-xs-3\" for=\"medicine\"> <span class=\"required-label\" id=\"medicine\"> Medicine</span> :</label>");
					out.println("<div class=\"col-xs-3\">");

					out.println("<ol>");
					for (int k = 0; k < medicine.length; k++) {
						out.println("<li>" + medicine[k] + "</li>");
					}
					out.println("</ol>");

					out.println("</div>");
					out.println("</div>");

					out.println("</div>");
					out.println("</p>");
					out.println("</div>");
					out.println("<div class=\"column\">");
					out.println("<div class=\"column-header clearfix\">");
					out.println("<h5 class=\"pull-right\">" + row.get(indexStart) + "</h5>");
					out.println("</div>");
					out.println("<hr>");
					out.println("<p>");
					out.println("<div class=\"form-horizontal\">");

					out.println("<div class=\"form-group\">");
					out.println(
							"<label class=\"control-label col-xs-3\" for=\"diet\"> <span class=\"required-label\" id=\"diet\"> Diet</span> :</label>");
					out.println("<div class=\"col-xs-3\">");
					out.println(row.get(indexStart + 5) == null ? "NO DATA" : row.get(indexStart + 5));
					out.println("</div>");
					out.println("</div>");
					out.println("<div class=\"form-group\">");
					out.println(
							"<label class=\"control-label col-xs-3\" for=\"laboratory\"> <span class=\"required-label\" id=\"laboratory\"> Laboratory</span> :</label>");
					out.println("<div class=\"col-xs-3\">");
					out.println("<ol>");
					for (int k = 0; k < laboratory.length; k++) {
						out.println("<li>" + laboratory[k] + "</li>");
					}
					out.println("</ol>");

					out.println("</div>");
					out.println("</div>");

					out.println("</div>");
					out.println("</p>");

					out.println("</div>");

					out.println("<div class=\"form-group\">");
					out.println("<div style=\"padding-right: 16px;\" class=\"pull-right\">");
					out.println("<a data-toggle=\"modal\" class=\"btn btn-warning btn-sm order-edit\" data-id='"
							+ row.get(indexStart + 1) + "'>edit</a>");
					out.println("<a data-toggle=\"modal\" class=\"btn btn-success btn-sm order-del\" data-id='"
							+ row.get(indexStart + 1) + "'>del</a>");
					out.println("</div>");
					out.println("</div>");

					out.println("</div>");

					i++;
				}

			} else {

				request.setAttribute("ipName", ipNo);
				request.setAttribute("token", token);
				request.setAttribute("msg", msg);
				request.getRequestDispatcher("/WEB-INF/views/gnrc-doctor-order.jsp").forward(request, response);
			}
		} catch (Exception e) {
			sendErrorReirect(request, response, "/WEB-INF/views/error.jsp", e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String ipNo = (String) session.getAttribute("ipNo") == null ? "" : (String) session.getAttribute("ipNo");
		String emr_det = request.getParameter("emrDetNo") == null ? "" : request.getParameter("emrDetNo");
		String userName = (String) session.getAttribute("user") == null ? "" : (String) session.getAttribute("user");
		String action = request.getParameter("ACTION") == null ? "" : request.getParameter("ACTION");

		String occupation = request.getParameter("DO001") == null ? "" : request.getParameter("DO001");
		String doctorName = request.getParameter("DO007") == null ? "" : request.getParameter("DO007");
		String[] DO003 = request.getParameterValues("DO003") == null ? new String[] { "NO DATA" }
				: request.getParameterValues("DO003");
		String treatment = request.getParameter("DO002") == null ? "" : request.getParameter("DO002");
		String diet = request.getParameter("DO005") == null ? "" : request.getParameter("DO005");
		String[] DO004 = request.getParameterValues("DO004") == null ? new String[] { "NO DATA" }
				: request.getParameterValues("DO004");
		String visitDoctor = request.getParameter("DO008") == null ? "" : request.getParameter("DO008");

		try {
			if ("DEL_ORDER".equals(action)) {

				System.out.println("EMD DET NO" + emr_det);
				boolean status = deleteDoctorData(emr_det);
				System.out.println("DELETE STATUS : " + status);
				out.print(status);

			} else {

				long start = System.currentTimeMillis();

				for (int i = 0; i < DO003.length; i++) {
					System.out.println(DO003[i]);
				}

				String medicine = Arrays.toString(DO003);
				String laboratory = Arrays.toString(DO004);

				System.out.println("OCCUPATION : " + occupation);
				System.out.println("DOCTOR NAME : " + doctorName);
				System.out.println("MEDICINE : " + medicine);
				System.out.println("TREATMENT : " + treatment);
				System.out.println("DIET : " + diet);
				System.out.println("LABORATORY : " + laboratory);
				System.out.println("VISITING DOCTOR : " + visitDoctor);

				keyValue.put("DO001", occupation);
				keyValue.put("DO002", treatment);
				keyValue.put("DO003", medicine);
				keyValue.put("DO004", laboratory);
				keyValue.put("DO005", diet);
				keyValue.put("DO007", doctorName);
				keyValue.put("DO008", visitDoctor);

				patient = Patient.getInstance();
				emr = Emr.getInstance();

				System.out.println("Patient Data" + patient);
				System.out.println("EMR Data " + emr);

				boolean isIpPresent = patientDao.getValidatedIp(ipNo);

				System.out.println("IS IP PRESENT : " + isIpPresent);

				String emrNo = "";

				// System.out.println("Sequence Number : " + emrSeqNum);

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
						response.sendRedirect(
								"/WEB-INF/views/gnrc-doctor-order.jsp?token=fail&msg=Something went wrong");
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
					response.sendRedirect("docorder.do?token=success&msg=Data have been added successfully");
				} else {
					response.sendRedirect("docorder.do?token=fail&msg=Something went wrong");
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
