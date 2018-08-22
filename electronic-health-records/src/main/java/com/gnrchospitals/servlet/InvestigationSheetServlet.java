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

			/*	out.println("<table id=\"fixedColumnExample\" class=\"stripe row-border order-column\" style=\"width:100%\">");
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

				out.println("</table>");*/
				
				out.println(" <table id=\"fixedColumnExample\" class=\"stripe row-border order-column\" style=\"width:100%\">\r\n" + 
						"        <thead>\r\n" + 
						"            <tr>\r\n" + 
						"                <th>First name</th>\r\n" + 
						"                <th>Last name</th>\r\n" + 
						"                <th>Position</th>\r\n" + 
						"                <th>Office</th>\r\n" + 
						"                <th>Age</th>\r\n" + 
						"                <th>Start date</th>\r\n" + 
						"                <th>Salary</th>\r\n" + 
						"                <th>Extn.</th>\r\n" + 
						"                <th>E-mail</th>\r\n" + 
						"            </tr>\r\n" + 
						"        </thead>\r\n" + 
						"        <tbody>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Tiger</td>\r\n" + 
						"                <td>Nixon</td>\r\n" + 
						"                <td>System Architect</td>\r\n" + 
						"                <td>Edinburgh</td>\r\n" + 
						"                <td>61</td>\r\n" + 
						"                <td>2011/04/25</td>\r\n" + 
						"                <td>$320,800</td>\r\n" + 
						"                <td>5421</td>\r\n" + 
						"                <td>t.nixon@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Garrett</td>\r\n" + 
						"                <td>Winters</td>\r\n" + 
						"                <td>Accountant</td>\r\n" + 
						"                <td>Tokyo</td>\r\n" + 
						"                <td>63</td>\r\n" + 
						"                <td>2011/07/25</td>\r\n" + 
						"                <td>$170,750</td>\r\n" + 
						"                <td>8422</td>\r\n" + 
						"                <td>g.winters@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Ashton</td>\r\n" + 
						"                <td>Cox</td>\r\n" + 
						"                <td>Junior Technical Author</td>\r\n" + 
						"                <td>San Francisco</td>\r\n" + 
						"                <td>66</td>\r\n" + 
						"                <td>2009/01/12</td>\r\n" + 
						"                <td>$86,000</td>\r\n" + 
						"                <td>1562</td>\r\n" + 
						"                <td>a.cox@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Cedric</td>\r\n" + 
						"                <td>Kelly</td>\r\n" + 
						"                <td>Senior Javascript Developer</td>\r\n" + 
						"                <td>Edinburgh</td>\r\n" + 
						"                <td>22</td>\r\n" + 
						"                <td>2012/03/29</td>\r\n" + 
						"                <td>$433,060</td>\r\n" + 
						"                <td>6224</td>\r\n" + 
						"                <td>c.kelly@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Airi</td>\r\n" + 
						"                <td>Satou</td>\r\n" + 
						"                <td>Accountant</td>\r\n" + 
						"                <td>Tokyo</td>\r\n" + 
						"                <td>33</td>\r\n" + 
						"                <td>2008/11/28</td>\r\n" + 
						"                <td>$162,700</td>\r\n" + 
						"                <td>5407</td>\r\n" + 
						"                <td>a.satou@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Brielle</td>\r\n" + 
						"                <td>Williamson</td>\r\n" + 
						"                <td>Integration Specialist</td>\r\n" + 
						"                <td>New York</td>\r\n" + 
						"                <td>61</td>\r\n" + 
						"                <td>2012/12/02</td>\r\n" + 
						"                <td>$372,000</td>\r\n" + 
						"                <td>4804</td>\r\n" + 
						"                <td>b.williamson@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Herrod</td>\r\n" + 
						"                <td>Chandler</td>\r\n" + 
						"                <td>Sales Assistant</td>\r\n" + 
						"                <td>San Francisco</td>\r\n" + 
						"                <td>59</td>\r\n" + 
						"                <td>2012/08/06</td>\r\n" + 
						"                <td>$137,500</td>\r\n" + 
						"                <td>9608</td>\r\n" + 
						"                <td>h.chandler@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Rhona</td>\r\n" + 
						"                <td>Davidson</td>\r\n" + 
						"                <td>Integration Specialist</td>\r\n" + 
						"                <td>Tokyo</td>\r\n" + 
						"                <td>55</td>\r\n" + 
						"                <td>2010/10/14</td>\r\n" + 
						"                <td>$327,900</td>\r\n" + 
						"                <td>6200</td>\r\n" + 
						"                <td>r.davidson@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Colleen</td>\r\n" + 
						"                <td>Hurst</td>\r\n" + 
						"                <td>Javascript Developer</td>\r\n" + 
						"                <td>San Francisco</td>\r\n" + 
						"                <td>39</td>\r\n" + 
						"                <td>2009/09/15</td>\r\n" + 
						"                <td>$205,500</td>\r\n" + 
						"                <td>2360</td>\r\n" + 
						"                <td>c.hurst@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Sonya</td>\r\n" + 
						"                <td>Frost</td>\r\n" + 
						"                <td>Software Engineer</td>\r\n" + 
						"                <td>Edinburgh</td>\r\n" + 
						"                <td>23</td>\r\n" + 
						"                <td>2008/12/13</td>\r\n" + 
						"                <td>$103,600</td>\r\n" + 
						"                <td>1667</td>\r\n" + 
						"                <td>s.frost@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Jena</td>\r\n" + 
						"                <td>Gaines</td>\r\n" + 
						"                <td>Office Manager</td>\r\n" + 
						"                <td>London</td>\r\n" + 
						"                <td>30</td>\r\n" + 
						"                <td>2008/12/19</td>\r\n" + 
						"                <td>$90,560</td>\r\n" + 
						"                <td>3814</td>\r\n" + 
						"                <td>j.gaines@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Quinn</td>\r\n" + 
						"                <td>Flynn</td>\r\n" + 
						"                <td>Support Lead</td>\r\n" + 
						"                <td>Edinburgh</td>\r\n" + 
						"                <td>22</td>\r\n" + 
						"                <td>2013/03/03</td>\r\n" + 
						"                <td>$342,000</td>\r\n" + 
						"                <td>9497</td>\r\n" + 
						"                <td>q.flynn@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Charde</td>\r\n" + 
						"                <td>Marshall</td>\r\n" + 
						"                <td>Regional Director</td>\r\n" + 
						"                <td>San Francisco</td>\r\n" + 
						"                <td>36</td>\r\n" + 
						"                <td>2008/10/16</td>\r\n" + 
						"                <td>$470,600</td>\r\n" + 
						"                <td>6741</td>\r\n" + 
						"                <td>c.marshall@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Haley</td>\r\n" + 
						"                <td>Kennedy</td>\r\n" + 
						"                <td>Senior Marketing Designer</td>\r\n" + 
						"                <td>London</td>\r\n" + 
						"                <td>43</td>\r\n" + 
						"                <td>2012/12/18</td>\r\n" + 
						"                <td>$313,500</td>\r\n" + 
						"                <td>3597</td>\r\n" + 
						"                <td>h.kennedy@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Tatyana</td>\r\n" + 
						"                <td>Fitzpatrick</td>\r\n" + 
						"                <td>Regional Director</td>\r\n" + 
						"                <td>London</td>\r\n" + 
						"                <td>19</td>\r\n" + 
						"                <td>2010/03/17</td>\r\n" + 
						"                <td>$385,750</td>\r\n" + 
						"                <td>1965</td>\r\n" + 
						"                <td>t.fitzpatrick@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Michael</td>\r\n" + 
						"                <td>Silva</td>\r\n" + 
						"                <td>Marketing Designer</td>\r\n" + 
						"                <td>London</td>\r\n" + 
						"                <td>66</td>\r\n" + 
						"                <td>2012/11/27</td>\r\n" + 
						"                <td>$198,500</td>\r\n" + 
						"                <td>1581</td>\r\n" + 
						"                <td>m.silva@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Paul</td>\r\n" + 
						"                <td>Byrd</td>\r\n" + 
						"                <td>Chief Financial Officer (CFO)</td>\r\n" + 
						"                <td>New York</td>\r\n" + 
						"                <td>64</td>\r\n" + 
						"                <td>2010/06/09</td>\r\n" + 
						"                <td>$725,000</td>\r\n" + 
						"                <td>3059</td>\r\n" + 
						"                <td>p.byrd@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Gloria</td>\r\n" + 
						"                <td>Little</td>\r\n" + 
						"                <td>Systems Administrator</td>\r\n" + 
						"                <td>New York</td>\r\n" + 
						"                <td>59</td>\r\n" + 
						"                <td>2009/04/10</td>\r\n" + 
						"                <td>$237,500</td>\r\n" + 
						"                <td>1721</td>\r\n" + 
						"                <td>g.little@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Bradley</td>\r\n" + 
						"                <td>Greer</td>\r\n" + 
						"                <td>Software Engineer</td>\r\n" + 
						"                <td>London</td>\r\n" + 
						"                <td>41</td>\r\n" + 
						"                <td>2012/10/13</td>\r\n" + 
						"                <td>$132,000</td>\r\n" + 
						"                <td>2558</td>\r\n" + 
						"                <td>b.greer@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Dai</td>\r\n" + 
						"                <td>Rios</td>\r\n" + 
						"                <td>Personnel Lead</td>\r\n" + 
						"                <td>Edinburgh</td>\r\n" + 
						"                <td>35</td>\r\n" + 
						"                <td>2012/09/26</td>\r\n" + 
						"                <td>$217,500</td>\r\n" + 
						"                <td>2290</td>\r\n" + 
						"                <td>d.rios@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Jenette</td>\r\n" + 
						"                <td>Caldwell</td>\r\n" + 
						"                <td>Development Lead</td>\r\n" + 
						"                <td>New York</td>\r\n" + 
						"                <td>30</td>\r\n" + 
						"                <td>2011/09/03</td>\r\n" + 
						"                <td>$345,000</td>\r\n" + 
						"                <td>1937</td>\r\n" + 
						"                <td>j.caldwell@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Yuri</td>\r\n" + 
						"                <td>Berry</td>\r\n" + 
						"                <td>Chief Marketing Officer (CMO)</td>\r\n" + 
						"                <td>New York</td>\r\n" + 
						"                <td>40</td>\r\n" + 
						"                <td>2009/06/25</td>\r\n" + 
						"                <td>$675,000</td>\r\n" + 
						"                <td>6154</td>\r\n" + 
						"                <td>y.berry@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Caesar</td>\r\n" + 
						"                <td>Vance</td>\r\n" + 
						"                <td>Pre-Sales Support</td>\r\n" + 
						"                <td>New York</td>\r\n" + 
						"                <td>21</td>\r\n" + 
						"                <td>2011/12/12</td>\r\n" + 
						"                <td>$106,450</td>\r\n" + 
						"                <td>8330</td>\r\n" + 
						"                <td>c.vance@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Doris</td>\r\n" + 
						"                <td>Wilder</td>\r\n" + 
						"                <td>Sales Assistant</td>\r\n" + 
						"                <td>Sidney</td>\r\n" + 
						"                <td>23</td>\r\n" + 
						"                <td>2010/09/20</td>\r\n" + 
						"                <td>$85,600</td>\r\n" + 
						"                <td>3023</td>\r\n" + 
						"                <td>d.wilder@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Angelica</td>\r\n" + 
						"                <td>Ramos</td>\r\n" + 
						"                <td>Chief Executive Officer (CEO)</td>\r\n" + 
						"                <td>London</td>\r\n" + 
						"                <td>47</td>\r\n" + 
						"                <td>2009/10/09</td>\r\n" + 
						"                <td>$1,200,000</td>\r\n" + 
						"                <td>5797</td>\r\n" + 
						"                <td>a.ramos@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Gavin</td>\r\n" + 
						"                <td>Joyce</td>\r\n" + 
						"                <td>Developer</td>\r\n" + 
						"                <td>Edinburgh</td>\r\n" + 
						"                <td>42</td>\r\n" + 
						"                <td>2010/12/22</td>\r\n" + 
						"                <td>$92,575</td>\r\n" + 
						"                <td>8822</td>\r\n" + 
						"                <td>g.joyce@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Jennifer</td>\r\n" + 
						"                <td>Chang</td>\r\n" + 
						"                <td>Regional Director</td>\r\n" + 
						"                <td>Singapore</td>\r\n" + 
						"                <td>28</td>\r\n" + 
						"                <td>2010/11/14</td>\r\n" + 
						"                <td>$357,650</td>\r\n" + 
						"                <td>9239</td>\r\n" + 
						"                <td>j.chang@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Brenden</td>\r\n" + 
						"                <td>Wagner</td>\r\n" + 
						"                <td>Software Engineer</td>\r\n" + 
						"                <td>San Francisco</td>\r\n" + 
						"                <td>28</td>\r\n" + 
						"                <td>2011/06/07</td>\r\n" + 
						"                <td>$206,850</td>\r\n" + 
						"                <td>1314</td>\r\n" + 
						"                <td>b.wagner@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Fiona</td>\r\n" + 
						"                <td>Green</td>\r\n" + 
						"                <td>Chief Operating Officer (COO)</td>\r\n" + 
						"                <td>San Francisco</td>\r\n" + 
						"                <td>48</td>\r\n" + 
						"                <td>2010/03/11</td>\r\n" + 
						"                <td>$850,000</td>\r\n" + 
						"                <td>2947</td>\r\n" + 
						"                <td>f.green@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Shou</td>\r\n" + 
						"                <td>Itou</td>\r\n" + 
						"                <td>Regional Marketing</td>\r\n" + 
						"                <td>Tokyo</td>\r\n" + 
						"                <td>20</td>\r\n" + 
						"                <td>2011/08/14</td>\r\n" + 
						"                <td>$163,000</td>\r\n" + 
						"                <td>8899</td>\r\n" + 
						"                <td>s.itou@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Michelle</td>\r\n" + 
						"                <td>House</td>\r\n" + 
						"                <td>Integration Specialist</td>\r\n" + 
						"                <td>Sidney</td>\r\n" + 
						"                <td>37</td>\r\n" + 
						"                <td>2011/06/02</td>\r\n" + 
						"                <td>$95,400</td>\r\n" + 
						"                <td>2769</td>\r\n" + 
						"                <td>m.house@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Suki</td>\r\n" + 
						"                <td>Burks</td>\r\n" + 
						"                <td>Developer</td>\r\n" + 
						"                <td>London</td>\r\n" + 
						"                <td>53</td>\r\n" + 
						"                <td>2009/10/22</td>\r\n" + 
						"                <td>$114,500</td>\r\n" + 
						"                <td>6832</td>\r\n" + 
						"                <td>s.burks@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Prescott</td>\r\n" + 
						"                <td>Bartlett</td>\r\n" + 
						"                <td>Technical Author</td>\r\n" + 
						"                <td>London</td>\r\n" + 
						"                <td>27</td>\r\n" + 
						"                <td>2011/05/07</td>\r\n" + 
						"                <td>$145,000</td>\r\n" + 
						"                <td>3606</td>\r\n" + 
						"                <td>p.bartlett@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Gavin</td>\r\n" + 
						"                <td>Cortez</td>\r\n" + 
						"                <td>Team Leader</td>\r\n" + 
						"                <td>San Francisco</td>\r\n" + 
						"                <td>22</td>\r\n" + 
						"                <td>2008/10/26</td>\r\n" + 
						"                <td>$235,500</td>\r\n" + 
						"                <td>2860</td>\r\n" + 
						"                <td>g.cortez@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Martena</td>\r\n" + 
						"                <td>Mccray</td>\r\n" + 
						"                <td>Post-Sales support</td>\r\n" + 
						"                <td>Edinburgh</td>\r\n" + 
						"                <td>46</td>\r\n" + 
						"                <td>2011/03/09</td>\r\n" + 
						"                <td>$324,050</td>\r\n" + 
						"                <td>8240</td>\r\n" + 
						"                <td>m.mccray@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Unity</td>\r\n" + 
						"                <td>Butler</td>\r\n" + 
						"                <td>Marketing Designer</td>\r\n" + 
						"                <td>San Francisco</td>\r\n" + 
						"                <td>47</td>\r\n" + 
						"                <td>2009/12/09</td>\r\n" + 
						"                <td>$85,675</td>\r\n" + 
						"                <td>5384</td>\r\n" + 
						"                <td>u.butler@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Howard</td>\r\n" + 
						"                <td>Hatfield</td>\r\n" + 
						"                <td>Office Manager</td>\r\n" + 
						"                <td>San Francisco</td>\r\n" + 
						"                <td>51</td>\r\n" + 
						"                <td>2008/12/16</td>\r\n" + 
						"                <td>$164,500</td>\r\n" + 
						"                <td>7031</td>\r\n" + 
						"                <td>h.hatfield@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Hope</td>\r\n" + 
						"                <td>Fuentes</td>\r\n" + 
						"                <td>Secretary</td>\r\n" + 
						"                <td>San Francisco</td>\r\n" + 
						"                <td>41</td>\r\n" + 
						"                <td>2010/02/12</td>\r\n" + 
						"                <td>$109,850</td>\r\n" + 
						"                <td>6318</td>\r\n" + 
						"                <td>h.fuentes@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Vivian</td>\r\n" + 
						"                <td>Harrell</td>\r\n" + 
						"                <td>Financial Controller</td>\r\n" + 
						"                <td>San Francisco</td>\r\n" + 
						"                <td>62</td>\r\n" + 
						"                <td>2009/02/14</td>\r\n" + 
						"                <td>$452,500</td>\r\n" + 
						"                <td>9422</td>\r\n" + 
						"                <td>v.harrell@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Timothy</td>\r\n" + 
						"                <td>Mooney</td>\r\n" + 
						"                <td>Office Manager</td>\r\n" + 
						"                <td>London</td>\r\n" + 
						"                <td>37</td>\r\n" + 
						"                <td>2008/12/11</td>\r\n" + 
						"                <td>$136,200</td>\r\n" + 
						"                <td>7580</td>\r\n" + 
						"                <td>t.mooney@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Jackson</td>\r\n" + 
						"                <td>Bradshaw</td>\r\n" + 
						"                <td>Director</td>\r\n" + 
						"                <td>New York</td>\r\n" + 
						"                <td>65</td>\r\n" + 
						"                <td>2008/09/26</td>\r\n" + 
						"                <td>$645,750</td>\r\n" + 
						"                <td>1042</td>\r\n" + 
						"                <td>j.bradshaw@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Olivia</td>\r\n" + 
						"                <td>Liang</td>\r\n" + 
						"                <td>Support Engineer</td>\r\n" + 
						"                <td>Singapore</td>\r\n" + 
						"                <td>64</td>\r\n" + 
						"                <td>2011/02/03</td>\r\n" + 
						"                <td>$234,500</td>\r\n" + 
						"                <td>2120</td>\r\n" + 
						"                <td>o.liang@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Bruno</td>\r\n" + 
						"                <td>Nash</td>\r\n" + 
						"                <td>Software Engineer</td>\r\n" + 
						"                <td>London</td>\r\n" + 
						"                <td>38</td>\r\n" + 
						"                <td>2011/05/03</td>\r\n" + 
						"                <td>$163,500</td>\r\n" + 
						"                <td>6222</td>\r\n" + 
						"                <td>b.nash@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Sakura</td>\r\n" + 
						"                <td>Yamamoto</td>\r\n" + 
						"                <td>Support Engineer</td>\r\n" + 
						"                <td>Tokyo</td>\r\n" + 
						"                <td>37</td>\r\n" + 
						"                <td>2009/08/19</td>\r\n" + 
						"                <td>$139,575</td>\r\n" + 
						"                <td>9383</td>\r\n" + 
						"                <td>s.yamamoto@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Thor</td>\r\n" + 
						"                <td>Walton</td>\r\n" + 
						"                <td>Developer</td>\r\n" + 
						"                <td>New York</td>\r\n" + 
						"                <td>61</td>\r\n" + 
						"                <td>2013/08/11</td>\r\n" + 
						"                <td>$98,540</td>\r\n" + 
						"                <td>8327</td>\r\n" + 
						"                <td>t.walton@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Finn</td>\r\n" + 
						"                <td>Camacho</td>\r\n" + 
						"                <td>Support Engineer</td>\r\n" + 
						"                <td>San Francisco</td>\r\n" + 
						"                <td>47</td>\r\n" + 
						"                <td>2009/07/07</td>\r\n" + 
						"                <td>$87,500</td>\r\n" + 
						"                <td>2927</td>\r\n" + 
						"                <td>f.camacho@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Serge</td>\r\n" + 
						"                <td>Baldwin</td>\r\n" + 
						"                <td>Data Coordinator</td>\r\n" + 
						"                <td>Singapore</td>\r\n" + 
						"                <td>64</td>\r\n" + 
						"                <td>2012/04/09</td>\r\n" + 
						"                <td>$138,575</td>\r\n" + 
						"                <td>8352</td>\r\n" + 
						"                <td>s.baldwin@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Zenaida</td>\r\n" + 
						"                <td>Frank</td>\r\n" + 
						"                <td>Software Engineer</td>\r\n" + 
						"                <td>New York</td>\r\n" + 
						"                <td>63</td>\r\n" + 
						"                <td>2010/01/04</td>\r\n" + 
						"                <td>$125,250</td>\r\n" + 
						"                <td>7439</td>\r\n" + 
						"                <td>z.frank@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Zorita</td>\r\n" + 
						"                <td>Serrano</td>\r\n" + 
						"                <td>Software Engineer</td>\r\n" + 
						"                <td>San Francisco</td>\r\n" + 
						"                <td>56</td>\r\n" + 
						"                <td>2012/06/01</td>\r\n" + 
						"                <td>$115,000</td>\r\n" + 
						"                <td>4389</td>\r\n" + 
						"                <td>z.serrano@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Jennifer</td>\r\n" + 
						"                <td>Acosta</td>\r\n" + 
						"                <td>Junior Javascript Developer</td>\r\n" + 
						"                <td>Edinburgh</td>\r\n" + 
						"                <td>43</td>\r\n" + 
						"                <td>2013/02/01</td>\r\n" + 
						"                <td>$75,650</td>\r\n" + 
						"                <td>3431</td>\r\n" + 
						"                <td>j.acosta@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Cara</td>\r\n" + 
						"                <td>Stevens</td>\r\n" + 
						"                <td>Sales Assistant</td>\r\n" + 
						"                <td>New York</td>\r\n" + 
						"                <td>46</td>\r\n" + 
						"                <td>2011/12/06</td>\r\n" + 
						"                <td>$145,600</td>\r\n" + 
						"                <td>3990</td>\r\n" + 
						"                <td>c.stevens@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Hermione</td>\r\n" + 
						"                <td>Butler</td>\r\n" + 
						"                <td>Regional Director</td>\r\n" + 
						"                <td>London</td>\r\n" + 
						"                <td>47</td>\r\n" + 
						"                <td>2011/03/21</td>\r\n" + 
						"                <td>$356,250</td>\r\n" + 
						"                <td>1016</td>\r\n" + 
						"                <td>h.butler@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Lael</td>\r\n" + 
						"                <td>Greer</td>\r\n" + 
						"                <td>Systems Administrator</td>\r\n" + 
						"                <td>London</td>\r\n" + 
						"                <td>21</td>\r\n" + 
						"                <td>2009/02/27</td>\r\n" + 
						"                <td>$103,500</td>\r\n" + 
						"                <td>6733</td>\r\n" + 
						"                <td>l.greer@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Jonas</td>\r\n" + 
						"                <td>Alexander</td>\r\n" + 
						"                <td>Developer</td>\r\n" + 
						"                <td>San Francisco</td>\r\n" + 
						"                <td>30</td>\r\n" + 
						"                <td>2010/07/14</td>\r\n" + 
						"                <td>$86,500</td>\r\n" + 
						"                <td>8196</td>\r\n" + 
						"                <td>j.alexander@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Shad</td>\r\n" + 
						"                <td>Decker</td>\r\n" + 
						"                <td>Regional Director</td>\r\n" + 
						"                <td>Edinburgh</td>\r\n" + 
						"                <td>51</td>\r\n" + 
						"                <td>2008/11/13</td>\r\n" + 
						"                <td>$183,000</td>\r\n" + 
						"                <td>6373</td>\r\n" + 
						"                <td>s.decker@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Michael</td>\r\n" + 
						"                <td>Bruce</td>\r\n" + 
						"                <td>Javascript Developer</td>\r\n" + 
						"                <td>Singapore</td>\r\n" + 
						"                <td>29</td>\r\n" + 
						"                <td>2011/06/27</td>\r\n" + 
						"                <td>$183,000</td>\r\n" + 
						"                <td>5384</td>\r\n" + 
						"                <td>m.bruce@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td>Donna</td>\r\n" + 
						"                <td>Snider</td>\r\n" + 
						"                <td>Customer Support</td>\r\n" + 
						"                <td>New York</td>\r\n" + 
						"                <td>27</td>\r\n" + 
						"                <td>2011/01/25</td>\r\n" + 
						"                <td>$112,000</td>\r\n" + 
						"                <td>4226</td>\r\n" + 
						"                <td>d.snider@datatables.net</td>\r\n" + 
						"            </tr>\r\n" + 
						"        </tbody>\r\n" + 
						"    </table>\r\n" + 
						"");
				
				

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
