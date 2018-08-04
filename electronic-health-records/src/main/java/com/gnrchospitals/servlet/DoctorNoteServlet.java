package com.gnrchospitals.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Enumeration;
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

@WebServlet(urlPatterns = "/docnote.do")
public class DoctorNoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Patient patient = null;
	private Emr emr = null;
	private SequenceNumberDao sequenceNumberDao = new SequenceNumberDaoImpl();
	private PatientDao patientDao = new PatientDaoImpl();
	private Map<String, String> keyValue = new HashMap<>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			String token = request.getParameter("token") == null ? "" : request.getParameter("token");
			String msg = request.getParameter("msg") == null ? "" : request.getParameter("msg");
			String ipNo = request.getParameter("ip_no") == null ? "" : request.getParameter("ip_no");
			String action = request.getParameter("ACTION") == null ? "" : request.getParameter("ACTION");
			String emrDetNo = request.getParameter("ed_no") == null ? "" : request.getParameter("ed_no");
			HttpSession session = request.getSession();

			PrintWriter out = response.getWriter();

			System.out.println("IP NO : " + ipNo);
			System.out.println("EMR DET NO : " + emrDetNo);
			System.out.println("ACTION : " + action);

			if ("FETCH_DOCTOR_NOTE_BY_ED_ID".equals(action)) {

				List<List<String>> list = patientDao.getDoctorNote(emrDetNo);
				List<String> col = list.get(0);
				List<String> row = list.get(1);
				System.out.println("Doctor list in modal " + list);

				int rowCount = row.size() / col.size();
				int i = 0;
				int indexStart = 0;

				while (i < rowCount) {

					indexStart = i * col.size();

					out.println("<div class=\"comment-head-dash clearfix\">\r\n"
							+ "  <div class=\"commenter-name-dash pull-left\">" + row.get(indexStart) + "</div>\r\n"
							+ "  <div class=\"days-dash pull-right\" id=\"now\">" + row.get(indexStart + 3)
							+ "</div>\r\n" + "				</div>");
					out.println("<p><textarea rows=\"5\" class=\"form-control input-sm updated-note\">"
							+ row.get(indexStart + 1) + "</textarea></p>");

					i++;
				}

			} else if ("FETCH_DOCTOR_NOTE".equals(action)) {

				List<List<String>> list = patientDao.getDoctorPreviousData(ipNo, "DOCTOR_PREVIOUS_NOTES");
				System.out.println("Doctor Previous Notes :" + list);

				List<String> col = list.get(0);
				List<String> row = list.get(1);
				System.out.println("Doctor list in modal " + list);

				System.out.println("DOCTORE REPORT STATUS : " + row);

				int rowCount = row.size() / col.size();
				int i = 0;
				int indexStart = 0;

				while (i < rowCount) {

					indexStart = i * col.size();

					out.println("<div class=\"comment-head-dash clearfix\">\r\n"
							+ "  <div class=\"commenter-name-dash pull-left\">" + row.get(indexStart) + "</div>\r\n"
							+ "  <div class=\"days-dash pull-right\" id=\"now\">" + row.get(indexStart + 3)
							+ "</div>\r\n" + "				</div>");
					out.println("<p><textarea rows=\"5\" class=\"form-control input-sm\" readonly>"
							+ row.get(indexStart + 1) + "</textarea></p>");
					out.println("<div class=\"modal-footer1 clearfix\">\r\n" + "					<a data-id=\""
							+ row.get(indexStart + 2) + "\" data-toggle=\"modal\"\r\n"
							+ "						class=\"btn btn-warning btn-sm doctor-edit-button pull-left\" >edit</a> <a\r\n"
							+ "						data-id=\"" + row.get(indexStart + 2)
							+ "\" data-toggle=\"modal\"\r\n"
							+ "						class=\"btn btn-primary btn-sm doctor-del-button pull-right\">del</a>\r\n"
							+ "				</div>");

					i++;
				}

			} else {

				request.setAttribute("token", token);
				request.setAttribute("msg", msg);
				request.setAttribute("ipName", (String) session.getAttribute("ipNo"));
				request.getRequestDispatcher("/WEB-INF/views/gnrc-doctor-note.jsp").forward(request, response);
			}
		} catch (Exception e) {
			sendErrorReirect(request, response, "WEB-INF/views/error.jsp", e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			PrintWriter out = response.getWriter();

			String action = request.getParameter("ACTION") == null ? "" : request.getParameter("ACTION");
			String ipNo = request.getParameter("ip_no") == null ? "" : request.getParameter("ip_no");
			String emr_det = request.getParameter("emrDetNo") == null ? "" : request.getParameter("emrDetNo");
			String note = request.getParameter("doctor_note") == null ? "" : request.getParameter("doctor_note");

			System.out.println("ACTION : " + action);
			System.out.println("IP NO : " + ipNo);
			System.out.println("NOTE : " + note);
			System.out.println("EMD DET NO" + emr_det);

			if ("UPDATE_NOTE".equals(action)) {
				boolean status = updateDoctorNote(note, emr_det);
				System.out.println("DELETE STATUS : " + status);
				out.print(status);

			} else if ("DEL_NOTE".equals(action)) {

				System.out.println("EMD DET NO" + emr_det);
				boolean status = deleteDoctorNote(emr_det);
				System.out.println("DELETE STATUS : " + status);
				out.print(status);

			} else {

				HttpSession session = request.getSession();
				Enumeration<String> parameterName = request.getParameterNames();

				String userName = (String) session.getAttribute("user");
				// String mrdNo = request.getParameter("mrd_no") == null ? "" :
				// request.getParameter("mrd_no");

				long start = System.currentTimeMillis();

				// System.out.println("USER NAME : " + userName);
				// System.out.println("MRD NO : " + mrdNo);
				// System.out.println("IP NO : " + ipNo);

				boolean isIpPresent = patientDao.getValidatedIp(ipNo);

				System.out.println("IS IP PRESENT : " + isIpPresent);

				String emrNo = "";

				// System.out.println("Sequence Number : " + emrSeqNum);

				patient = Patient.getInstance();
				System.out.println("Patient Instance ID before insertEmrClinicalData " + patient);

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
								"/WEB-INF/views/gnrc-doctor-note.jsp?token=fail&msg=EmrClinicalRecord insert failed");
					}

				} else {
					boolean emrStatus = patientDao.findEmrByIpNumber(ipNo);
					System.out.println("Find Emr status : " + emrStatus);
					emrNo = patient.getEmr().getEmrNo();
				}

				SequenceNumber emrDetSeqNum = new SequenceNumber("EMRDET", userName);

				System.out.println("EMRDET Sequence Number : " + emrDetSeqNum);

				String emrDetNo = sequenceNumberDao.getSequenceNumber(emrDetSeqNum);

				while (parameterName.hasMoreElements()) {

					String paramName = parameterName.nextElement();
					System.out.println("parameter Name " + paramName);

					boolean isValid = patientDao.validateKey(paramName);
					System.out.println("Is parameter Valid " + isValid);

					if (isValid) {
						String paramValue = request.getParameter(paramName) == null ? ""
								: request.getParameter(paramName);
						// System.out.println("parameter Value " + paramValue);
						keyValue.put(paramName, paramValue);
					}
				}

				patient = Patient.getInstance();
				System.out.println("Patient Instance ID after insertEmrClinicalData " + patient);
				emr = Emr.getInstance();
				System.out.println("EMR Instance ID after insertEmrClinicalData " + emr);
				// emr.setEmrNo(emrNo);
				emr.setCreateUser(userName);
				emr.setUpdateUser(userName);
				emr.setKeyValue(keyValue);
				emr.setEmrDetNo(emrDetNo);
				patient.setEmr(emr);

				boolean isEmrHealthDataInsert = patientDao.insertEmrHealthData(patient);

				long end = System.currentTimeMillis();

				System.out.println("Time takes to process this : " + (end - start) + " ms");

				if (isEmrHealthDataInsert) {
					response.sendRedirect("/docnote.do?token=success&msg=Data have been added successfully");
				} else {
					response.sendRedirect("/docnote.do?token=fail&msg=Something went wrong");
				}

			}

		} catch (Exception e) {
			sendErrorReirect(request, response, "WEB-INF/views/error.jsp", e);
		}

	}

	public boolean deleteDoctorNote(String emrDetNumber) throws SQLException {

		return patientDao.deleteDoctorNote(emrDetNumber);

	}

	public boolean updateDoctorNote(String note, String emrDetNumber) throws SQLException {

		return patientDao.updateDoctorNote(note, emrDetNumber);

	}
	
	protected void sendErrorReirect(HttpServletRequest request, HttpServletResponse response, String errroPageURL, Throwable e) throws ServletException, IOException{
		
		 request.setAttribute ("javax.servlet.jsp.jspException", e);
		getServletConfig().getServletContext().getRequestDispatcher(errroPageURL).forward(request, response);
		
	}

}
