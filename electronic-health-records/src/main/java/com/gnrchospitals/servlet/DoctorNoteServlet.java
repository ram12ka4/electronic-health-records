package com.gnrchospitals.servlet;

import java.io.IOException;
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

		String token = request.getParameter("token") == null ? "" : request.getParameter("token");
		String msg = request.getParameter("msg") == null ? "" : request.getParameter("msg");
		String ipNo = request.getParameter("ip_no") == null ? "" : request.getParameter("ip_no");

		List<List<String>> list = patientDao.getDoctorPreviousNote(ipNo);
		System.out.println("Doctor Previous Notes :" + list);
		
		/*
		 * System.out.println("TOKEN : " + token); System.out.println("MSG : " + msg);
		 */
		request.setAttribute("token", token);
		request.setAttribute("msg", msg);
		request.setAttribute("ipName", ipNo);
		request.setAttribute("doctorNotes", list);
		request.getRequestDispatcher("/WEB-INF/views/gnrc-doctor-note.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Enumeration<String> parameterName = request.getParameterNames();

		String userName = (String) session.getAttribute("user");
		// String mrdNo = request.getParameter("mrd_no") == null ? "" :
		// request.getParameter("mrd_no");
		String ipNo = request.getParameter("ip_no") == null ? "" : request.getParameter("ip_no");

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
				String paramValue = request.getParameter(paramName) == null ? "" : request.getParameter(paramName);
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

}
