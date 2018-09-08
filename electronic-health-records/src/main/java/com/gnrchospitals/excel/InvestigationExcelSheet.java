package com.gnrchospitals.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InvestigationExcelSheet {

	public void writeDataToExcelFile(String name, String age, String sex, String service, String ipNumber,
			String bedNumber, List<List<String>> list, OutputStream out) {

		List<String> colData = list.get(0);
		List<String> rowData = list.get(1);
		
		System.out.println("Column Data : " + colData);
		System.out.println("Row Data : " + rowData);

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Investigation Report");
		Object[][] datatypes = { { "Datatype", "Type", "Size(in bytes)" }, { "int", "Primitive", 2 },
				{ "float", "Primitive", 4 }, { "double", "Primitive", 8 }, { "char", "Primitive", 1 },
				{ "String", "Non-Primitive", "No fixed size" } };

		int rowNo = 0;
		System.out.println("Creating Excel");

		for (Object[] datatype : datatypes) {

			Row row = sheet.createRow(rowNo++);
			int colNo = 0;

			for (Object field : datatype) {
				Cell cell = row.createCell(colNo++);
				if (field instanceof String) {
					cell.setCellValue((String) field);
				} else if (field instanceof Integer) {
					cell.setCellValue((Integer) field);
				}

			}
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
