package br.com.gft.testautomation.common.export;

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;

import br.com.gft.testautomation.common.model.TestCases;

/** Class that contains several methods that support the Export module. */
public class ExcelUtils {

	/** Method used to compare strings, when a String may be null */
	public static boolean isEqual(String o1, String o2) {
	    return o1 == o2 || (o1 != null && o1.equals(o2));
	}
	
	/** Recursive method to delete the files within a folder */
	public static void deleteFolder(File folder) {
	    File[] files = folder.listFiles();
	    if (files != null) { 
	        for (File f: files) {
	            if (f.isDirectory()) {
	                deleteFolder(f);
	            }else{
	                f.delete();
	            }
	        }
	    }
	}
	
	/** Method that adds a "\n" to break the line in the Excel document, 
	 * whenever there is a ";" or "." . Receives the TestCase object. */
	public static void breakLine(TestCases testCase){
		
		testCase.setPre_requisite(testCase.getPre_requisite().replace(";", "; \n"));
		testCase.setPre_requisite(testCase.getPre_requisite().replace(".", ". \n"));
		testCase.setTestcase_description(testCase.getTestcase_description().replace(";", "; \n"));
		testCase.setTestcase_description(testCase.getTestcase_description().replace(".", ". \n"));
		testCase.setResults(testCase.getResults().replace(";", "; \n"));
		testCase.setResults(testCase.getResults().replace(".", ". \n"));
	}
	
	/** Method that add a value and a style to each cell.
	 * Receives the row, the column (where the value will be), the value as String 
	 * and the style as HSSFCellStyle */
	public static void addValueToCell(HSSFRow row, int column, String value, HSSFCellStyle style){
		
		Cell cell = row.createCell(column);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}

	public static void addValueToCell(HSSFRow row1, int column, int lin, HSSFCellStyle style) {
		
		Cell cell = row1.createCell(column);
		cell.setCellStyle(style);
		cell.setCellValue(lin);
	}


}
