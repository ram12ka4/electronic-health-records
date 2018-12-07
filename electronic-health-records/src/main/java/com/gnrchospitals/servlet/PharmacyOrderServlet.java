package com.gnrchospitals.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnrchospitals.dao.PatientDao;
import com.gnrchospitals.daoimpl.PatientDaoImpl;
import com.gnrchospitals.dto.Patient;

@WebServlet(urlPatterns = "/pharma.order")
public class PharmacyOrderServlet extends HttpServlet {

	private static final long serialVersionUID = 6157982947117798172L;
	private PatientDao patientDao = new PatientDaoImpl();
	private Patient patient = Patient.getInstance(); // Singleton class

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setAttribute("ipNumber", request.getParameter("ip_no"));
		session.setAttribute("moduleName", request.getParameter("moduleName"));
		request.getRequestDispatcher("/WEB-INF/views/gnrc-pharmacy-order.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();


		
		String action = request.getParameter("ACTION") == null || request.getParameter("ACTION").isEmpty() ? "" : request.getParameter("ACTION");
		String pharmaOrderNumber = request.getParameter("poNumber") == null ? ""	: request.getParameter("poNumber");
		String patientNo = patient.getIpNumber();
		String mrd = patient.getMrdNumber();
		String patientType = patient.getPatientCategoryCode();
		String visitNo = patient.getVisit();
		String wardCode = patient.getWardCode();
		String userId = (String) session.getAttribute("user");
		String itemName = request.getParameter("itemName") == null || request.getParameter("itemName").isEmpty() ? "" : request.getParameter("itemName");
		String[] itemCode = request.getParameterValues("itemCode");
		String[] qty = request.getParameterValues("qty");
		String referDoctor = request.getParameter("referDoctor") == null
				|| request.getParameter("referDocId").isEmpty() ? "" : request.getParameter("referDocId");

		System.out.println("ACTION : " + action);
		System.out.println("Pharma Order No : " + pharmaOrderNumber);
		System.out.println("patientNo : " + patientNo);
		System.out.println("mrd : " + mrd);
		System.out.println("patientType : " + patientType);
		System.out.println("visitNo : " + visitNo);
		System.out.println("userId : " + userId);
		System.out.println("referDoctor : " + referDoctor);

	

		try {

			if ("INSERT_UPDATE_PHARMA_ORDER".equals(action)) {
				String poNumber = patientDao.insertUpdatePharmaOrder("MAIN", pharmaOrderNumber, wardCode, "PHRMC", mrd, patientNo, referDoctor, userId, itemCode, qty);
				out.print(poNumber);
			} else if ("FETCH_SERVICE_ID_RATE".equals(action)) {
				/*String currentRate = patientDao.getServiceIdRate(serviceId[0], patientNo);*/
				//System.out.println("Current Rate : " + currentRate);
				//out.print(currentRate);
			} else if ("FETCH_PHARMA_ORDER_DETAIL".equals(action)) {
				List<String> list = patientDao.getPharmaOrderDetail(pharmaOrderNumber);
				System.out.println("Pharmacy Order Detail : " + list);
				out.print(list);
			} else if ("FETCH_PATIENT_HISTORY".equals(action)) {
				//List<String> list = patientDao.getDoctorNote(doctorOrderNumber);
				//System.out.println("Doctor Note : " + list);
				//out.print(list);
			} else if ("FETCH_PREVIOUS_PHARMA_ORDER_LIST".equals(action)) {
				List<List<String>> list = patientDao.getPrevPharmaOrderList(patientNo);
				List<String> column = list.get(0);
				
				System.out.println("Column name : " + column);
				
				List<String> row = list.get(1);
				out.println(
						"<table id=\"pharmaTable\" class=\"table-striped table-bordered nowrap\" style=\"width:100%\">");
				out.println("<thead>");
				out.println("<tr>");
				out.println("<th>S/N</th>");
				for (int i = 0; i < column.size(); i++) {
					out.println("<th>" + column.get(i) + "</th>");
				}
				out.println("<th>Action</th>");
				out.println("</tr>");
				out.println("</thead>");
				out.println("<tbody>"); // problem in this section
				int j = 0;
				int colIndex = 0;
				int rowIndex = row.size() / column.size();
				while (j < rowIndex) {
					out.println("<tr>");
					out.println("<td>" + (j + 1) + "</td>");
					String poNo = row.get(colIndex);
					String date = row.get(colIndex+1);
					String refDocCode = row.get(colIndex+3);
					String refDocName = row.get(colIndex+4);
					String status = row.get(colIndex+5);
					for (int i = 0; i < column.size(); i++) {
						out.println("<td>" + row.get(colIndex) + "</td>");
						colIndex++;
					}
					out.println(
							"<td><div class=\"view-class\"><button class=\"btn btn-warning btn-sm view-btn\" req-date=\""+ date +"\" status=\""+ status +"\" ref-doc-code=\""+ refDocCode +"\" ref-doc-name=\""+ refDocName +"\" po-no=\""
									+ poNo + "\">View</button></div></td>");
					out.println("</tr>");
					// colIndex +=1;
					j++;
				}
				out.println("</tbody>");
				out.println("</table>");
			}  else if ("GET_SERVICE_LIST".equals(action)) {
				List<String> list = patientDao.getServiceList();
				out.print(list);
			} else if ("FETCH_SPECIMEN_LIST".equals(action)) {
				//List<String> list = patientDao.getSpecimenList(serviceCode);
				//System.out.println("Specimen List : " + list);
				//out.print(list);
			} else if ("FETCH_DOCTOR_LIST".equals(action)) {
				List<String> list = patientDao.getDoctorList();
				ObjectMapper mapper = new ObjectMapper();
				String jsonMapper = mapper.writeValueAsString(list);
				System.out.println("Service Rate List : " + jsonMapper);
				out.println(jsonMapper);
			} else if ("GET_PANEL_SERVICE_CODE".equals(action)) {
				//List<String> list = patientDao.getPanelServiceCodeList(serviceCode);
				//out.print(list);
			} else if ("GET_ITEM_LIST_DRUG_REQ".equals(action)) {
				List<String> list = patientDao.getDrugReqItemList(itemName);
				ObjectMapper mapper = new ObjectMapper();
				String jsonMapper = mapper.writeValueAsString(list);
				System.out.println("Service Rate List : " + jsonMapper);
				out.println(jsonMapper);
			} else {
				request.getRequestDispatcher("/WEB-INF/views/gnrc-pharmacy-order.jsp").forward(request, response);
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