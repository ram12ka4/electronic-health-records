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
import com.gnrchospitals.dto.ServiceOrder;

@WebServlet(urlPatterns = "/service.order")
public class ServiceOrderServlet extends HttpServlet {

	private static final long serialVersionUID = 6157982947117798172L;
	private PatientDao patientDao = new PatientDaoImpl();
	private Patient patient = Patient.getInstance(); // Singleton class

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setAttribute("ipNumber", request.getParameter("ip_no"));
		session.setAttribute("moduleName", request.getParameter("moduleName"));
		request.getRequestDispatcher("/WEB-INF/views/gnrc-service-order.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		String action = request.getParameter("ACTION") == null || request.getParameter("ACTION").isEmpty() ? ""
				: request.getParameter("ACTION");
		HttpSession session = request.getSession();

		String serviceCat = request.getParameter("serviceCat") == null ? "" : request.getParameter("serviceCat");
		String serviceDesc = request.getParameter("serviceDesc") == null ? "" : request.getParameter("serviceDesc");
		String serviceCode = request.getParameter("serviceCode") == null ? "" : request.getParameter("serviceCode");
		String serviceOrderoNumber = request.getParameter("soNumber") == null ? "" : request.getParameter("soNumber");
		String doctorOrderNumber = request.getParameter("doctorNoteNumber") == null ? ""
				: request.getParameter("doctorNoteNumber");
		String voucherNumber = request.getParameter("voucherNumber") == null ? ""
				: request.getParameter("voucherNumber");

		// PKG_I_SERVORDER_HEADER_N
		String patientNo = patient.getIpNumber();
		String netAmount = request.getParameter("totalNetAmount") == null
				|| request.getParameter("totalNetAmount").isEmpty() ? "" : request.getParameter("totalNetAmount");
		String doctorId = "000";
		String mrd = patient.getMrdNumber();
		String patientType = patient.getPatientCategoryCode();
		String visitNo = patient.getVisit();
		String userId = (String) session.getAttribute("user");

		// PKG_I_SERVORDER_DETAIL
		String[] serviceId = request.getParameterValues("serviceId");
		String[] qty = request.getParameterValues("qty");
		String[] disAmount = request.getParameterValues("disAmount");
		String[] disPercent = request.getParameterValues("discount");
		String[] specimen = request.getParameterValues("specimen");
		String[] treatedBy = request.getParameterValues("treatedBy");
		String[] specimenChecked = request.getParameterValues("spcimenChkBox");
		String disIndication = "P";
		String referDoctor = request.getParameter("referDoctor") == null
				|| request.getParameter("referDoctor").isEmpty() ? "" : request.getParameter("referDoctor");

		System.out.println("ACTION : " + action);
		System.out.println("patientNo : " + patientNo);
		System.out.println("netAmount : " + netAmount);
		System.out.println("doctorId : " + doctorId);
		System.out.println("mrd : " + mrd);
		System.out.println("patientType : " + patientType);
		System.out.println("visitNo : " + visitNo);
		System.out.println("userId : " + userId);
		System.out.println("disIndication : " + disIndication);
		System.out.println("referDoctor : " + referDoctor);
		System.out.println("service Order Number : " + serviceOrderoNumber);

		if (specimenChecked != null) {
			for (String value : specimenChecked) {
				System.out.println("Specimen Checked : " + value);
			}
		}

		if (treatedBy != null) {
			for (String value : treatedBy) {
				System.out.println("treatedBy : " + value);
			}
		}

		if (specimen != null) {
			for (String value : specimen) {
				System.out.println("Specimen : " + value);
			}
		}

		try {

			if ("INSERT_SERVICE_ORDER".equals(action)) {
				String soNumber = patientDao.insertServiceOrderData(serviceOrderoNumber, doctorOrderNumber, patientNo,
						netAmount, doctorId, mrd, patientType, visitNo, userId, disIndication, referDoctor, serviceId,
						qty, disAmount, disPercent, specimen, treatedBy, specimenChecked, voucherNumber);
				out.print(soNumber);
			} else if ("FETCH_SERVICE_ID_RATE".equals(action)) {
				String currentRate = patientDao.getServiceIdRate(serviceId[0], patientNo);
				System.out.println("Current Rate : " + currentRate);
				out.print(currentRate);
			} else if ("FETCH_PREV_SERVICE_ORDER_DETAIL".equals(action)) {
				List<String> list = patientDao.getServiceOrderDetail(serviceOrderoNumber);
				System.out.println("Service Order Detail List : " + list);
				out.print(list);
			} else if ("FETCH_PATIENT_HISTORY".equals(action)) {
				List<String> list = patientDao.getDoctorNote(doctorOrderNumber);
				System.out.println("Doctor Note : " + list);
				out.print(list);
			} else if ("FETCH_PREVIOUS_PATIENT_HISTORY".equals(action)) {
				List<List<String>> list = patientDao.getPatientHistory(patientNo);
				List<String> row = list.get(1);
				List<String> column = list.get(0);
				out.println(
						"<table id=\"example1\" class=\"table-striped table-bordered nowrap\" style=\"width:100%\">");
				out.println("<thead>");
				out.println("<tr>");
				out.println("<th>Select</th>");
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
					String ipNo = row.get(colIndex);
					// String soNo = row.get(colIndex+1);
					for (int i = 0; i < column.size(); i++) {
						out.println("<td>" + row.get(colIndex) + "</td>");
						colIndex++;
					}
					out.println(
							"<td><div class=\"view-class\"><button class=\"btn btn-warning btn-sm pat-view-btn\" dr-no=\""
									+ ipNo + "\">View</button></div></td>");
					out.println("</tr>");
					// colIndex +=1;
					j++;
				}
				out.println("</tbody>");
				out.println("</table>");
				// out.print(list);
			} else if ("FETCH_PREVIOUS_SERVICE_ORDER".equals(action)) {

				List<List<String>> list = patientDao.getPrevServiceOrderList(patientNo);
				List<String> row = list.get(1);
				List<String> column = list.get(0);
				out.println(
						"<table id=\"example1\" class=\"table-striped table-bordered nowrap\" style=\"width:100%\">");
				System.out.println(
						"<table id=\"example1\" class=\"table-striped table-bordered nowrap\" style=\"width:100%\">");
				out.println("<thead>");
				System.out.println("<thead>");
				out.println("<tr>");
				System.out.println("<tr>");
				out.println("<th>Select</th>");
				System.out.println("<th>Select</th>");
				for (int i = 0; i < column.size() - 3; i++) {
					out.println("<th>" + column.get(i + 1) + "</th>");
					System.out.println("<th>" + column.get(i + 1) + "</th>");
				}
				out.println("<th>Action</th>");
				System.out.println("<th>Action</th>");
				out.println("</tr>");
				System.out.println("</tr>");
				out.println("</thead>");
				System.out.println("</thead>");
				out.println("<tbody>"); // problem in this section
				System.out.println("<tbody>"); // problem in this section
				int j = 0;
				int colIndex = 0;
				int rowIndex = row.size() / column.size();
				while (j < rowIndex) {

					String isBilled = row.get(colIndex + 5);
					int totalSpecimen = Integer.parseInt(row.get(colIndex + 7));
					int totalSpecimenDone = Integer.parseInt(row.get(colIndex + 8));

					// System.out.println("Total Specimen : " + totalSpecimen);
					// System.out.println("Total Specimen Done : " + totalSpecimenDone);
					System.out.println("Row No. : " + j);

					if (totalSpecimen > 0 && !isBilled.equalsIgnoreCase("NOT BILLED")) {
						if (totalSpecimenDone == 0) {
							// System.out.println("In if clause");
							out.println("<tr class=\"redClass\">");
							System.out.println("<tr class=\"redClass\">");
						} else if (totalSpecimenDone < totalSpecimen) {
							// System.out.println("In else if clause");
							System.out.println("<tr class=\"orangeClass\">");
							out.println("<tr class=\"orangeClass\">");
						}
						if (totalSpecimen == totalSpecimenDone) {
							// System.out.println("In if clause again");
							out.println("<tr class=\"greenClass\">");
							System.out.println("<tr class=\"greenClass\">");
						}
					} else {
						out.println("<tr>");
						System.out.println("<tr>");
					}

					out.println("<td>" + (j + 1) + "</td>");
					System.out.println("<td>" + (j + 1) + "</td>");
					String ipNo = row.get(colIndex);
					String soNo = row.get(colIndex + 1);
					for (int i = 0; i < column.size() - 3; i++) {
						out.println("<td>" + row.get(colIndex + 1) + "</td>");
						System.out.println("<td>" + row.get(colIndex + 1) + "</td>");
						colIndex++;
					}
					out.println(
							"<td><div class=\"delete-btn\"><button class=\"btn btn-warning btn-sm view-btn\" so-no=\""
									+ soNo + "\" ip-no=\"" + ipNo + "\">View</button></div></td>");
					System.out.println(
							"<td><div class=\"delete-btn\"><button class=\"btn btn-warning btn-sm view-btn\" so-no=\""
									+ soNo + "\" ip-no=\"" + ipNo + "\">View</button></div></td>");
					out.println("</tr>");
					System.out.println("</tr>");
					colIndex += 3;
					j++;
				}
				out.println("</tbody>");
				System.out.println("</tbody>");
				out.println("</table>");
				System.out.println("</table>");
				// out.print(list);
			} else if ("GET_SERVICE_LIST".equals(action)) {
				List<String> list = patientDao.getServiceList();
				out.print(list);
			} else if ("FETCH_SPECIMEN_LIST".equals(action)) {
				List<String> list = patientDao.getSpecimenList(serviceCode);
				System.out.println("Specimen List : " + list);
				out.print(list);
			} else if ("FETCH_DOCTOR_LIST".equals(action)) {
				List<String> list = patientDao.getDoctorList();
				ObjectMapper mapper = new ObjectMapper();
				String jsonMapper = mapper.writeValueAsString(list);
				System.out.println("Service Rate List : " + jsonMapper);
				out.println(jsonMapper);
			} else if ("GET_PANEL_SERVICE_CODE".equals(action)) {
				List<String> list = patientDao.getPanelServiceCodeList(serviceCode);
				out.print(list);
			} else if ("GET_SERVICE_RATE_LIST".equals(action)) {
				List<ServiceOrder> list = patientDao.getServiceRateList(serviceCat, serviceDesc);
				ObjectMapper mapper = new ObjectMapper();
				String jsonMapper = mapper.writeValueAsString(list);
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