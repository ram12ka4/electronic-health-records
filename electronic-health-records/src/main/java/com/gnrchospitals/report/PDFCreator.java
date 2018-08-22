package com.gnrchospitals.report;

import java.util.Date;
import java.util.List;

import javax.swing.plaf.SliderUI;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import oracle.jdbc.proxy.annotation.GetCreator;

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
		headerTable.addCell(getCell("TRANSFER SUMMARY", PdfPCell.ALIGN_CENTER, PDFCreator.HEADER_FONT));
		headerTable.setSpacingAfter(30f);
		document.add(headerTable);

		PdfPTable nameMRDTable = new PdfPTable(3);
		nameMRDTable.setWidthPercentage(100);
		nameMRDTable.addCell(getCell("Name : NAOREM SURAJ KUMAR", PdfPCell.ALIGN_LEFT, PDFCreator.SMALL_BOLD));
		nameMRDTable.addCell(getCell("IP : IP/171222/000013", PdfPCell.ALIGN_CENTER, PDFCreator.SMALL_BOLD));
		nameMRDTable.addCell(getCell("MRD No : SM/171222/000074", PdfPCell.ALIGN_RIGHT, PDFCreator.SMALL_BOLD));
		nameMRDTable.setSpacingAfter(30f);
		document.add(nameMRDTable);


		PdfPTable table3 = new PdfPTable(1);
		PdfPCell cell3 = new PdfPCell();
		Chunk reportTitle = new Chunk("Provisional / Final Diagnosis : \t\t", PDFCreator.SMALL_BOLD);
		Chunk reportValue = new Chunk("NAOREM SURAJ KUMAR", PDFCreator.NORMAL_FONT);
		
		Phrase phrase = new Phrase();
		phrase.add(reportTitle);
		phrase.add(reportValue);

		paragraph.add(phrase);
		paragraph.setAlignment(Element.ALIGN_LEFT);
		
		table3.addCell(paragraph);
		
		/*preface.add(new Phrase("Provisional / Final Diagnosis : \t\t", PDFCreator.SMALL_BOLD));
		preface.add(new Phrase("NAOREM SURAJ KUMAR", PDFCreator.NORMAL_FONT));
		*///addEmptyLine(paragraph, 1);
		paragraph.add(new Phrase("Transferred form\t\t: ", PDFCreator.SMALL_BOLD));
		paragraph.add(new Phrase("ICU", PDFCreator.NORMAL_FONT));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Phrase("Transfer Date/Time\t\t: ", PDFCreator.SMALL_BOLD));
		paragraph.add(new Phrase(new Date().toString(), PDFCreator.NORMAL_FONT));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Phrase("Cause of Transfer\t\t: ", PDFCreator.SMALL_BOLD));
		paragraph.add(new Phrase(new Date().toString(), PDFCreator.NORMAL_FONT));
		addEmptyLine(paragraph, 1);
		paragraph.add(new Phrase("Name of Trasfer Doctor\t\t: ", PDFCreator.SMALL_BOLD));
		paragraph.add(new Phrase(new Date().toString(), PDFCreator.NORMAL_FONT));
		
		
		PdfPTable vitals = new PdfPTable(2);
		vitals.setWidthPercentage(100);
		vitals.addCell(getCell("Pulse	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.HEADER_FONT));
		vitals.addCell(getCell("Pulse	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.HEADER_FONT));
		vitals.addCell(getCell("Pulse	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.HEADER_FONT));
		vitals.addCell(getCell("Pulse	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.HEADER_FONT));
		vitals.addCell(getCell("Pulse	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.HEADER_FONT));
		vitals.addCell(getCell("Pulse	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.HEADER_FONT));
		vitals.addCell(getCell("Pulse	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.HEADER_FONT));
		vitals.addCell(getCell("Pulse	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.HEADER_FONT));
		vitals.addCell(getCell("Pulse	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.HEADER_FONT));
		vitals.addCell(getCell("Pulse	: 234", PdfPCell.ALIGN_CENTER, PDFCreator.HEADER_FONT));
		vitals.setSpacingAfter(30f);
		document.add(vitals);

		
		
		
		document.add(paragraph);

		document.newPage();
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

	public static PdfPCell getCell(Paragraph paragraph) {
		PdfPCell cell = new PdfPCell(paragraph);
		cell.setPadding(0);
		//cell.setHorizontalAlignment(alignment);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}
}
