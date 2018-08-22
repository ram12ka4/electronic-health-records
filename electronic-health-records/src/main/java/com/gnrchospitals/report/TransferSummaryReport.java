package com.gnrchospitals.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet(urlPatterns = { "/transfer.report" })
public class TransferSummaryReport extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<String> list = new ArrayList<>();
		
		list.add("Ram");
		list.add("Banajit Da");
		list.add("debashis Da");
		list.add("Bidyout da");
		
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			Document document = new Document(PageSize.A4, 36, 36, 90, 36);
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
			writer.setPageEvent(event);

			document.open();

			PDFCreator.addMetaData(document, "Transfer Summary");
			PDFCreator.addtitlePage(document, "Transfer Summary");
			PDFCreator.addContent(document, list);
			//document.add(new Paragraph(new Date().toString()));

			document.close();

			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());
			// write ByteArrayOutputStream to the ServletOutputStream
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (DocumentException de) {
			throw new IOException(de.getMessage());
		}

	}

}
