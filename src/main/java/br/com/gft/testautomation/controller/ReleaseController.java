	package br.com.gft.testautomation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import br.com.gft.testautomation.common.login.LoginUtils;
import br.com.gft.testautomation.common.model.Release;
import br.com.gft.testautomation.common.model.Ticket;
import br.com.gft.testautomation.common.repositories.ParameterDao;
import br.com.gft.testautomation.common.repositories.ReleaseDao;
import br.com.gft.testautomation.common.repositories.TicketDao;
import br.com.gft.testautomation.common.validator.ReleaseValidator;


/** Controller responsible for the releases.jsp and the URLs getList and addRelease.
 * This controller displays the release list and controls the add release function
 * from the modal inside the releases.jsp page.*/
@Controller
/** Put on the Session (and later uses it), if not exists, the attributes: 
 * release project, release tag and release id. */
@SessionAttributes({"project", "tag", "id_release", "parameter"})
public class ReleaseController{
	
	
	/* Autowires the ReleaseDaoImpl bean */
	@Autowired
	private ReleaseDao releaseDao;
	
	/* Autowires the TicketDaoImpl bean */
	@Autowired
	private TicketDao ticketDao;
	
	/* Autowires the ParameterDaoImpl bean */
	@Autowired
	private ParameterDao parameterDao;
	
	/* Creates a instance of ReleaseValidator class */
	ReleaseValidator releaseValidator;
	
	/** Autowire this instance and set this to the controller constructor */
	@Autowired
	public ReleaseController(ReleaseValidator releaseValidator){
		this.releaseValidator = releaseValidator;
	}
	
	/** Map the URL getList on GET method.
	 * Prepares the releases.jsp page to be properly displayed with the list of Releases. */
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public ModelAndView getReleaseList(ModelMap model, SessionStatus status){
		
		/* Empty all the attributes from the session when release view is loaded. This way
		 * it prevents old values of the session to be used on new calls*/
		status.setComplete(); 
		
		/*
		Parameter param = new Parameter();
		param = parameterDao.findParameterById(1);
		System.out.println("Project_name: " + param.getProject_name() 
					+ " - importXMLButton: " + param.getImportJIRAxmlButton());
		model.addAttribute("parameter", param);*/
		
		/* Add an object to the model attribute */
		model.addAttribute("release", new Release());
		
		
		/* Add the logged in user to show on the page */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 
	    String names[];
	    names = LoginUtils.splitEmail(name);
	    model.addAttribute("user", names[1] + ", " + names[0]);
		
		/* Creates a list using the findAll method of the releaseDao bean */
	    List<Release> releaseList = releaseDao.findAll();
		
		/* Return new view using the list on the releases.jsp page */
		return new ModelAndView("releases", "releaseList", releaseList);
	}	
	
	//AJAX
	@RequestMapping(value="/getListAjax", method=RequestMethod.GET,
            		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getReleaseList() {
		
		List<Release> data = releaseDao.findAll();
		String jsonData = new Gson().toJson(data);
		System.out.println("Json: " + jsonData);
		
        return jsonData;
    }
	
	
	//AJAX Map the URL createRelease on POST method.
	@RequestMapping(value="/createRelease", method=RequestMethod.POST, 
            		produces = MediaType.APPLICATION_JSON_VALUE, 
            		consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addRelease(@RequestBody Release release,  BindingResult result) {
		
		System.out.println("Project: " + release.getName() + ", Tag: " +
				release.getTag() + ", Target_Date: " + release.getTarget_date());
		
		Integer status = 0;
		
		/* Validate the Release object and return a BindingResult object */
		releaseValidator.validate(release, result);
		
		/* If the BindingResult object has errors: */
		if(result.hasErrors()){
			/*set status variable to 1 and javascript will get this response 
			 * and show a notification to the user */
			status = 1;
		}else{
			/* If the object has no errors, insert the object in the database */
			try {
				releaseDao.saveOrUpdate(release);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}	
		return "{\"status\":"+status+"}";
    }
	
	//AJAX  Map the URL editReleaseAjax
	@RequestMapping(value="/editReleaseAjax", method=RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String editReleaseAjax(@RequestBody Release release, BindingResult result) {
		System.out.println("UPDATED - Project: " + release.getName() + ", Tag: " +
				release.getTag() + ", Target_Date: " + release.getTarget_date());
		
		Integer status = 0;
		
		/* Validate the Release object and return a BindingResult object */
		releaseValidator.validate(release, result);
		
		/* If the BindingResult object has errors: set status variable to 1 and 
		 * javascript will get this response and show a notification to the user */
		if(result.hasErrors()){
			status = 1;
		}else{
			try {
				releaseDao.saveOrUpdate(release);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}	
		return "{\"status\":"+status+"}";
	}
	
	//AJAX Map the URL deleteReleaseAjax
	@RequestMapping(value="/deleteReleaseAjax", method=RequestMethod.POST, 
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteReleaseAjax(@RequestBody Long id) {
		System.out.println("ID: " + id);
		Integer status = 0;
		List<Ticket> listTicket = ticketDao.findAllByReleaseId(id);
		
		//Check if the release has tickets assigned to it, if so it can not be deleted
		if(listTicket.size() > 0){
			status = 1;
		}
		else
			releaseDao.delete(id);
		
        return "{\"status\":"+status+"}";
    }
}