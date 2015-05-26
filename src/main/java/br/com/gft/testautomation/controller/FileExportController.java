package br.com.gft.testautomation.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import br.com.gft.testautomation.common.export.ExcelDocument;
import br.com.gft.testautomation.common.export.ExcelUtils;

/** Controller responsible for the URL getExcel/{filename}.
 * Here, the MS-Excel document will be created and downloaded. */
@Controller
@RequestMapping("/getExcel/{filename}")
public class FileExportController {
	
	/* Autowire the ExcelDocument bean to be able to create a MS-Excel document */
	@Autowired
	ExcelDocument excelDoc;	
	
	
	
	/** Method that access the ExcelDocument methods to create and download a MS-Excel document.
	 * The method returns the newly created document as a view, so it returns as a downloadable file.
	 * Receives parameters to properly populate the MS-Excel using the data from the previous page.
	 * These parameters are: The ticket id, the JIRA code, the project tag, the ticket environment,
	 * developer, tester, description and time to run all tests. Also receives a Map<String,Object>
	 * model, a HSSFWorkbook, and a servlet request/response. */
	@RequestMapping(method  = RequestMethod.GET)
	protected View generateExcel(@RequestParam("id_ticket") final Integer id_ticket, 
		@RequestParam("jira") final String jira,
		@RequestParam("tag") final String tag, 
		@RequestParam("environment") final String environment,
		@RequestParam("developer") final String developer,
		@RequestParam("tester") final String tester,
		@RequestParam("description") final String description,
		@RequestParam("run_time") final String run_time,
		Map<String, Object> model,
		HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response){		
		
		/* First, uses the createHeader method to create the document header using the parameters
		 * received. Then, creates the document itself. */
		try {
			excelDoc.createHeader(id_ticket, jira, tag, description, environment, developer, tester, run_time,false);
			excelDoc.buildExcelDocument(model, workbook, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}				
		
		/* In this controller, we won't use the temporary files created in the ExcelDocument method.
		 * So, using the deleteFolder method from the ExcelUtils class, the temporary folder will
		 * be cleaned. */
		File outputFolder = new File("/resources");
		
		ExcelUtils.deleteFolder(outputFolder);
		
		//Returns the document as a view, so Spring can download it
		return excelDoc;		
	}
}
