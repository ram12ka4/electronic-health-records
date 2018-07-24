package com.gnrchospitals;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gnrchospitals.dao.EmrClinicalDao;
import com.gnrchospitals.dao.EmrHealthDao;
import com.gnrchospitals.dao.SequenceNumberDao;
import com.gnrchospitals.daoimpl.EmrClinicalDaoImpl;
import com.gnrchospitals.daoimpl.EmrHealthDaoImpl;
import com.gnrchospitals.daoimpl.SequenceNumberDaoImpl;
import com.gnrchospitals.dto.EmrClinical;
import com.gnrchospitals.dto.EmrHealth;
import com.gnrchospitals.dto.SequenceNumber;

@WebServlet(urlPatterns = "/docnote.do")
public class DoctorNoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// private static List<EmrHealth> dataList = new ArrayList<>();
	private EmrHealth emrHealth = null;
	private EmrClinical emrClinical = null;
	private SequenceNumberDao sequenceNumberDao = new SequenceNumberDaoImpl();
	private EmrHealthDao emrHealthDao = new EmrHealthDaoImpl();
	private EmrClinicalDao clinicalDao = new EmrClinicalDaoImpl();
	private Map<String, String> keyValue = new HashMap<>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute("token", request.getParameter("token"));
		request.setAttribute("msg", request.getParameter("msg"));
		request.setAttribute("ipName", request.getParameter("ip_no"));
		request.getRequestDispatcher("/WEB-INF/views/gnrc-doctor-note.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("user");
		String mrdNo = request.getParameter("mrd_no") == null ? "" : request.getParameter("mrd_no");
		String ipNo = request.getParameter("ip_no") == null ? "" : request.getParameter("ip_no");
		Enumeration<String> parameterName = request.getParameterNames();

		long start = System.currentTimeMillis();

		System.out.println("USER NAME : " + userName);
		System.out.println("MRD NO : " + mrdNo);
		System.out.println("IP NO : " + ipNo);

		boolean isIpPresent = clinicalDao.findByIpNum(ipNo);

		System.out.println("IS IP PRESENT : " + isIpPresent);

		SequenceNumber emrSeqNum = new SequenceNumber("EMR", userName);

		System.out.println("Sequence Number :  " + emrSeqNum);

		String emrNo = sequenceNumberDao.getSequenceNumber(emrSeqNum);

		if (!isIpPresent) {

			emrClinical = new EmrClinical(mrdNo, emrNo, ipNo, "VT01", "1", userName, userName);

			boolean isEmrClinicalDataInsert = clinicalDao.insert(emrClinical);

			if (!isEmrClinicalDataInsert) {
				request.getRequestDispatcher(
						"/WEB-INF/views/gnrc-doctor-note.jsp?token=fail&msg=EmrClinicalRecord insert failed")
						.forward(request, response);
			}

		}

		SequenceNumber emrDetSeqNum = new SequenceNumber("EMRDET", userName);

		System.out.println("Sequence Number :  " + emrDetSeqNum);

		String emrDetNo = sequenceNumberDao.getSequenceNumber(emrDetSeqNum);

		while (parameterName.hasMoreElements()) {

			String paramName = parameterName.nextElement();
			System.out.println("parameter Name " + paramName);

			boolean isValid = emrHealthDao.validateKey(paramName);

			if (isValid) {
				String paramValue = request.getParameter(paramName) == null ? "" : request.getParameter(paramName);
				System.out.println("parameter Value " + paramValue);
				keyValue.put(paramName, paramValue);
			}
		}

		emrHealth = new EmrHealth(emrNo, emrDetNo, userName, userName, keyValue);

		boolean isEmrHealthDataInsert = emrHealthDao.insert(emrHealth);

		long end = System.currentTimeMillis();

		System.out.println("Time takes to process this : " + (end - start) + " ms");

		if (isEmrHealthDataInsert) {
			request.getRequestDispatcher(
					"/WEB-INF/views/gnrc-doctor-note.jsp?token=success&msg=EmrHealthData insert success")
					.forward(request, response);
		} else {
			request.getRequestDispatcher("/WEB-INF/views/gnrc-doctor-note.jsp?token=fail&msg=EmrHealthData insert fail")
					.forward(request, response);
		}

	}

}
