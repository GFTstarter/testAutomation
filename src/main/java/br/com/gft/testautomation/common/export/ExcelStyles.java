package br.com.gft.testautomation.common.export;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

/** Class responsible for creating the different excel styles presented on 
 * the exported document. */
public class ExcelStyles {

	/** Method that creates the style of main panel's left column */
	public HSSFCellStyle leftStyle(HSSFWorkbook workbook){
		HSSFCellStyle cellStyleLeft = workbook.createCellStyle();
		HSSFFont fontLeft = workbook.createFont();	
		cellStyleLeft.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		cellStyleLeft.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyleLeft.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		fontLeft.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);		
		cellStyleLeft.setFont(fontLeft);
		
		return cellStyleLeft;		
	}
	
	/** Method that creates the style of the document's first row */
	public HSSFCellStyle firstRowStyle(HSSFWorkbook workbook){
		HSSFCellStyle cellStyleFirstRow = workbook.createCellStyle();		
		HSSFFont fontFirstRow = workbook.createFont();	
		cellStyleFirstRow.setFillForegroundColor(HSSFColor.DARK_TEAL.index);	
		cellStyleFirstRow.setFillPattern(CellStyle.SOLID_FOREGROUND);	
		fontFirstRow.setColor(HSSFColor.WHITE.index);
		fontFirstRow.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontFirstRow.setFontHeightInPoints((short)13);
		cellStyleFirstRow.setFont(fontFirstRow);
		cellStyleFirstRow.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		return cellStyleFirstRow;
	}
	
	/** Method that creates the style of the content cell's header */
	public HSSFCellStyle panelHeaderStyle(HSSFWorkbook workbook){
		HSSFCellStyle cellStyleHeader = workbook.createCellStyle();		
		HSSFFont fontHeader = workbook.createFont();	
		cellStyleHeader.setFillForegroundColor(HSSFColor.DARK_TEAL.index);	
		cellStyleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);	
		fontHeader.setColor(HSSFColor.WHITE.index);
		fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontHeader.setFontHeightInPoints((short)11);
		cellStyleHeader.setFont(fontHeader);
		cellStyleHeader.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleHeader.setBottomBorderColor(HSSFColor.WHITE.index);
		cellStyleHeader.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleHeader.setLeftBorderColor(HSSFColor.WHITE.index);
		cellStyleHeader.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleHeader.setRightBorderColor(HSSFColor.WHITE.index);
		cellStyleHeader.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleHeader.setTopBorderColor(HSSFColor.WHITE.index);
		cellStyleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		return cellStyleHeader;
	}
	
	/** Method that creates the style of the common content */
	public HSSFCellStyle contentStyle(HSSFWorkbook workbook){
		HSSFCellStyle cellStyleContent = workbook.createCellStyle();
		HSSFFont fontContent = workbook.createFont();
		fontContent.setFontHeightInPoints((short)9);
		cellStyleContent.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		cellStyleContent.setFillPattern(CellStyle.SOLID_FOREGROUND);	
		cellStyleContent.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleContent.setBottomBorderColor(HSSFColor.WHITE.index);
		cellStyleContent.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleContent.setLeftBorderColor(HSSFColor.WHITE.index);
		cellStyleContent.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleContent.setRightBorderColor(HSSFColor.WHITE.index);
		cellStyleContent.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleContent.setTopBorderColor(HSSFColor.WHITE.index);
		cellStyleContent.setWrapText(true);
		cellStyleContent.setFont(fontContent);
		cellStyleContent.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyleContent.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		return cellStyleContent;
	}
}
