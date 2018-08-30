package com.gnrchospitals.report;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gnrchospitals.util.LoginDBConnection;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@WebServlet(urlPatterns = { "/transfer.report" })
public class TransferSummaryReport extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Connection con = LoginDBConnection.getConnection();
			JasperReport jasperReport = null;
			JasperDesign jasperDesign = null;
			Map<String, String> parameter = new HashMap<>();

			String path = getServletContext().getRealPath("/WEB-INF/views/");
			System.out.println("PDF path is" + path);
			jasperDesign = JRXmlLoader.load(path + "/sample.jrxml");
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			byte[] byteStream = JasperRunManager.runReportToPdf(jasperReport, parameter, con);
			OutputStream out = response.getOutputStream();
			response.setContentType("application/pdf");
			response.setContentLength(byteStream.length);
			out.write(byteStream, 0, byteStream.length);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
