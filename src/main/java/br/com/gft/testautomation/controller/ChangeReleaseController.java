package br.com.gft.testautomation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.gft.testautomation.common.login.LoginUtils;
import br.com.gft.testautomation.common.model.Release;
import br.com.gft.testautomation.common.model.ReleaseTicket;
import br.com.gft.testautomation.common.model.Ticket;
import br.com.gft.testautomation.common.repositories.ReleaseDao;
import br.com.gft.testautomation.common.repositories.TicketDao;

/** Controller responsible for the changerelease.jsp and the URL changeRelease.
 * Here, the user can change the release that the selected tickets belong to.  */
@Controller
public class ChangeReleaseController {

	@Autowired
	ReleaseDao releaseDao;
	
	@Autowired
	TicketDao ticketDao;

	/** Map the changeRelease URL on GET method.
	 * Receive as parameters from the previous page: release project, release tag 
	 * and the id release. These parameters are receive to properly redirect the user to the
	 * previous page after the required actions occurred. */
	@RequestMapping(value = "/changeRelease", method = RequestMethod.GET)
	public String initChangeReleaseForm(@RequestParam("id_release") Integer id_release,
			@RequestParam("project") String project,
			@RequestParam("tag") String tag, ModelMap model) {

		/* Creates two lists to populate the dropdown box and the list box of the .jsp page */
		//Creates a list using the findAll method 
	    List<Release> releaseList = releaseDao.findAll();
	    /* Creates a list using the findAllByReleaseId method */
		List<Ticket> ticketList = ticketDao.findAllByReleaseId(id_release);
		
		/* Add this lists to the model as attributes */
		model.addAttribute("releaseList", releaseList);
		model.addAttribute("ticketList", ticketList);

		/* Also add the parameters to the model attributes, if they haven't been add. */
		if (!model.containsAttribute("project")) {
			model.addAttribute("project", project);
		}
		if (!model.containsAttribute("tag")) {
			model.addAttribute("tag", tag);
		}
		if (!model.containsAttribute("id_release")) {
			model.addAttribute("id_release", id_release);
		}

		/* Add the logged in user to show on the page */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 
	    String names[];
	    names = LoginUtils.splitEmail(name);
	    model.addAttribute("user", names[1] + ", " + names[0]);
	    
		/* Also add to the model a new object of the wrapped class ReleaseTicket */
		model.addAttribute("ReleaseTicket", new ReleaseTicket());

		/* Redirect to the .jsp page */
		return "changerelease";
	}

	/** Map the changeRelease URL on POST method.
	 * Receive as parameters the required data to process the action: a String array containing
	 * the multiple selects from the listbox and the selected Release Id from the dropdown box.
	 * Also receives the model attributes from the GET method, so it will be able to redirect 
	 * to the correct page. */
	@RequestMapping(value = "/changeRelease", method = RequestMethod.POST)
	public String onChangeReleaseSubmit(@RequestParam("ticketList") String[] id_ticket,
			@RequestParam("releaseList") Integer newRelease,
			@ModelAttribute("id_release") Integer id_release,
			@ModelAttribute("project") String project,
			@ModelAttribute("tag") String tag, ModelMap model) {

		/* Add a new object of the wrapped class ReleaseTicket into the form */
		model.addAttribute("ReleaseTicket", new ReleaseTicket());

		/* For every registry in the String array, parse the Ticket Id into Integer
		 * and do the update in the Tickets table using the updateRelease method of TicketService. */
		for (int i = 0; i < id_ticket.length; i++) {
			Long id = Long.parseLong(id_ticket[i]);
			
			ticketDao.updateColumnValue(id, "id_release", "" + newRelease);
		}

		/* Redirect to the controller responsible for the previous page */
		return "redirect:ticketsList?project="+project+"&tag="+tag+"&id_release="+id_release+"&smsg=true";
	}
}
