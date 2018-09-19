package com.gnrchospitals.excel;

import java.io.IOException;

import java.io.OutputStream;
import java.util.List;

import org.apache.lucene.document.Field.Index;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InvestigationExcelSheet {

	public void writeDataToExcelFile(String name, String age, String sex, String service, String ipNumber,
			String bedNumber, List<List<String>> list, List<List<String>> excelHeanderRangeList, OutputStream out) {

		List<String> colData = list.get(0);
		List<String> rowData = list.get(1);

		List<String> colHeaderName = excelHeanderRangeList.get(0);
		List<String> rowHeaderNameRange = excelHeanderRangeList.get(1);

		//System.out.println("Column Data : " + colData);
		//System.out.println("Row Data : " + rowData);
		//System.out.println("colHeaderName Data : " + colHeaderName);
		//System.out.println("rowHeaderNameRange Data : " + rowHeaderNameRange);

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Investigation Sheet");
		Row row = null;
		Cell cell = null;

		sheet.protectSheet("gnrc@123");

		Font logoFont = workbook.createFont();
		logoFont.setFontHeightInPoints((short) 28);
		logoFont.setFontName("Arial Black");
		logoFont.setBold(true);
		logoFont.setColor(IndexedColors.PINK.getIndex());

		// Cell Border Style
		CellStyle logoBorderStyle = workbook.createCellStyle();
		logoBorderStyle.setWrapText(true);
		logoBorderStyle.setAlignment(HorizontalAlignment.CENTER);
		logoBorderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		logoBorderStyle.setFont(logoFont);

		Font titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setFontName("Calibri");
		titleFont.setColor(IndexedColors.BLACK.getIndex());

		// Cell Border Style
		CellStyle titleBorderStyle = workbook.createCellStyle();
		titleBorderStyle.setWrapText(true);
		titleBorderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		titleBorderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		titleBorderStyle.setAlignment(HorizontalAlignment.CENTER);
		titleBorderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		titleBorderStyle.setFont(titleFont);

		Font diagnosFont = workbook.createFont();
		diagnosFont.setFontHeightInPoints((short) 12);
		diagnosFont.setBold(true);
		diagnosFont.setFontName("Calibri");
		diagnosFont.setColor(IndexedColors.BLACK.getIndex());

		// Cell Border Style
		CellStyle diagnosBorderStyle = workbook.createCellStyle();
		diagnosBorderStyle.setWrapText(true);
		diagnosBorderStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		diagnosBorderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		diagnosBorderStyle.setAlignment(HorizontalAlignment.CENTER);
		diagnosBorderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		diagnosBorderStyle.setFont(diagnosFont);

		// Cell Border Style
		CellStyle cellBorderStyle = workbook.createCellStyle();
		cellBorderStyle.setWrapText(true);
		cellBorderStyle.setAlignment(HorizontalAlignment.CENTER);
		cellBorderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellBorderStyle.setBorderBottom(BorderStyle.THIN);
		cellBorderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cellBorderStyle.setBorderLeft(BorderStyle.THIN);
		cellBorderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		cellBorderStyle.setBorderTop(BorderStyle.THIN);
		cellBorderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		cellBorderStyle.setBorderRight(BorderStyle.THIN);
		cellBorderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		CellStyle cellAlignStyle = workbook.createCellStyle();
		cellAlignStyle.setBorderBottom(BorderStyle.THIN);
		cellAlignStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cellAlignStyle.setBorderLeft(BorderStyle.THIN);
		cellAlignStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		cellAlignStyle.setBorderTop(BorderStyle.THIN);
		cellAlignStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		cellAlignStyle.setBorderRight(BorderStyle.THIN);
		cellAlignStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		cellAlignStyle.setAlignment(HorizontalAlignment.CENTER);
		cellAlignStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		//System.out.println("First step");

		int dateRange = rowData.size() / colData.size();
		int headerCount = rowHeaderNameRange.size() / colHeaderName.size();

		//System.out.println("Header  : " + headerCount);
		//System.out.println("Cell range : " + dateRange);

		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("GNRC");
		CellRangeAddress logoRange = new CellRangeAddress(row.getRowNum(), row.getRowNum() + 1, cell.getColumnIndex(), dateRange);
		sheet.addMergedRegion(logoRange);
		cell.setCellStyle(logoBorderStyle);
		RegionUtil.setBorderRight(BorderStyle.THIN, logoRange, sheet);
		RegionUtil.setBorderBottom(BorderStyle.THIN, logoRange, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THIN, logoRange, sheet);
		RegionUtil.setBorderTop(BorderStyle.THIN, logoRange, sheet);

		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue("INVESTIGATION REPORT");
		CellRangeAddress titleRange = new CellRangeAddress(row.getRowNum(), row.getRowNum(), cell.getColumnIndex(), dateRange);
		sheet.addMergedRegion(titleRange);
		cell.setCellStyle(titleBorderStyle);
		RegionUtil.setBorderRight(BorderStyle.THIN, titleRange, sheet);
		RegionUtil.setBorderBottom(BorderStyle.THIN, titleRange, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THIN, titleRange, sheet);
		RegionUtil.setBorderTop(BorderStyle.THIN, titleRange, sheet);

		row = sheet.createRow(3);
		cell = row.createCell(0);
		cell.setCellValue("NAME : " + name);
		CellRangeAddress nameRnge = new CellRangeAddress(row.getRowNum(), row.getRowNum(), cell.getColumnIndex(), 1);
		sheet.addMergedRegion(nameRnge);
		RegionUtil.setBorderRight(BorderStyle.THIN, nameRnge, sheet);
		RegionUtil.setBorderBottom(BorderStyle.THIN, nameRnge, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THIN, nameRnge, sheet);
		RegionUtil.setBorderTop(BorderStyle.THIN, nameRnge, sheet);

		cell = row.createCell(2);
		cell.setCellValue("AGE : " + age);
		cell.setCellStyle(cellBorderStyle);

		cell = row.createCell(3);
		cell.setCellValue("SEX : " + sex);
		cell.setCellStyle(cellBorderStyle);

		cell = row.createCell(4);
		cell.setCellValue("BED NO. : " + bedNumber);
		cell.setCellStyle(cellBorderStyle);

		row = sheet.createRow(4);
		cell = row.createCell(0);
		cell.setCellValue("SERVICE & UNIT : " + service);
		CellRangeAddress serviceRnge = new CellRangeAddress(row.getRowNum(), row.getRowNum(), cell.getColumnIndex(), 2);
		sheet.addMergedRegion(serviceRnge);
		RegionUtil.setBorderRight(BorderStyle.THIN, serviceRnge, sheet);
		RegionUtil.setBorderBottom(BorderStyle.THIN, serviceRnge, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THIN, serviceRnge, sheet);
		RegionUtil.setBorderTop(BorderStyle.THIN, serviceRnge, sheet);

		cell = row.createCell(3);
		cell.setCellValue("IP No. : " + ipNumber);
		CellRangeAddress ipRange = new CellRangeAddress(row.getRowNum(), row.getRowNum(), cell.getColumnIndex(), 4);
		sheet.addMergedRegion(ipRange);
		RegionUtil.setBorderRight(BorderStyle.THIN, ipRange, sheet);
		RegionUtil.setBorderBottom(BorderStyle.THIN, ipRange, sheet);
		RegionUtil.setBorderLeft(BorderStyle.THIN, ipRange, sheet);
		RegionUtil.setBorderTop(BorderStyle.THIN, ipRange, sheet);
		cell.setCellStyle(cellBorderStyle);

		int i = 0;
		int j = 1;
		row = sheet.createRow(5);
		cell = row.createCell(0);
		sheet.setColumnWidth(0, 5000);
		cell.setCellValue("Parameters");
		cell.setCellStyle(cellBorderStyle);

		while (i < rowData.size()) {
			cell = row.createCell(j++);
			sheet.setColumnWidth(j - 1, 3800);
			cell.setCellValue(rowData.get(i));
			cell.setCellStyle(cellBorderStyle);
			i += colData.size();
		}

		int rowIndex = 0;
		int count = 0;
		int hearderCount = 0;
		boolean flag = true;
		int headerIndex = 0;
		int paramIndex = 0;
		int tempIndex = 0;

		//System.out.println("Row Count : " + ((colData.size() - 1) + headerCount));

		while (rowIndex < ((colData.size() - 1) + headerCount)) {

			//System.out.println("Step 1");

			if (count == 0) {
				row = sheet.createRow(rowIndex + 6);
				cell = row.createCell(0);
				cell.setCellValue(colData.get(rowIndex + 1));
				cell.setCellStyle(cellBorderStyle);

			//	System.out.println("Step 1.1");
				
				cell = row.createCell(1);
				cell.setCellValue(rowData.get(1));
				cell.setCellStyle(cellAlignStyle);
			//	System.out.println("Step 1.2");
			//	System.out.println("Row Number : " + row.getRowNum());
			//	System.out.println("Cell Number : " + cell.getColumnIndex());
				if (dateRange != 1) {
					CellRangeAddress mergedCell = new CellRangeAddress(row.getRowNum(), row.getRowNum(), cell.getColumnIndex(), dateRange);
					sheet.addMergedRegion(mergedCell);
					RegionUtil.setBorderRight(BorderStyle.THIN, mergedCell, sheet);
				}
			//	System.out.println("Step 1.3");
			//	System.out.println("Step 1.4");

			} else {

				System.out.println("step 2");

				if (flag) {
					/*
					 * System.out.println("Row Index 1 : " + rowIndex);
					 * System.out.println("Row Number : " + (rowIndex + 3) +
					 * "------> Header Name  : " + rowHeaderNameRange.get(headerIndex));
					 */
					row = sheet.createRow(rowIndex + 6); // 21
					cell = row.createCell(0);
					cell.setCellValue(rowHeaderNameRange.get(headerIndex));
					cell.setCellStyle(cellAlignStyle);
					// CellRangeAddress mergedCell = new CellRangeAddress(rowIndex + 3, rowIndex +
					// 3, 1, dateRange + 1);
					CellRangeAddress mergedCell = new CellRangeAddress(row.getRowNum(), row.getRowNum(), cell.getColumnIndex(),	dateRange);
					sheet.addMergedRegion(mergedCell);
					cell.setCellStyle(diagnosBorderStyle);

					sheet.createFreezePane(1, 6);
					RegionUtil.setBorderRight(BorderStyle.THIN, mergedCell, sheet);
					RegionUtil.setBorderTop(BorderStyle.THIN, mergedCell, sheet);
					flag = false;

				} else {

				//	System.out.println("step 3");

				//	System.out.println("header range count : " + Integer.parseInt(rowHeaderNameRange.get(paramIndex + 1)));

					if (hearderCount < Integer.parseInt(rowHeaderNameRange.get(paramIndex + 1))) {

						/*
						 * System.out.println("Row Index 2 : " + rowIndex);
						 * System.out.println("Temp Index : " + tempIndex);
						 */
						row = sheet.createRow(rowIndex + 6);
						cell = row.createCell(0);
						cell.setCellValue(colData.get(tempIndex + 2));
						cell.setCellStyle(cellBorderStyle);

						int colIndex = 0;

						for (int k = 0; k < dateRange; k++) {
							cell = row.createCell(k + 1);
							cell.setCellValue(rowData.get(tempIndex + 2 + colIndex));
							cell.setCellStyle(cellAlignStyle);
							colIndex += colData.size();
						}

						tempIndex++;

						/*
						 * System.out.println("Header Count : " + hearderCount + "----> Row Number : " +
						 * (row.getRowNum() + 1) + "------->  Column Name : " + colData.get(tempIndex));
						 */

						hearderCount++;

					} else {
						// System.out.println("Header Count overflow Index No : " + rowIndex); // 18
						hearderCount = 0;
						headerIndex += 2;
						flag = true;
						rowIndex--;
						paramIndex += 2;
					}

				}
			}

			count++;
			rowIndex++;

		}

		try {
			workbook.write(out);
			out.flush();
			out.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
