package br.com.gft.testautomation.common.export;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hpsf.PropertySetFactory;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import br.com.gft.testautomation.common.model.TestCases;
import br.com.gft.testautomation.common.repositories.TestCaseDao;

/** Class responsible for generating an MS-Excel file */
@SuppressWarnings("deprecation") //CellRangeAddress is deprecated
public class ExcelDocument extends AbstractExcelView{

	@Autowired
	TestCaseDao testCaseDao;

	/* Autowire the PopulateHeader bean */
	@Autowired
	PopulateHeader populateHeader;	

	/** Method that creates a PopulateHeader object, setting the values received in the 
	 * appropriated attributes.  */
	public void createHeader(long id_ticket, String jira, String release, String description, String environment, String developer,
			String tester, String time, boolean fileBlank){

		populateHeader.setExcelIdTicket(id_ticket);
		populateHeader.setExcelJira(jira);
		populateHeader.setExcelRelease(release);
		populateHeader.setExcelDescription(description);
		populateHeader.setExcelEnvironment(environment);
		populateHeader.setExcelDeveloper(developer);
		populateHeader.setExcelTester(tester);
		populateHeader.setExcelTime(time);
		populateHeader.setFileBlank(fileBlank);
	}

	/** Method that actually creates the MS-Excel document. This method came with the extending
	 * of the AbstractExcelView class */
	public void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/* Creates a list of the TestCases to put on the MS-Excel document */
		Query query = new Query();
		query.addCriteria(Criteria.where("id_ticket").in(populateHeader.getExcelIdTicket()));
		List<TestCases> testCasesList = testCaseDao.findAllByTicketId(populateHeader.getExcelIdTicket());
		
		

		/* Creating a worksheet */
		HSSFSheet sheet = workbook.createSheet("Test Case");	

		/* Creating additional summary information */
		workbook.createInformationProperties();
		SummaryInformation summaryInfo = PropertySetFactory.newSummaryInformation();				
		summaryInfo = workbook.getSummaryInformation();
		summaryInfo.setAuthor("GFT Brasil");

		/* Instantiates objects that contain the custom style and the custom colours  */
		ExcelStyles excelStyles = new ExcelStyles();
		ExcelPalette excelPalette = new ExcelPalette();

		/* Putting styles and custom colours */	

		//Custom colors via HSSFPalette
		@SuppressWarnings("unused")
		HSSFPalette pallete = excelPalette.generatePalette(workbook);

		//Left column style
		HSSFCellStyle cellStyleLeft = excelStyles.leftStyle(workbook);

		//First row style
		HSSFCellStyle cellStyleFirstRow = excelStyles.firstRowStyle(workbook);

		//Panel header style
		HSSFCellStyle cellStyleHeader = excelStyles.panelHeaderStyle(workbook);

		//Common content style
		HSSFCellStyle cellStyleContent = excelStyles.contentStyle(workbook);

		/* Creating the cells and putting the content */

		//Panel header row and cells			
		Row panelHeader = sheet.createRow((short) 0);
		Cell cellPanelHeader = panelHeader.createCell((short) 0);
		//Content and style
		cellPanelHeader.setCellValue(populateHeader.getExcelJira() + " Test Case");
		cellPanelHeader.setCellStyle(cellStyleFirstRow);			
		//Merging the cells
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

		//Panel row and cells
		HSSFRow panel0 = sheet.createRow(1);
		HSSFCell cellPanel00 = panel0.createCell(0);
		//Content and style
		cellPanel00.setCellStyle(cellStyleLeft);		
		cellPanel00.setCellValue("Release:");
		HSSFCell cellPanel01 = panel0.createCell(2);
		cellPanel01.setCellStyle(cellStyleContent);
		cellPanel01.setCellValue(populateHeader.getExcelRelease());
		//Merging the cells
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));

		HSSFCell cellPanel02 = panel0.createCell(3);
		//Content and style
		cellPanel02.setCellStyle(cellStyleContent);		
		cellPanel02.setCellValue(populateHeader.getExcelJira() + " - " + populateHeader.getExcelDescription());
		//Merging the cells
		sheet.addMergedRegion(new CellRangeAddress(1, 5, 3, 7));

		//Second panel row and cells
		HSSFRow panel1 = sheet.createRow(2);
		HSSFCell cellPanel10 = panel1.createCell(0);
		//Content and style
		cellPanel10.setCellStyle(cellStyleLeft);
		cellPanel10.setCellValue("Environment:");

		HSSFCell cellPanel11 = panel1.createCell(2);
		//Content and style
		cellPanel11.setCellStyle(cellStyleContent);
		cellPanel11.setCellValue(populateHeader.getExcelEnvironment());
		//Merging the cells
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));

		//Third panel row and cells
		HSSFRow panel2 = sheet.createRow(3);
		HSSFCell cellPanel20 = panel2.createCell(0);
		//Content and style
		cellPanel20.setCellStyle(cellStyleLeft);
		cellPanel20.setCellValue("Developer:");

		HSSFCell cellPanel21 = panel2.createCell(2);
		//Content and Style
		cellPanel21.setCellStyle(cellStyleContent);
		cellPanel21.setCellValue(populateHeader.getExcelDeveloper());
		//Merging the cells
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 1));

		//Fourth panel row and cells
		HSSFRow panel3 = sheet.createRow(4);
		HSSFCell cellPanel30 = panel3.createCell(0);
		//Content and style
		cellPanel30.setCellStyle(cellStyleLeft);
		cellPanel30.setCellValue("Tester:");

		HSSFCell cellPanel31 = panel3.createCell(2);
		//Content and style
		cellPanel31.setCellStyle(cellStyleContent);
		cellPanel31.setCellValue(populateHeader.getExcelTester());
		//Merging the cells
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 1));

		//Fifth panel row and cells
		HSSFRow panel4 = sheet.createRow(5);
		HSSFCell cellPanel40 = panel4.createCell(0);
		//Content and style
		cellPanel40.setCellStyle(cellStyleLeft);
		cellPanel40.setCellValue("Time to run all tests:");

		HSSFCell cellPanel41 = panel4.createCell(2);
		//Content and style
		cellPanel41.setCellStyle(cellStyleContent);
		cellPanel41.setCellValue(populateHeader.getExcelTime());
		//Merging the cells
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 1));

		//Test cases header row
		HSSFRow header = sheet.createRow(6);

		HSSFCell cell0 = header.createCell(0);
		//Content and style
		cell0.setCellStyle(cellStyleHeader);
		cell0.setCellValue("Task Id");

		HSSFCell cell1 = header.createCell(1);	
		//Content and style
		cell1.setCellStyle(cellStyleHeader);
		cell1.setCellValue("Status");

		HSSFCell cell2 = header.createCell(2);
		//Content and style
		cell2.setCellStyle(cellStyleHeader);
		cell2.setCellValue("Tested By");

		HSSFCell cell3 = header.createCell(3);
		//Content and style
		cell3.setCellStyle(cellStyleHeader);
		cell3.setCellValue("Tested On");

		HSSFCell cell4 = header.createCell(4);
		//Content and style
		cell4.setCellStyle(cellStyleHeader);
		cell4.setCellValue("Pre-Requisite");

		HSSFCell cell5 = header.createCell(5);
		//Content and style
		cell5.setCellStyle(cellStyleHeader);
		cell5.setCellValue("Description");

		HSSFCell cell6 = header.createCell(6);
		//Content and style
		cell6.setCellStyle(cellStyleHeader);
		cell6.setCellValue("Expected Results");

		HSSFCell cell7 = header.createCell(7);
		//Content and style
		cell7.setCellStyle(cellStyleHeader);
		cell7.setCellValue("Comments");				

		//Next row
		int rowNum = 7;
		int lin = 0;
		/* Uses the TestCases list to add data to the MS-Excel document.
		 * Each row of the list will be add to a new row at the document, using the
		 * addValueToCell method.
		 * For each row added, the method breakLine will be executed once, using the 
		 * TestCase object. */
		for (TestCases testCase : testCasesList) {
			ExcelUtils.breakLine(testCase);
			HSSFRow row1 = sheet.createRow(rowNum++);
			lin++;
			ExcelUtils.addValueToCell(row1, 0, lin, cellStyleContent);		
			ExcelUtils.addValueToCell(row1, 1, populateHeader.isFileBlank() ? "Pending" : testCase.getStatus(), cellStyleContent);	
			ExcelUtils.addValueToCell(row1, 2, populateHeader.isFileBlank() ? "" : testCase.getTested_by(), cellStyleContent);	
			ExcelUtils.addValueToCell(row1, 3, populateHeader.isFileBlank() ? "" : testCase.getTested_on(), cellStyleContent);	
			ExcelUtils.addValueToCell(row1, 4, testCase.getPre_requisite(), cellStyleContent);					
			ExcelUtils.addValueToCell(row1, 5, testCase.getTestcase_description(), cellStyleContent);
			ExcelUtils.addValueToCell(row1, 6, testCase.getResults(), cellStyleContent);	
			ExcelUtils.addValueToCell(row1, 7, populateHeader.isFileBlank() ? "" : testCase.getComments(), cellStyleContent);	
		}

		//Fix the width of the columns
		sheet.setColumnWidth(0, 2700);				
		sheet.setColumnWidth(1, 2200);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);			
		sheet.setColumnWidth(4, 10000);
		sheet.setColumnWidth(5, 12000);
		sheet.setColumnWidth(6, 10000);
		sheet.setColumnWidth(7, 10000);

		/* Creates the MS-Excel document in a temporary folder, in case they are used by
		 * another method. */
		//Create the folder
		File tempFolder = new File("C:\\TEMP\\testapptemp\\files");
		tempFolder.mkdir();
		//Create the file
		String fileName = populateHeader.isFileBlank() ? "_Test_Plan_blank.xls" : "_Test_Plan.xls";
		FileOutputStream fos = new FileOutputStream("C:\\TEMP\\testapptemp\\files\\"+populateHeader.getExcelJira()+ fileName);
		workbook.write(fos);
		fos.close();
	}			
}


