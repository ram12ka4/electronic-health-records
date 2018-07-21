package com.gnrchospitals;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gnrchospitals.dao.EmrHealthDao;
import com.gnrchospitals.dao.SequenceNumberDao;
import com.gnrchospitals.dao.EmrClinicalDao;
import com.gnrchospitals.daoimpl.EmrHealthDaoImpl;
import com.gnrchospitals.daoimpl.EmrClinicalDaoImpl;
import com.gnrchospitals.daoimpl.SequenceNumberDaoImpl;
import com.gnrchospitals.dto.EmrHealth;
import com.gnrchospitals.dto.SequenceNumber;
import com.gnrchospitals.dto.EmrClinical;

import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/docnote.do")
public class DoctorNoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static List<EmrHealth> dataList = new ArrayList<>();
	private EmrHealth emrHealth = null;
	private EmrClinical emrClinical = new EmrClinical();
	private SequenceNumberDao sequenceNumberDao = new SequenceNumberDaoImpl();
	private EmrHealthDao emrHealthDao = new EmrHealthDaoImpl();
	private EmrClinicalDao clinicalDao = new EmrClinicalDaoImpl();
	private Map<String, String> keyValue = new HashMap<>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/WEB-INF/views/gnrc-doctor-note.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("user");
		String mrdNo = request.getParameter("mrd_no") == null ? "" : request.getParameter("mrd_no");
		String ipNo = request.getParameter("ip_no") == null ? "" : request.getParameter("ip_no");
		Enumeration<String> parameterName = request.getParameterNames();

		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
		Date today = new Date();
		String currentDate = sdf.format(today);

		boolean isIpPresent = clinicalDao.findByIpNum(ipNo);

		if (!isIpPresent) {
				
		}

		SequenceNumber emrSequenceNumber = new SequenceNumber("EMR", userName);
		String emrNo = sequenceNumberDao.getSequenceNumber();

		SequenceNumber emrDetsequenceNumber = new SequenceNumber("EMRDET", userName);
		String emrDetNo = sequenceNumberDao.getSequenceNumber();

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

		emrHealth = new EmrHealth(emrNo, emrDetNo, userName, currentDate, userName, currentDate, keyValue);

		boolean isDataInsert = emrHealthDao.insert(emrHealth);
		
		if (isDataInsert) {
			request.getRequestDispatcher("/WEB-INF/views/gnrc-doctor-note.jsp").forward(request, response);
		}

		

	}

}
