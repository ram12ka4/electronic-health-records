package com.gnrchospitals.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

@WebServlet(urlPatterns = "/invest.do")
public class InvestigationSheetServlet extends HttpServlet {

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
			request.getRequestDispatcher("/WEB-INF/views/gnrc-invst-sheet.jsp").forward(request, response);

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

		String totalCount = request.getParameter("IS001") == null ? ""
				: keyValue.put("IS001", request.getParameter("IS001"));
		String leucocyteCount = request.getParameter("IS002") == null ? ""
				: keyValue.put("IS002", request.getParameter("IS002"));
		String neutrophils = request.getParameter("IS003") == null ? ""
				: keyValue.put("IS003", request.getParameter("IS003"));
		String lymphocytes = request.getParameter("IS004") == null ? ""
				: keyValue.put("IS004", request.getParameter("IS004"));
		String eosinophils = request.getParameter("IS005") == null ? ""
				: keyValue.put("IS005", request.getParameter("IS005"));
		String monocytes = request.getParameter("IS006") == null ? ""
				: keyValue.put("IS006", request.getParameter("IS006"));
		String hb = request.getParameter("IS007") == null ? "" : keyValue.put("IS007", request.getParameter("IS007"));
		String pcv = request.getParameter("IS008") == null ? "" : keyValue.put("IS008", request.getParameter("IS008"));
		String esr = request.getParameter("IS009") == null ? "" : keyValue.put("IS009", request.getParameter("IS009"));
		String inr = request.getParameter("IS010") == null ? "" : keyValue.put("IS010", request.getParameter("IS010"));
		String prothrombinTimeTest = request.getParameter("IS011") == null ? ""
				: keyValue.put("IS011", request.getParameter("IS011"));
		String prothrombinTimeControl = request.getParameter("IS065") == null ? ""
				: keyValue.put("IS065", request.getParameter("IS065"));
		String totalPletelesCount = request.getParameter("IS012") == null ? ""
				: keyValue.put("IS012", request.getParameter("IS012"));
		String mp = request.getParameter("IS013") == null ? "" : keyValue.put("IS013", request.getParameter("IS013"));
		String parasiteLoad = request.getParameter("IS014") == null ? ""
				: keyValue.put("IS014", request.getParameter("IS014"));
		String reticulocyteCount = request.getParameter("IS015") == null ? ""
				: keyValue.put("IS015", request.getParameter("IS015"));
		String bloodGroup = request.getParameter("IS016") == null ? ""
				: keyValue.put("IS016", request.getParameter("IS016"));
		String protein = request.getParameter("IS017") == null ? ""
				: keyValue.put("IS017", request.getParameter("IS017"));
		String albumin = request.getParameter("IS018") == null ? ""
				: keyValue.put("IS018", request.getParameter("IS018"));
		String sgot = request.getParameter("IS019") == null ? "" : keyValue.put("IS019", request.getParameter("IS019"));
		String sgpt = request.getParameter("IS020") == null ? "" : keyValue.put("IS020", request.getParameter("IS020"));
		String alkalinePhosphatase = request.getParameter("IS021") == null ? ""
				: keyValue.put("IS021", request.getParameter("IS021"));
		String BillirubinDirect = request.getParameter("IS022") == null ? ""
				: keyValue.put("IS022", request.getParameter("IS022"));
		String BillirubinIndirect = request.getParameter("IS066") == null ? ""
				: keyValue.put("IS066", request.getParameter("IS066"));
		String urea = request.getParameter("IS023") == null ? "" : keyValue.put("IS023", request.getParameter("IS023"));
		String creatinine = request.getParameter("IS024") == null ? ""
				: keyValue.put("IS024", request.getParameter("IS024"));
		String sugar = request.getParameter("IS025") == null ? ""
				: keyValue.put("IS025", request.getParameter("IS025"));
		String na = request.getParameter("IS026") == null ? "" : keyValue.put("IS026", request.getParameter("IS026"));
		String k = request.getParameter("IS027") == null ? "" : keyValue.put("IS027", request.getParameter("IS027"));
		String ca = request.getParameter("IS028") == null ? "" : keyValue.put("IS028", request.getParameter("IS028"));
		String mg = request.getParameter("IS029") == null ? "" : keyValue.put("IS029", request.getParameter("IS029"));
		String nh = request.getParameter("IS030") == null ? "" : keyValue.put("IS030", request.getParameter("IS030"));
		String lipidProfile = request.getParameter("IS031") == null ? ""
				: keyValue.put("IS031", request.getParameter("IS031"));
		String totalCholesterol = request.getParameter("IS032") == null ? ""
				: keyValue.put("IS032", request.getParameter("IS032"));
		String triglyceride = request.getParameter("IS033") == null ? ""
				: keyValue.put("IS033", request.getParameter("IS033"));
		String ldh = request.getParameter("IS034") == null ? "" : keyValue.put("IS034", request.getParameter("IS034"));
		String ldl = request.getParameter("IS035") == null ? "" : keyValue.put("IS035", request.getParameter("IS035"));
		String hdl = request.getParameter("IS036") == null ? "" : keyValue.put("IS036", request.getParameter("IS036"));
		String vldl = request.getParameter("IS037") == null ? "" : keyValue.put("IS037", request.getParameter("IS037"));
		String amylase = request.getParameter("IS038") == null ? ""
				: keyValue.put("IS038", request.getParameter("IS038"));
		String lipase = request.getParameter("IS039") == null ? ""
				: keyValue.put("IS039", request.getParameter("IS039"));
		String CPK = request.getParameter("IS040") == null ? "" : keyValue.put("IS040", request.getParameter("IS040"));
		String crp = request.getParameter("IS041") == null ? "" : keyValue.put("IS041", request.getParameter("IS041"));
		String ckmb = request.getParameter("IS042") == null ? "" : keyValue.put("IS042", request.getParameter("IS042"));
		String trcpT = request.getParameter("IS043") == null ? ""
				: keyValue.put("IS043", request.getParameter("IS043"));
		String procalcitonin = request.getParameter("IS044") == null ? ""
				: keyValue.put("IS044", request.getParameter("IS044"));
		String t3 = request.getParameter("IS045") == null ? "" : keyValue.put("IS045", request.getParameter("IS045"));
		String t4 = request.getParameter("IS046") == null ? "" : keyValue.put("IS046", request.getParameter("IS046"));
		String TSH = request.getParameter("IS047") == null ? "" : keyValue.put("IS047", request.getParameter("IS047"));
		String urine = request.getParameter("IS048") == null ? ""
				: keyValue.put("IS048", request.getParameter("IS048"));
		String stool = request.getParameter("IS049") == null ? ""
				: keyValue.put("IS049", request.getParameter("IS049"));
		String sputum = request.getParameter("IS050") == null ? ""
				: keyValue.put("IS050", request.getParameter("IS050"));
		String hbsag = request.getParameter("IS051") == null ? ""
				: keyValue.put("IS051", request.getParameter("IS051"));
		String hiv = request.getParameter("IS052") == null ? "" : keyValue.put("IS052", request.getParameter("IS052"));
		String hcv = request.getParameter("IS053") == null ? "" : keyValue.put("IS053", request.getParameter("IS053"));
		String hav = request.getParameter("IS054") == null ? "" : keyValue.put("IS054", request.getParameter("IS054"));
		String hev = request.getParameter("IS055") == null ? "" : keyValue.put("IS055", request.getParameter("IS055"));
		String widal = request.getParameter("IS056") == null ? ""
				: keyValue.put("IS056", request.getParameter("IS056"));
		String vdrl = request.getParameter("IS057") == null ? "" : keyValue.put("IS057", request.getParameter("IS057"));
		String hsv = request.getParameter("IS058") == null ? "" : keyValue.put("IS058", request.getParameter("IS058"));
		String je = request.getParameter("IS059") == null ? "" : keyValue.put("IS059", request.getParameter("IS059"));
		String chestXRay = request.getParameter("IS061") == null ? ""
				: keyValue.put("IS061", request.getParameter("IS061"));
		String CTScan = request.getParameter("IS062") == null ? ""
				: keyValue.put("IS062", request.getParameter("IS062"));
		String mri = request.getParameter("IS063") == null ? "" : keyValue.put("IS063", request.getParameter("IS063"));
		String others = request.getParameter("IS064") == null ? ""
				: keyValue.put("IS064", request.getParameter("IS064"));
		String paramList = request.getParameter("paramList") == null ? "" : request.getParameter("paramList");

		List<String> list = new ArrayList<>();

		System.out.println("Parameter List : " + paramList);
		String paramValue[] = paramList.replace("[", "").replace("]", "").replace("\"", "").split(",");

		/*
		 * for (String paramName : paramValue) { System.out.println("param value " +
		 * paramName); }
		 */

		/*
		 * BufferedReader br = new BufferedReader(new
		 * InputStreamReader(request.getInputStream())); String [] json = null; if (br
		 * != null) { json = br.readLine().replace("\"", "").split("&");
		 * System.out.println("JSON DATA " + json[0] + " " + json[1]); paramType =
		 * json[1]; action = json[0];
		 * 
		 * }
		 */

		System.out.println("PARAM TYPE " + paramType);
		System.out.println("ACTION " + action);

		try {

			if ("FETCH_PARAM_NAME".equals(action.trim())) {
				System.out.println("In INSERT_PARAM_VALUE");
				List<List<String>> paramNamelist = patientDao.fetchGobalTempData(ipNo, paramValue);
				List<String> col = paramNamelist.get(0);
				List<String> row = paramNamelist.get(1);

				System.out.println("Column name size : " + paramNamelist.get(0).size());
				System.out.println("Row value size : " + paramNamelist.get(1).size());
				System.out.println("Row value : " + paramNamelist.get(1));

				// ObjectMapper mapper = new ObjectMapper();
				// response.setContentType("application/json");
				// String json1 = mapper.writeValueAsString(keyValue);
				// System.out.println("JSON DATA" + json);

				int colCount = row.size() / col.size();
				System.out.println("Column Count : " +colCount);
				int j = 2;
				int index = 2;
				int count = 0;

				out.println(
						"<table id=\"fixedColumnExample\" class=\"stripe row-border order-column\" style=\"width:100%\">");
				out.println("<thead>");
				out.println("<tr>");
				out.println("<th>Parameter</th>");
				for (int i = 0; i < row.size(); i += col.size()) {
					out.println("<th>" + row.get(i) + "</th>");
				}
				out.println("</tr>");
				out.println("</thead>");
				out.println("<tbody>");
				// problem in this section

				while (j < col.size()) {
					
					out.println("<tr>");
					
					out.println("<td>" + col.get(j) + "</td>");
					
					for (int i = 0; i < row.size(); i += col.size()) {
						System.out.println("Count : " + count++);
						out.println("<td>" + row.get(i + index) + "</td>");
					}
					
					out.println("</tr>");
					index++;
					j++;
				}

				// j++;

				out.println("</tbody>");

				out.println("</table>");

				// out.println(paramNamelist);
			} else if ("GET_BLOOD_GRP_DATA".equals(action.trim())) {
				System.out.println("In if part");
				list = patientDao.getParameterList(paramType);
				System.out.println("KEY VALUE" + list);
				// ObjectMapper mapper = new ObjectMapper();
				// response.setContentType("application/json");
				// String json1 = mapper.writeValueAsString(keyValue);
				// System.out.println("JSON DATA" + json);
				out.println(list);
			} else {

				long start = System.currentTimeMillis();

				System.out.println("TOTAL COUNT : " + totalCount);
				System.out.println("LEUCOCYTE COUNT : " + leucocyteCount);
				System.out.println("NEUTROPHILS : " + neutrophils);
				System.out.println("lymphocytes : " + lymphocytes);
				System.out.println("eosinophils : " + eosinophils);
				System.out.println("monocytes : " + monocytes);
				System.out.println("hb : " + hb);
				System.out.println("pcv : " + pcv);
				System.out.println("esr : " + esr);
				System.out.println("inr : " + inr);

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
					response.sendRedirect("/invest.do?token=success&msg=Data have been added successfully");
				} else {
					response.sendRedirect("/invest.do?token=fail&msg=Something went wrong");
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
