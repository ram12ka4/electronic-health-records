package com.gnrchospitals.report;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gnrchospitals.dao.PatientDao;
import com.gnrchospitals.daoimpl.PatientDaoImpl;
import com.gnrchospitals.excel.InvestigationExcelSheet;

@WebServlet(urlPatterns = { "/invest.report" })
public class InvestigationReport extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name") == null  || request.getParameter("name").isEmpty() ? "" : request.getParameter("name");

		String age = request.getParameter("age") == null  || request.getParameter("age").isEmpty() ? "" : request.getParameter("age");

		String sex = request.getParameter("sex") == null  || request.getParameter("sex").isEmpty() ? "" : request.getParameter("sex");

		String service = request.getParameter("service") == null  || request.getParameter("service").isEmpty() ? "" : request.getParameter("service");

		String ipNumber = request.getParameter("ip_no") == null  || request.getParameter("ip_no").isEmpty() ? "" : request.getParameter("ip_no");

		String bedNumber = request.getParameter("bed_number") == null  || request.getParameter("bed_number").isEmpty() ? "" : request.getParameter("bed_number");

		InvestigationExcelSheet investExcel = new InvestigationExcelSheet();
		PatientDao patientDao = new PatientDaoImpl();

		try {
			List<List<String>> list = patientDao.getPreviousRecord(ipNumber, "IS");
			List<List<String>> excelHeanderRangeList = patientDao.getExcelHeaderRange("IS");

			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=sample.xlsx");
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "cache");
			response.setHeader("Cache-Control", "private");

			investExcel.writeDataToExcelFile(name, age, sex, service, ipNumber, bedNumber, list, excelHeanderRangeList, 
					response.getOutputStream());

		} catch (Exception e) {
			System.out.println("Going to sendErrorRidirect method");
			sendErrorReirect(request, response, "/WEB-INF/views/error.jsp", e);
		}

	}

	protected void sendErrorReirect(HttpServletRequest request, HttpServletResponse response, String errroPageURL,
			Throwable e) throws ServletException, IOException {
		request.setAttribute("javax.servlet.jsp.jspException", e);
		getServletConfig().getServletContext().getRequestDispatcher(errroPageURL).forward(request, response);

	}

}
