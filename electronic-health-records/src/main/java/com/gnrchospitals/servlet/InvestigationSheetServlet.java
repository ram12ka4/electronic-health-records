package com.gnrchospitals.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.codehaus.jackson.map.ObjectMapper;

import com.gnrchospitals.dao.PatientDao;
import com.gnrchospitals.daoimpl.PatientDaoImpl;

@WebServlet(urlPatterns = "/invest.do")
public class InvestigationSheetServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PatientDao patientDao = new PatientDaoImpl();
	private Map<String, String> keyValue = new HashMap<>();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String token = request.getParameter("token") == null ? "" : request.getParameter("token");
		String msg = request.getParameter("msg") == null ? "" : request.getParameter("msg");
		String ipNo = (String) session.getAttribute("ipNo") == null ? "" : (String) session.getAttribute("ipNo");
		String action = request.getParameter("ACTION") == null ? "" : request.getParameter("ACTION");

		try {

			request.setAttribute("ipName", request.getParameter("ip_no"));
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
		String action = request.getParameter("ACTION") == null ? "" : request.getParameter("ACTION");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String paramType = request.getParameter("paramType") == null ? "" : request.getParameter("paramType");

		List<String> list = new ArrayList<>();

		/*
		 * String [] json = null; if (br != null) { json = br.readLine().replace("\"",
		 * "").split("&"); System.out.println("JSON DATA " + json[0] + " " + json[1]);
		 * paramType = json[1]; action = json[0];
		 * 
		 * }
		 */

		System.out.println("PARAM TYPE " + paramType);
		System.out.println("ACTION " + action);

		try {

			if ("GET_BLOOD_GRP_DATA".equals(action.trim())) {
				System.out.println("In if part");
				list = patientDao.getParameterList(paramType);
				System.out.println("KEY VALUE" + list);
				// ObjectMapper mapper = new ObjectMapper();
				// response.setContentType("application/json");
				// String json1 = mapper.writeValueAsString(keyValue);
				// System.out.println("JSON DATA" + json);
				out.println(list);
			} else {
				System.out.println("In else part");
				request.setAttribute("ipName", request.getParameter("ip_no"));
				request.getRequestDispatcher("/WEB-INF/views/gnrc-invst-sheet.jsp").forward(request, response);
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
