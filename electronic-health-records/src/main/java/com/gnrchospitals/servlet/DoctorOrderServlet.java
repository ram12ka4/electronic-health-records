package com.gnrchospitals.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
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
			String token = request.getParameter("token") == null ? "" : request.getParameter("token");
			String msg = request.getParameter("msg") == null ? "" : request.getParameter("msg");
			String ipNo = (String) session.getAttribute("ipNo") == null ? "" : (String) session.getAttribute("ipNo");
			String action = request.getParameter("ACTION") == null ? "" : request.getParameter("ACTION");

			request.setAttribute("ipName", ipNo);
			request.setAttribute("token", token);
			request.setAttribute("msg", msg);
			request.getRequestDispatcher("/WEB-INF/views/gnrc-doctor-order.jsp").forward(request, response);
		} catch (Exception e) {
			sendErrorReirect(request, response, "/WEB-INF/views/error.jsp", e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {

			HttpSession session = request.getSession();
			String ipNo = (String) session.getAttribute("ipNo") == null ? "" : (String) session.getAttribute("ipNo");
			String userName = (String) session.getAttribute("user") == null ? ""
					: (String) session.getAttribute("user");
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

			long start = System.currentTimeMillis();

			for (int i=0; i<DO003.length; i++) {
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
					response.sendRedirect("/WEB-INF/views/gnrc-doctor-order.jsp?token=fail&msg=Something went wrong");
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
				response.sendRedirect("/docorder.do?token=success&msg=Data have been added successfully");
			} else {
				response.sendRedirect("/docorder.do?token=fail&msg=Something went wrong");
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

}
