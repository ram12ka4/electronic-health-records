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
		
		String orderNo = request.getParameter("orderNo") == null || request.getParameter("orderNo").isEmpty() ? "": request.getParameter("orderNo");
		String referDoctor = request.getParameter("referDoctor") == null || request.getParameter("referDoctor").isEmpty() ? "": request.getParameter("referDoctor");
		String patientName = request.getParameter("patientName") == null || request.getParameter("patientName").isEmpty() ? "": request.getParameter("patientName");
		String speciality = request.getParameter("speciality") == null || request.getParameter("speciality").isEmpty() ? "": request.getParameter("speciality");
		String consultant = request.getParameter("consultant") == null || request.getParameter("consultant").isEmpty() ? "": request.getParameter("consultant");
		String doctorId = request.getParameter("doctorId") == null || request.getParameter("doctorId").isEmpty() ? "": request.getParameter("doctorId");
		String orderDate = request.getParameter("orderDate") == null || request.getParameter("orderDate").isEmpty() ? "": request.getParameter("orderDate");
		String ward = request.getParameter("ward") == null || request.getParameter("ward").isEmpty() ? "": request.getParameter("ward");
		String bed = request.getParameter("bed") == null || request.getParameter("bed").isEmpty() ? "": request.getParameter("bed");
		String mrd = request.getParameter("mrd") == null || request.getParameter("mrd").isEmpty() ? "": request.getParameter("mrd");
		String visitNo = request.getParameter("visitNo") == null || request.getParameter("visitNo").isEmpty() ? "": request.getParameter("visitNo");
		String sex = request.getParameter("sex") == null || request.getParameter("sex").isEmpty() ? "": request.getParameter("sex");
		String age = request.getParameter("age") == null || request.getParameter("age").isEmpty() ? "": request.getParameter("age");
		String patCategoryCode = request.getParameter("patCategoryCode") == null || request.getParameter("patCategoryCode").isEmpty() ? "": request.getParameter("patCategoryCode");
		String patSubCategoryCode = request.getParameter("patSubCategoryCode") == null || request.getParameter("patSubCategoryCode").isEmpty() ? "": request.getParameter("patSubCategoryCode");
		String serviceCategory = request.getParameter("serviceCategory") == null || request.getParameter("serviceCategory").isEmpty() ? "": request.getParameter("serviceCategory");
		String serviceId = request.getParameter("serviceId") == null || request.getParameter("serviceId").isEmpty() ? "": request.getParameter("serviceId");
		String qty = request.getParameter("qty") == null || request.getParameter("qty").isEmpty() ? "": request.getParameter("qty");
		String rate = request.getParameter("rate") == null || request.getParameter("rate").isEmpty() ? "": request.getParameter("rate");
		String discount = request.getParameter("discount") == null || request.getParameter("discount").isEmpty() ? "": request.getParameter("discount");
		//String serviceCategory = request.getParameter("serviceCategory") == null || request.getParameter("serviceCategory").isEmpty() ? "": request.getParameter("serviceCategory");
		
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
