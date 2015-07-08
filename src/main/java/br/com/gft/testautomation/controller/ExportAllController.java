package br.com.gft.testautomation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
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

import br.com.gft.testautomation.common.export.CompressFiles;
import br.com.gft.testautomation.common.export.ExcelDocument;
import br.com.gft.testautomation.common.export.ExcelUtils;
import br.com.gft.testautomation.common.model.Ticket;
import br.com.gft.testautomation.common.repositories.TicketDao;

/** Class responsible for the getZip URL.
 * Here, a zip will be generated with all the possible MS-Excel documents that can exist
 * within the tickets of a release */
@Controller
public class ExportAllController {
	
	/* Autowire the ExcelDocument bean to generate a MS-Excel document */
	@Autowired
	ExcelDocument excelDoc;

	@Autowired
	ExcelDocument excelBlank;
	
	@Autowired
	TicketDao ticketDao;
	
	
	/** Map the getZip URL on GET method.
	 * Here, the .zip file will be generated and shown in the directory.
	 * Receive as parameters the release ID, the release tag, the release project,
	 * a Map<String, Object> model and a request/response . */
	@RequestMapping(value = "/getZip", method = RequestMethod.GET)
	protected void generateZip(@RequestParam("id_release") Long id_release,
			@RequestParam("tag") String tag,
			@RequestParam("project") String project,
			Map<String, Object> model,			
			HttpServletRequest request,
            HttpServletResponse response) throws Exception{

		/* Creates a list using the findAllByReleaseId */
		List<Ticket> ticketList = ticketDao.findAllByReleaseId(id_release);
		
		//Creates a list of files to store the MS-Excel documents 
		List<File> files = new ArrayList<File>();	
		
		String tempPath = request.getRealPath("/WEB-INF/temp");
		tempPath = tempPath + File.separator;
		//String tempPath = "C:\\Users\\lnsr\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\automacaotestes\\WEB-INF\\temp\\";

		/* For each Ticket object in the ticket list, check if the TestCaseStatus is Failed.
		 * If it is, it's not supposed to export.  */
		for (Ticket ticket : ticketList){
			if (ExcelUtils.isEqual(ticket.getTestcase_status(), "Failed")){
				System.out.println(ticket.getId_ticket() + " Status Failed, not supposed to export.");
			}else{
				/* If it is not, create a new HSSFWorkbook and generate a new MS-Excel file,
				 * storing it in the files list */
				HSSFWorkbook workbook = new HSSFWorkbook();
				excelDoc.createHeader(ticket.getId_ticket(), ticket.getJira(), tag, ticket.getDescription(), ticket.getEnvironment(), ticket.getDeveloper(), ticket.getTester(), ticket.getRun_time(),false);
				excelDoc.buildExcelDocument(model, workbook, request, response);
				files.add(new File(tempPath+"files"+File.separator+ticket.getJira()+"_Test_Plan.xls"));
				
				HSSFWorkbook workBlank = new HSSFWorkbook();
				excelBlank.createHeader(ticket.getId_ticket(), ticket.getJira(), tag, ticket.getDescription(), ticket.getEnvironment(), ticket.getDeveloper(), ticket.getTester(), ticket.getRun_time(),true);
				excelBlank.buildExcelDocument(model, workBlank, request, response);
				files.add(new File(tempPath+"files"+File.separator+ticket.getJira()+"_Test_Plan_blank.xls"));
				
			}
		}
		
		//Creating a folder to keep the .zip file
		File tempFolder = new File(tempPath+"exportedExcel"); 
		tempFolder.mkdir();		
		//Creating a .zip file
		File zipFile = new File(tempPath+"exportedExcel"+File.separator+project+"_"+tag+".zip");
		/*Accessing the zipIt method from the CompressFiles class, to create a .zip file
		 * using the list of files */
		CompressFiles.zipIt(zipFile, files);	
		
		/* Access the temporary folder where the MS-Excel documents were stored, and delete 
		 * all the files in it */
		File outputFolder = new File(tempPath+"files");		
		ExcelUtils.deleteFolder(outputFolder);		
		
		/*-------- Download .zip ----------*/
		//get ServletContext
		ServletContext context = request.getServletContext();
		
		//FileInputStream from generated .zip in temporary folder
		File downloadFile = new File(tempPath+"exportedExcel"+File.separator+project+"_"+tag+".zip");
		FileInputStream inputStream = new FileInputStream(downloadFile);
		
		// get MIME type of the file
        String mimeType = context.getMimeType(tempPath+"exportedExcel"+File.separator+project+"_"+tag+".zip");
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
 
        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
 
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[1024];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        } 
        
        System.out.println("Download .zip successfully");
        inputStream.close();
        outStream.close();      
	}      
}
