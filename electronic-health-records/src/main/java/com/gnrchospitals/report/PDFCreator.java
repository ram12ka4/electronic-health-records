package com.gnrchospitals.report;

import java.util.Date;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PDFCreator {

	public final static Font SMALL_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	public final static Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
	public final static Font HEADER_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

	public static void addMetaData(Document document, String fileName) {
		document.addTitle("Transfer Summary");
		document.addSubject("GNRC");
		document.addAuthor("Ram Kumar Basak");
	}

	public static void addContent(Document document, List<String> list) throws DocumentException {
		Paragraph paragraph = new Paragraph();
		paragraph.setFont(NORMAL_FONT);
		createReportTable(paragraph, list);
		document.add(paragraph);
	}

	private static void createReportTable(Paragraph paragraph, List<String> list) {
		// TODO Auto-generated method stub
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		paragraph.add(new Chunk("Report Table : -", SMALL_BOLD));
	}

	public static void addtitlePage(Document document, String title) throws DocumentException {

		Paragraph paragraph = new Paragraph();

		PdfPTable headerTable = new PdfPTable(1);
		headerTable.setWidthPercentage(100);
		//headerTable.setSpacingBefore(30);
		headerTable.setSpacingAfter(30);
		document.add(headerTable);
		headerTable.addCell(getCell("TRANSFER SUMMARY", PdfPCell.ALIGN_CENTER, PDFCreator.HEADER_FONT));
		document.add(headerTable);

		PdfPTable nameMRDTable = new PdfPTable(3);
		nameMRDTable.setWidthPercentage(100);
		nameMRDTable.addCell(getCell("Name : NAOREM SURAJ KUMAR", PdfPCell.ALIGN_LEFT, PDFCreator.SMALL_BOLD));
		nameMRDTable.addCell(getCell("IP : IP/171222/000013", PdfPCell.ALIGN_CENTER, PDFCreator.SMALL_BOLD));
		nameMRDTable.addCell(getCell("MRD No : SM/171222/000074", PdfPCell.ALIGN_RIGHT, PDFCreator.SMALL_BOLD));
		nameMRDTable.setSpacingAfter(50f);
		document.add(nameMRDTable);

		PdfPTable table3 = new PdfPTable(2);
		table3.setWidthPercentage(100);
		table3.addCell(getCell3("Provisional / Final Diagnosis : \t\t", PdfPCell.ALIGN_LEFT, PDFCreator.NORMAL_FONT));
		table3.addCell(getCell3("NAOREM SURAJ KUMAR", PdfPCell.ALIGN_MIDDLE, PDFCreator.NORMAL_FONT));
		table3.addCell(getCell3("Transferred form\t\t : ", PdfPCell.ALIGN_LEFT, PDFCreator.NORMAL_FONT));
		table3.addCell(getCell3("ICU", PdfPCell.ALIGN_MIDDLE, PDFCreator.NORMAL_FONT));
		table3.addCell(getCell3("Transfer Date/Time\t\t : ", PdfPCell.ALIGN_LEFT, PDFCreator.NORMAL_FONT));
		table3.addCell(getCell3(new Date().toString(), PdfPCell.ALIGN_MIDDLE, PDFCreator.NORMAL_FONT));
		table3.addCell(getCell3("Cause of Transfer\t\t : ", PdfPCell.ALIGN_LEFT, PDFCreator.NORMAL_FONT));
		table3.addCell(getCell3(new Date().toString(), PdfPCell.ALIGN_MIDDLE, PDFCreator.NORMAL_FONT));
		table3.addCell(getCell3("Name of Trasfer Doctor\t\t : ", PdfPCell.ALIGN_LEFT, PDFCreator.NORMAL_FONT));
		table3.addCell(getCell3(new Date().toString(), PdfPCell.ALIGN_MIDDLE, PDFCreator.NORMAL_FONT));
		table3.setSpacingAfter(50f);
		document.add(table3);

		PdfPTable vitals = new PdfPTable(2);
		vitals.setWidthPercentage(100);
		PdfPCell vitalCell = new PdfPCell(new Phrase("Vitals", PDFCreator.HEADER_FONT));
		vitalCell.setColspan(2);
		vitalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		vitalCell.setPadding(10);
		vitals.addCell(vitalCell);
		vitals.addCell(getCell3("Pulse	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.NORMAL_FONT));
		vitals.addCell(getCell3("Temp	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.NORMAL_FONT));
		vitals.addCell(getCell3("ITB/NITB : 234", PdfPCell.ALIGN_CENTER, PDFCreator.NORMAL_FONT));
		vitals.addCell(getCell3("Chest	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.NORMAL_FONT));
		vitals.addCell(getCell3("U. Cath	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.NORMAL_FONT));
		vitals.addCell(getCell3("B. P.	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.NORMAL_FONT));
		vitals.addCell(getCell3("R. R.	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.NORMAL_FONT));
		vitals.addCell(getCell3("V/NB	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.NORMAL_FONT));
		vitals.addCell(getCell3("CVS	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.NORMAL_FONT));
		vitals.addCell(getCell3("GCS	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.NORMAL_FONT));
		vitals.setSpacingAfter(30f);
		document.add(vitals);

		//document.add(paragraph);

		//document.newPage();
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		// TODO Auto-generated method stub
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public static PdfPCell getCell(String text, int alignment, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setPadding(0);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	public static PdfPCell getCell3(String text, int alignment, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setPadding(10);
		cell.setVerticalAlignment(alignment);
		cell.setBorder(PdfPCell.BOX);
		return cell;
	}
}
