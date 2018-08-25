package com.gnrchospitals.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet(urlPatterns = "/transfer.do")
public class TransferSummaryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Patient patient = null;
	private Emr emr = null;
	private SequenceNumberDao sequenceNumberDao = new SequenceNumberDaoImpl();
	private PatientDao patientDao = new PatientDaoImpl();
	private Map<String, String> keyValue = new HashMap<>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String token = request.getParameter("token") == null ? "" : request.getParameter("token");
		String msg = request.getParameter("msg") == null ? "" : request.getParameter("msg");
		String action = request.getParameter("ACTION") == null ? "" : request.getParameter("ACTION");

		try {
			request.setAttribute("msg", msg);
			request.setAttribute("token", token);
			request.setAttribute("ipName", (String) session.getAttribute("ipNo"));
			request.getRequestDispatcher("/WEB-INF/views/gnrc-transfer-sum.jsp").forward(request, response);
		} catch (Exception ex) {
			sendErrorReirect(request, response, "/WEB-INF/views/error.jsp", ex);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		// java.lang.IllegalStateException: getWriter() has already been called for this
		// response
		PrintWriter out = response.getWriter();

		String token = request.getParameter("token") == null ? "" : request.getParameter("token");
		String msg = request.getParameter("msg") == null ? "" : request.getParameter("msg");
		String ipNo = (String) session.getAttribute("ipNo") == null ? "" : (String) session.getAttribute("ipNo");
		String userName = (String) session.getAttribute("user") == null ? "" : (String) session.getAttribute("user");
		String action = request.getParameter("ACTION") == null ? "" : request.getParameter("ACTION");
		String paramType = request.getParameter("paramType") == null ? "" : request.getParameter("paramType");
		String edNo = request.getParameter("edNo") == null || request.getParameter("edNo").isEmpty() ? ""
				: request.getParameter("edNo");
		String finalDiagnosis = request.getParameter("TS001") == null || request.getParameter("TS001").isEmpty() ? ""
				: keyValue.put("TS001", request.getParameter("TS001"));
		String transferReport = request.getParameter("TS002") == null || request.getParameter("TS002").isEmpty() ? ""
				: keyValue.put("TS002", request.getParameter("TS002"));
		String transferDateTime = request.getParameter("TS003") == null || request.getParameter("TS003").isEmpty() ? ""
				: keyValue.put("TS003", request.getParameter("TS003"));
		String causeTransfer = request.getParameter("TS004") == null || request.getParameter("TS004").isEmpty() ? ""
				: keyValue.put("TS004", request.getParameter("TS004"));
		String transferDoctorName = request.getParameter("TS005") == null || request.getParameter("TS005").isEmpty()
				? ""
				: keyValue.put("TS005", request.getParameter("TS005"));
		String pulse = request.getParameter("TS006") == null || request.getParameter("TS006").isEmpty() ? ""
				: keyValue.put("TS006", request.getParameter("TS006"));
		String temp = request.getParameter("TS007") == null || request.getParameter("TS007").isEmpty() ? ""
				: keyValue.put("TS007", request.getParameter("TS007"));
		String itbNitb = request.getParameter("TS008") == null || request.getParameter("TS008").isEmpty() ? ""
				: keyValue.put("TS008", request.getParameter("TS008"));
		String chest = request.getParameter("TS009") == null || request.getParameter("TS009").isEmpty() ? ""
				: keyValue.put("TS009", request.getParameter("TS009"));
		String uCath = request.getParameter("TS010") == null || request.getParameter("TS010").isEmpty() ? ""
				: keyValue.put("TS010", request.getParameter("TS010"));
		String bloodPressure = request.getParameter("TS011") == null || request.getParameter("TS011").isEmpty() ? ""
				: keyValue.put("TS011", request.getParameter("TS011"));
		String rRate = request.getParameter("TS012") == null || request.getParameter("TS012").isEmpty() ? ""
				: keyValue.put("TS012", request.getParameter("TS012"));
		String vNv = request.getParameter("TS013") == null || request.getParameter("TS013").isEmpty() ? ""
				: keyValue.put("TS013", request.getParameter("TS013"));
		String cvs = request.getParameter("TS014") == null || request.getParameter("TS014").isEmpty() ? ""
				: keyValue.put("TS014", request.getParameter("TS014"));
		String gcs = request.getParameter("TS015") == null || request.getParameter("TS015").isEmpty() ? ""
				: keyValue.put("TS015", request.getParameter("TS015"));
		String ipm = request.getParameter("TS016") == null || request.getParameter("TS016").isEmpty() ? ""
				: keyValue.put("TS016", request.getParameter("TS016"));
		String[] TS017 = request.getParameterValues("TS017");

		// System.out.println("Array is : " + TS017[0]);

		if (TS017 != null) {
			for (String value : TS017) {
				if (!value.equals("")) {
					String amp = Arrays.toString(TS017).replace(",", "-");
					keyValue.put("TS017", amp);
				}
			}
		}

		try {

			long start = System.currentTimeMillis();

			System.out.println("finalDiagnosis : " + finalDiagnosis);
			System.out.println("transferReport : " + transferReport);
			System.out.println("transferDateTime : " + transferDateTime);
			System.out.println("causeTransfer : " + causeTransfer);
			System.out.println("transferDoctorName : " + transferDoctorName);
			System.out.println("pulse : " + pulse);
			System.out.println("temp : " + temp);
			System.out.println("itbNitb : " + itbNitb);
			System.out.println("chest : " + chest);
			System.out.println("uCath : " + uCath);
			System.out.println("bloodPressure : " + bloodPressure);
			System.out.println("rRate : " + rRate);
			System.out.println("vNv : " + vNv);
			System.out.println("cvs : " + cvs);
			System.out.println("gcs : " + gcs);
			System.out.println("ipm : " + ipm);
			System.out.println("ED No : " + edNo);

			if ("GET_PREV_ED_NO".equals(action)) {

				System.out.println("In if part");
				List<String> list = patientDao.getPreviousRecordNo(paramType);
				System.out.println("KEY VALUE" + list);
				out.println(list);
			} else if ("GET_PREV_TRANSFER_RECORD".equals(action)) {

				System.out.println("In if part");
				Map<String, String> map = patientDao.getPreviousData(edNo, paramType);
				System.out.println("KEY VALUE" + map);
				out.println(map);
			} else {

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
								"/WEB-INF/views/gnrc-transfer-sum.jsp?token=fail&msg=Something went wrong");
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
					response.sendRedirect("/transfer.do?token=success&msg=Data have been added successfully");
				} else {
					response.sendRedirect("/transfer.do?token=fail&msg=Something went wrong");
				}

			}

		} catch (Exception ex) {
			sendErrorReirect(request, response, "/WEB-INF/views/error.jsp", ex);
		}

	}

	protected void sendErrorReirect(HttpServletRequest request, HttpServletResponse response, String errroPageURL,
			Throwable e) throws ServletException, IOException {
		request.setAttribute("javax.servlet.jsp.jspException", e);
		getServletConfig().getServletContext().getRequestDispatcher(errroPageURL).forward(request, response);

	}

}
