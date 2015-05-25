package br.com.gft.testautomation.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.gft.testautomation.common.login.LoginUtils;
import br.com.gft.testautomation.common.unmarshaller.StringToFile;
import br.com.gft.testautomation.common.unmarshaller.XmlToString;
import br.com.gft.testautomation.common.unmarshaller.XmlUnmarshaller;

/** Controller responsible for the importxml.jsp and for the URL uploadFile.
 * Here the XML is uploaded, converted to String to necessary modifications,
 * converted again to File, then unmarshalled to become Ticket objects. */
@Controller
public class FileUploadController {

	/* Autowire the component XmlUnmarshaller */
	@Autowired
	XmlUnmarshaller xmlUnmarshaller;

	/** Map the URL uploadFile on POST method.
	 * Receives the file from the submitted form, and the model attributes
	 * release id, project and tag, added on the GET method. */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFileHandler(@RequestParam("file") MultipartFile file,
			@ModelAttribute("id_release") Integer id_release,
			@ModelAttribute("project") String project,
			@ModelAttribute("tag") String tag, ModelMap model) {

		String fulltext;
		File newFile;

		/* Convert the file submitted from the form into a String variable */
		fulltext = XmlToString.convertToString(file);

		/* Convert the treated String into a file variable */
		newFile = StringToFile.stringToFile(fulltext);

		/* Unmarshall the XML file, sending the file and the release id to properly
		 * insert into the database table */
		xmlUnmarshaller.convertToXml(newFile, id_release);
		
		/* Redirect the user to the controller responsible for the Ticket page,
		 * where the new data from the XML file will appear */
		return "redirect:ticketsList?project="+project+"&tag="+tag+"&id_release="+id_release;
	}

	/** Map the URL uploadFile on GET method.
	 * Receives the parameters id release, project and tag, received from the page that called
	 * this controller. Also creates a ModelMap to store this parameters */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
	public String initFileUploadForm(@RequestParam("id_release") Integer id_release,
			@RequestParam("project") String project, @RequestParam("tag") String tag, ModelMap model) {
		
		/* Store the received parameters into model attributes */
		model.addAttribute("project", project);
		model.addAttribute("id_release", id_release);
		model.addAttribute("tag", tag);
		
		/* Add the logged in user to show on the page */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 
	    String names[];
	    names = LoginUtils.splitEmail(name);
	    model.addAttribute("user", names[1] + ", " + names[0]);
	    
		/* Exhibits the importxml.jsp page with the new model attributes */
		return "importxml";
	}
}
