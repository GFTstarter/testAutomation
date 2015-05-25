package br.com.gft.testautomation.common.export;

import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/** Class responsible for creating the custom colours for the exported excel document */
public class ExcelPalette {

	/** Method that generate the custom colours */
	public HSSFPalette generatePalette(HSSFWorkbook workbook){
		/* Creates a custom palette */
		HSSFPalette customPalette =  workbook.getCustomPalette();
		
		/* Create custom colours based on RGB numbers and assign them into existing colours of the palette */
		customPalette.setColorAtIndex(HSSFColor.PALE_BLUE.index, (byte) 185, (byte) 201, (byte) 237);
		customPalette.setColorAtIndex(HSSFColor.LIGHT_TURQUOISE.index, (byte) 229, (byte) 236, (byte) 251);
		
		/* Return the new custom palette */
		return customPalette;
	}
}
