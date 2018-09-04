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
import javax.servlet.http.HttpSession;

import com.gnrchospitals.util.LoginDBConnection;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@WebServlet(urlPatterns = { "/transfer.report" })
public class InvestigationReport extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String ipNumber = (String) session.getAttribute("ipNo");

		System.out.println("Session Value : " + ipNumber);

		try (Connection con = LoginDBConnection.getConnection()) {
			JasperReport jasperReport = null;
			JasperDesign jasperDesign = null;
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("ip_param", (String) ipNumber);

			String path = getServletContext().getRealPath("/WEB-INF/views/");
			jasperDesign = JRXmlLoader.load(path + "/investigation-report.jrxml");

			System.out.println("PDF path is" + path);
			System.out.println("Parameter : " + parameters);

			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			byte[] byteStream = JasperRunManager.runReportToPdf(jasperReport, parameters, con);
			OutputStream out = response.getOutputStream();
			prepareDownload(response, "Investigation_Sheet_Report");
			response.setContentLength(byteStream.length);
			out.write(byteStream, 0, byteStream.length);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.sendError(500, "Unable to generate report");
		}

	}
	
    private void prepareDownload(final HttpServletResponse response, final String filename) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".pdf");
        response.setHeader("Expires", "0");
        response.setHeader("Pragma", "cache");
        response.setHeader("Cache-Control", "private");
}

}
