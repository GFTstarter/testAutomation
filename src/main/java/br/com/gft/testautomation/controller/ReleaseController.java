	package br.com.gft.testautomation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import br.com.gft.testautomation.common.login.LoginUtils;
import br.com.gft.testautomation.common.model.Release;
import br.com.gft.testautomation.common.model.Ticket;
import br.com.gft.testautomation.common.repositories.ReleaseDao;
import br.com.gft.testautomation.common.repositories.TicketDao;
import br.com.gft.testautomation.common.validator.ReleaseValidator;


/** Controller responsible for the releases.jsp and the URLs getList and addRelease.
 * This controller displays the release list and controls the add release function
 * from the modal inside the releases.jsp page.*/
@Controller
/** Put on the Session (and later uses it), if not exists, the attributes: 
 * release project, release tag and release id. */
@SessionAttributes({"project", "tag", "id_release"})
public class ReleaseController{
	
	
	/* Autowires the ReleaseDaoImpl bean */
	@Autowired
	private ReleaseDao releaseDao;
	
	/* Autowires the TicketDaoImpl bean */
	@Autowired
	private TicketDao ticketDao;
	
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
		
		/* Empty all the attributes from the session */
		status.setComplete();
		
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
	
	/** Map the URL addRelease on POST method. 
	 * Receives the Release object from the form on the modal, and a BindingResult object
	 * to validate the information submitted. */
	@RequestMapping(value = "/addRelease", method = RequestMethod.POST)
	public String releaseFormSubmit(@ModelAttribute("release") Release release, BindingResult result){
		
		/* Validate the Release object and return a BindingResult object */
		releaseValidator.validate(release, result);
		
		/* If the BindingResult object has errors: */
		if(result.hasErrors()){
			/* Get back to the releases.jsp page with a error message displaying */
			return "redirect:getList?msg=true";
		}else{
			/* If the object has no errors, insert the object in the database using 
			 * the save method of ReleaseDaoImpl bean, and redirect to the releases.jsp
			 * page using the GetList URL. */
			try {
				releaseDao.saveOrUpdate(release);
			} catch (Exception e) {
				e.printStackTrace();
			}			
			return "redirect:getList";
		}				
	}
	
	//AJAX
	@RequestMapping(value="/createRelease", method=RequestMethod.POST, 
            		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Release addRelease(@RequestBody Release release) {
		System.out.println("Project: " + release.getName() + ", Tag: " +
				release.getTag() + ", Target_Date: " + release.getTarget_date());
		releaseDao.saveOrUpdate(release);
		
		return release;
    }
	
	/** Map the URL editRelease, that comes from a modal, on the POST method.
	 * Receives the necessary parameters to update the Release of that row. */
	@RequestMapping(value = "/editRelease", method = RequestMethod.POST)
	public String editRelease(@ModelAttribute("release") Release release, BindingResult result){
		
		/* Validate the receive parameters, in case they are empty */
		if ((release.getProject() == "") || (release.getTag() == "") || (release.getName() == "")){
			/* If they are empty, back to the same page, now showing a message */
			return "redirect:getList?msg=true";
		}else{
			/* If they are not empty, back to the same page after the update in the database */
			//Search for a document with the id given to be updated
			
			System.out.println(release.getId_release());
			
			releaseDao.saveOrUpdate(release);
			
			return "redirect:getList";
		}		
	}
	
	//AJAX
	@RequestMapping(value="/editReleaseAjax", method=RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Release editReleaseAjax(@RequestBody Release release) {
		System.out.println("UPDATED - Project: " + release.getName() + ", Tag: " +
				release.getTag() + ", Target_Date: " + release.getTarget_date());
		releaseDao.saveOrUpdate(release);

		return release;
	}
	
	
	
	@RequestMapping(value = "/deleteRelease", method = RequestMethod.POST)
	public String deleteRelease(@RequestParam("delete_id_release") Integer id){
		
		releaseDao.delete(id);
		
		return "redirect:getList";
	}
	
	//AJAX
	@RequestMapping(value="/deleteReleaseAjax", method=RequestMethod.POST, 
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteReleaseAjax(@RequestBody Integer id) {
		System.out.println("ID: " + id);
		Integer status = 1;
		List<Ticket> listTicket = ticketDao.findAllByReleaseId(id);
		
		//Check if the release has tickets assigned to it, if so it can not be deleted
		if(listTicket.size() > 0){
			status = 0;
		}
		else
			releaseDao.delete(id);
		
        return "{\"status\":"+status+"}";
    }
}