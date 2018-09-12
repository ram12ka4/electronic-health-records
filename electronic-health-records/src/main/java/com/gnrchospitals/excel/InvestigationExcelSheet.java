package com.gnrchospitals.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
			String bedNumber, List<List<String>> list, OutputStream out) {

		List<String> colData = list.get(0);
		List<String> rowData = list.get(1);
		String[] headerName = new String[] { "HAEMATOLOGY", "BIOCHEMISTRY", "CLINICAL PATHOLOGY", "SEROLOGY",
				"RADIOLOGY", "OTHERS" };
		Integer[] headerRange = new Integer[] { 16, 32, 3, 9, 3, 1 };

		System.out.println("Column Data : " + colData);
		System.out.println("Row Data : " + rowData);

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Investigation Sheet");
		Row row = null;
		Cell cell = null;

		// Cell Border Style
		CellStyle cellBorderStyle = workbook.createCellStyle();
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

		row = sheet.createRow(1);

		System.out.println("First step");

		int dateRange = rowData.size() / colData.size();

		System.out.println("Cell range : " + dateRange);

		int i = 0;
		int j = 2;
		row = sheet.createRow(2);
		cell = row.createCell(1);
		cell.setCellValue("Parameters");
		cell.setCellStyle(cellBorderStyle);

		while (i < rowData.size()) {
			cell = row.createCell(j++);
			cell.setCellValue(rowData.get(i));
			cell.setCellStyle(cellBorderStyle);
			i += colData.size();
		}

		int rowIndex = 0;
		int count = 0;
		int hearderCount = 0;
		boolean flag = true;
		int headerIndex = 0;
		int tempIndex = 0;

		while (rowIndex < colData.size() + 5) {

			if (count == 0) {
				row = sheet.createRow(rowIndex + 3);
				cell = row.createCell(1);
				cell.setCellValue(colData.get(rowIndex + 1));
				cell.setCellStyle(cellBorderStyle);

				cell = row.createCell(2);
				cell.setCellValue(rowData.get(1));
				cell.setCellStyle(cellAlignStyle);
				CellRangeAddress mergedCell = new CellRangeAddress(3, 3, 2, dateRange + 1);
				sheet.addMergedRegion(mergedCell);
				RegionUtil.setBorderRight(BorderStyle.THIN, mergedCell, sheet);

			} else {

				if (flag) {
					//System.out.println("Row Index 1 : " + rowIndex);
					//System.out.println(							"Row Number : " + (rowIndex + 3) + "------> Header Name  : " + headerName[headerIndex]);
					row = sheet.createRow(rowIndex + 3); // 21
					cell = row.createCell(1);
					cell.setCellValue(headerName[headerIndex]);
					cell.setCellStyle(cellAlignStyle);
					CellRangeAddress mergedCell = new CellRangeAddress(rowIndex + 3, rowIndex + 3, 1, dateRange + 1);
					sheet.addMergedRegion(mergedCell);
					sheet.createFreezePane( 2, 3);
					RegionUtil.setBorderRight(BorderStyle.THIN, mergedCell, sheet);
					RegionUtil.setBorderTop(BorderStyle.THIN, mergedCell, sheet);
					flag = false;

				} else {

				
					if (hearderCount < headerRange[headerIndex]) {

						//System.out.println("Row Index 2 : " + rowIndex);
						//System.out.println("Temp Index : " + tempIndex);

						row = sheet.createRow(rowIndex + 3); 
						cell = row.createCell(1);
						cell.setCellValue(colData.get(tempIndex + 2));
						cell.setCellStyle(cellBorderStyle);

						int colIndex = 0;

						for (int k = 0; k < dateRange; k++) {
							cell = row.createCell(k + 2);
							cell.setCellValue(rowData.get(tempIndex + 2 + colIndex));
							cell.setCellStyle(cellAlignStyle);
							colIndex += colData.size();
						}

						tempIndex++;

						/*System.out.println("Header Count : " + hearderCount + "----> Row Number : "
								+ (row.getRowNum() + 1) + "------->  Column Name : " + colData.get(tempIndex))*/;

						hearderCount++;

					} else {
						//System.out.println("Header Count overflow Index No : " + rowIndex); // 18
						hearderCount = 0;
						headerIndex++;
						flag = true;
						rowIndex--;
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
