package com.gnrchospitals.report;

import java.io.IOException;
import java.nio.charset.MalformedInputException;

import javax.servlet.http.HttpServletRequest;

import com.gnrchospitals.context.Context;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent extends PdfPageEventHelper {

	private PdfTemplate t;
	private Image total;

	public void onOpenDocument(PdfWriter writer, Document document) {
		//t = writer.getDirectContent().createTemplate(30, 16);
		t = writer.getDirectContent().createTemplate(30, 1);
		try {
			total = Image.getInstance(t);
			total.setRole(PdfName.ARTIFACT);
		} catch (DocumentException de) {
			// TODO: handle exception
			throw new ExceptionConverter(de);
		}
	}

	public void onEndPage(PdfWriter writer, Document document) {
		addHeader(writer);
		addFooter(writer);
	}

	private void addFooter(PdfWriter writer) {
		// TODO Auto-generated method stub
		PdfPTable footer = new PdfPTable(3);
		try {
			// set defaults
			footer.setWidths(new int[] { 24, 2, 1 });
			footer.setTotalWidth(527);
			footer.setLockedWidth(true);
			footer.getDefaultCell().setFixedHeight(40);
			footer.getDefaultCell().setBorder(Rectangle.TOP);
			footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

			// add copyright
			footer.addCell(new Phrase("\u00A9 gnrchospitals.com", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));

			// add current page count
			footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			footer.addCell(new Phrase(String.format("Page %d of", writer.getPageNumber()),
					new Font(Font.FontFamily.HELVETICA, 8)));

			// add placeholder for total page count
			PdfPCell totalPageCount = new PdfPCell(total);
			totalPageCount.setBorder(Rectangle.TOP);
			totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
			footer.addCell(totalPageCount);

			// write page
			PdfContentByte canvas = writer.getDirectContent();
			canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
			footer.writeSelectedRows(0, -1, 34, 50, canvas);
			canvas.endMarkedContentSequence();
		} catch (DocumentException de) {
			throw new ExceptionConverter(de);
		}

	}

	private void addHeader(PdfWriter writer) {
		// TODO Auto-generated method stub
		PdfPTable header = new PdfPTable(1);

		try {
			//header.setWidths(new int[] { 2, 24 });
			header.setTotalWidth(527);
			header.setLockedWidth(true);
			header.getDefaultCell().setFixedHeight(100);
			header.getDefaultCell().setBorder(Rectangle.BOX);
			header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

			System.out.println(HeaderFooterPageEvent.class);

			HttpServletRequest request = Context.getCurrentinstance().getRequest();
			String path = request.getServletContext().getRealPath("/images");

			// add text
			/*PdfPCell text = new PdfPCell();
			text.setPaddingBottom(15);
			text.setPaddingLeft(10);
			text.setBorder(Rectangle.BOTTOM);
			text.setBorderColor(BaseColor.LIGHT_GRAY);
			text.addElement(new Phrase("GNRC Ltd.", new Font(Font.FontFamily.HELVETICA, 12)));
			text.addElement(new Phrase("https://gnrchospitals.com", new Font(Font.FontFamily.HELVETICA, 8)));
			header.addCell(text);*/
			
			
			// add image
			
			header.setPaddingTop(0);
			
		
			System.out.println("Absolute Path is : " + path);
			Image logo = Image.getInstance(path + "/favicon.jpg");
			PdfPCell cell = new PdfPCell(logo, true);
			//cell.setPadding(2);
			cell.setPadding(0);
			cell.setFixedHeight(90f);
			
			cell.setBorder(PdfPCell.BOTTOM);
			cell.setBorderColor(BaseColor.LIGHT_GRAY);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			header.addCell(cell);

		

			// write content
			header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
		} catch (DocumentException de) {
			// TODO: handle exception
			throw new ExceptionConverter(de);
		} catch (MalformedInputException mal) {
			throw new ExceptionConverter(mal);
		} catch (IOException mal) {
			throw new ExceptionConverter(mal);
		}
	}

	public void onCloseDocument(PdfWriter writer, Document document) {
		int totalLength = String.valueOf(writer.getPageNumber()).length();
		int totalWidth = totalLength * 5;
		ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
				new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)), totalWidth,
				6, 0);
	}

}
