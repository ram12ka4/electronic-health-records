package com.gnrchospitals.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnrchospitals.dao.PatientDao;
import com.gnrchospitals.daoimpl.PatientDaoImpl;
import com.gnrchospitals.dto.IndoorPatient;
import com.gnrchospitals.dto.ServiceOrder;

@WebServlet(urlPatterns = "/patient.transfer")
public class ServiceOrderServlet extends HttpServlet {

	private static final long serialVersionUID = 6157982947117798172L;
	private PatientDao patientDao = new PatientDaoImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("ipNumber", request.getParameter("ip_no"));
		request.getRequestDispatcher("/WEB-INF/views/gnrc-service-order.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String action = request.getParameter("ACTION") == null || request.getParameter("ACTION").isEmpty() ? "" : request.getParameter("ACTION");
		String ipNo = request.getParameter("ip_no") == null ? "" : request.getParameter("ip_no");
		String serviceCat = request.getParameter("serviceCat") == null ? "" : request.getParameter("serviceCat");
		String serviceDesc = request.getParameter("serviceDesc") == null ? "" : request.getParameter("serviceDesc");
		String serviceCode = request.getParameter("serviceCode") == null ? "" : request.getParameter("serviceCode");

		try {

			if ("GET_SERVICE_LIST".equals(action)) {

				List<String> list = patientDao.getServiceList();

				out.print(list);

			} else if ("GET_PANEL_SERVICE_CODE".equals(action)) {

				List<String> list = patientDao.getPanelServiceCodeList(serviceCode);

				out.print(list);

			} else	if ("GET_SERVICE_RATE_LIST".equals(action)) {

				List<ServiceOrder> list = patientDao.getServiceRateList(serviceCat, serviceDesc);

				ObjectMapper mapper = new ObjectMapper();

				String jsonMapper = mapper.writeValueAsString(list);

				//System.out.println("JSON MAPPER : " + jsonMapper);

				//String jsonMapper = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);

				//System.out.println("JSON MAPPER : " + jsonMapper);
				
				System.out.println("Service Rate List : " + jsonMapper);

				out.println(jsonMapper);

			} else {
				request.getRequestDispatcher("/WEB-INF/views/gnrc-service-order.jsp").forward(request, response);
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
