package br.com.gft.testautomation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import br.com.gft.testautomation.common.login.LoginUtils;
import br.com.gft.testautomation.common.model.TestCases;
import br.com.gft.testautomation.common.model.Ticket;
import br.com.gft.testautomation.common.repositories.TestCaseDao;
import br.com.gft.testautomation.common.repositories.TicketDao;

/** Controller responsible for the tickets.jsp and the URLs ticketsList and updateTicket.
 * This controller displays the ticket list and controls the edit ticket function
 * from the modal inside the tickets.jsp page. */
@Controller
/** Put on the Session (and later uses it), if not exists, the attributes: 
 * release project, release tag and release id. */
@SessionAttributes({"project", "tag", "id_release"})
public class TicketController {

	
	/* Autowires the TicketDaoImpl bean */
	@Autowired
	private TicketDao ticketDao;
	
	/*Autowires the TestCasesDaoImpl bean*/
	@Autowired
	private TestCaseDao testCaseDao;
	
	/*Index variable to control which position will be accessed in the List<>*/
	private Integer i;
	
	/** Map the ticketsList URL on GET method.
	 * Receive as the parameters from the previous page: release project, release tag and and
	 * id release. These parameters are received to properly show to the user that they are
	 * accessing the correct page. */
	@RequestMapping(value = "/ticketsList", method = RequestMethod.GET)
	public ModelAndView initTicketPage(@RequestParam("project") String project,
				@RequestParam("tag") String tag, 
				@RequestParam("id_release") Integer id_release,
				ModelMap model) {
		
		/* As the pages redirects itself after editing any information, these conditions
		 * below test if the attribute hasn`t been setted to the model yet. If they hasn`t, 
		 * they will be setted. */
		if(!model.containsAttribute("project")){
			model.addAttribute("project", project);
		}
		if(!model.containsAttribute("tag")){
			model.addAttribute("tag", tag);
		}
		if(!model.containsAttribute("id_release")){
			model.addAttribute("id_release", id_release);
		}
		
		/* Add the logged in user to show on the page */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 
	    String names[];
	    names = LoginUtils.splitEmail(name);
	    model.addAttribute("user", names[1] + ", " + names[0]);
	    
		/* Set a new Ticket object every time the URL its called */
		model.addAttribute("ticket", new Ticket());		
		
		List<Ticket> ticketList = ticketDao.findAllByReleaseId(id_release);

			
		/* Return new view using the list on the tickets.page */
		return new ModelAndView("tickets", "ticketList", ticketList);
	}	
	
	//Controller to support selection of required row on page startTest
	@RequestMapping(value = "/startTestsSelected", method = RequestMethod.GET)
	public ModelAndView startTestsSelected(@RequestParam(value = "id_ticket", required = false) Long id_ticket,
			@RequestParam(value = "id_testcase", required = false) Long id_testcase,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "pre_requisite", required = false) String pre_requisite,
			@RequestParam(value = "testcase_description", required = false) String testcase_description, 
			@RequestParam(value = "results", required = false) String results,
			@RequestParam(value = "id_task", required = false) Long id_task,
			@RequestParam(value = "comments", required = false) String comments,
			@ModelAttribute("testCase") TestCases testCase,
			ModelMap model){
			
		model.addAttribute("current_task", id_task);
		model.addAttribute("id_testcase", id_testcase);
		model.addAttribute("id_ticket", id_ticket);
		model.addAttribute("status", status);
		model.addAttribute("pre_requisite", pre_requisite);
		model.addAttribute("testcase_description", testcase_description);
		model.addAttribute("results", results);
		model.addAttribute("comments", comments);
		
		List<TestCases> testCasesList = testCaseDao.findAllByTicketId(id_ticket);
		
		return new ModelAndView("startTests", "testCasesList", testCasesList);
	}
	
	//Controller to handle the first access of startTest page(through the ticket page)
	@RequestMapping(value = "/startTests", method = RequestMethod.GET)
	public String startTests(
			@RequestParam(value = "id_ticket", required = false) Long id_ticket,
			@RequestParam(value = "id_testcase", required = false) Long id_testcase,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "pre_requisite", required = false) String pre_requisite,
			@RequestParam(value = "testcase_description", required = false) String testcase_description, 
			@RequestParam(value = "results", required = false) String results,
			@RequestParam(value = "id_task", required = false) Long id_task,
			@RequestParam(value = "comments", required = false) String comments,
			@RequestParam(value = "id_release", required = false) String id_release,
			
			@ModelAttribute("testCase") TestCases testCase,
			ModelMap model) {
		
		/*Variable to control which is the next TastCase */
		TestCases nextTc = null;
		System.out.println("GET");
		
		/* Set a new TestCases object every time the URL its called */
		if(!model.containsAttribute("testCase")){
			model.addAttribute("testCase", new TestCases());		
		}
		/* Set a new Ticket object every time the URL its called
		 * This is used to edit the Run_time field */
		if(!model.containsAttribute("ticket")){
			model.addAttribute("ticket", new Ticket());	
		}
				
		System.out.println("IdRelease: "+id_release+" id_task: "+ id_task +"Id_testcase: " + id_testcase + ",status: " + status+ ",id_testcase: " + id_testcase);
		
		model.addAttribute("id_testcase", id_testcase);
		model.addAttribute("id_ticket", id_ticket);
		model.addAttribute("status", status);
		model.addAttribute("pre_requisite", pre_requisite);
		model.addAttribute("testcase_description", testcase_description);
		model.addAttribute("results", results);
		model.addAttribute("comments", comments);
		
		List<TestCases> testCasesList = testCaseDao.findAllByTicketId(id_ticket);
		
		//Take the first record of the list to start the startTest page.
		try{
			nextTc = testCasesList.get(0);
		}
		catch (Exception e){
			if(id_release == null){
				return "redirect:startTests?nmsg=1";
			}
			else
				return null;
		}
		model.addAttribute("current_task", nextTc.getTask_id());
		model.addAttribute("testCasesList", testCasesList);
		
		/*Avoid infinite redirect loop when calling this controller,
		 * by checking if any value get from the URL is null, this way the redirection
		 * will be executed only once*/
		if(id_testcase == null){
			return "redirect:startTests?id_testcase="+nextTc.getTestcase_id()+"&id_ticket="
					+nextTc.getId_ticket()+"&status="+nextTc.getStatus()+
					"&pre_requisite="+nextTc.getPre_requisite()+"&testcase_description="
					+nextTc.getTestcase_description()+"&results="+nextTc.getResults()+"&comments="+nextTc.getComments();
		}
		return null;
	}
	
	@RequestMapping(value = "/startTestsP", method = RequestMethod.POST)
	public ModelAndView addStartTests(@RequestBody
			@RequestParam String action,
			@ModelAttribute("comments") String comments,
			@ModelAttribute("status") String newStatus,
			@ModelAttribute("id_ticket") Long id_ticket,
			@ModelAttribute("id_testcase") Long id_testcase,
			@ModelAttribute("testCase") TestCases testCase,
			ModelMap model) {
		
		System.out.println("POST: id_testcase: " + id_testcase + " Status: " + newStatus);
		
		/*Variable to control which is the next TastCase */
		TestCases nextTc = null;
		
		/* Set a new TestCases object every time the URL its called */
		if(!model.containsAttribute("testCase")){
			model.addAttribute("testCase", new TestCases());		
		}
		/* Set a new Ticket object every time the URL its called
		 * This is used to edit the Run_time field */
		if(!model.containsAttribute("ticket")){
			model.addAttribute("ticket", new Ticket());	
		}
		
		/*Recognise which button was clicked (passed or failed)
		 *through the (name="action") parameter on <button> tag int the starTest.jsp page
		 *Here in the controller the (@RequestParam String action) bind this information*/
		if(action.equals("passed") ){
		     System.out.println("passed");
		     testCaseDao.updateTwoColumnValue(id_testcase, "status", "Passed", "comments", comments);
	    }
	    else if(action.equals("failed")){
	    	System.out.println("failed");
	    	testCaseDao.updateTwoColumnValue(id_testcase, "status", "Failed", "comments", comments);
	    }
		
		List<TestCases> testCasesList = testCaseDao.findAllByTicketId(id_ticket);
		//Get index from testCases being manage at the moment
		for(TestCases tc : testCasesList){
			if(tc.getTestcase_id() == id_testcase){
				i = testCasesList.indexOf(tc);
				System.out.println("Index: " + i);
			}
		}
		
		//Take the next TestCase and return it
		//cath handle indexOutOfBound exception, and reset the "cursor" of the table
		try {
			nextTc = testCasesList.get(i+1);
		} catch (Exception e) {
			nextTc = testCasesList.get(0);
		}
		
		model.addAttribute("current_task", nextTc.getTask_id());
		model.addAttribute("id_testcase", nextTc.getTestcase_id());
		model.addAttribute("id_ticket", nextTc.getId_ticket());
		model.addAttribute("status", nextTc.getStatus());
		model.addAttribute("pre_requisite", nextTc.getPre_requisite());
		model.addAttribute("testcase_description", nextTc.getTestcase_description());
		model.addAttribute("results", nextTc.getResults());
		model.addAttribute("comments", nextTc.getComments());
		
		return new ModelAndView("startTests", "testCasesList", testCasesList);
	}
	
	/** Map the refreshTicket URL on GET method.
	 * Properly refresh all the information that will be displayed on the next page,
	 * receiving the parameters that also will be sent to the next page.  */
	@RequestMapping(value = "/refreshTicket", method = RequestMethod.GET)
	public String refreshPage(@RequestParam("project") String project, @RequestParam("tag") String tag, 
								@RequestParam("id_release") Integer id_release){
		
		List<Ticket> ticketList = ticketDao.findAllByReleaseId(id_release);
		
		/* Change the color of the JIRA link if there is a test case with status FAILED within
		 * the ticket displayed in each row.
		 * Creates a list of TestCases for each Ticket in the ticket list and go through.
		 * If there is a test case with status FAILED, set the TestCaseStatus attribute of
		 * the ticket object to match the current status. This way, it will be displayed in red */
		for (Ticket ticket : ticketList){
			Long id = ticket.getId_ticket();					
			
			List<TestCases> testCasesList = testCaseDao.findAllByTicketId(id);
			for (TestCases testCases : testCasesList){				
				if(testCases.getStatus().equals("Failed")){		
					//Update the TestCase_status field
					ticketDao.updateColumnValue(id, "testcase_status", "Failed");					
					
					//Then break the loop, because there's no need to keep going in this list
					break;					
				}else{
					//Update the TestCase_status field
					ticketDao.updateColumnValue(id, "testcase_status", "Pending/Passed");	
				}
			}
		}	
		
		/* Redirect to the ticketList URL using the received parameters */
		return "redirect:ticketsList?project="+project+"&tag="+tag+"&id_release="+id_release;
	}
	
	/** Map the URL updateTicket on POST method. 
	 * Receive as parameters the following attributes to be edited: ticket developer,
	 * ticket tester and ticket status. The others parameters are used to properly redirect
	 * the page after the edit happens. */
	@RequestMapping(value = "/updateTicket", method = RequestMethod.POST)
	public String updateTicket(@ModelAttribute("project") String project,
			@ModelAttribute("tag") String tag,
			@ModelAttribute("id_release") Integer id_release,
			@ModelAttribute("ticket") Ticket ticket,
			@RequestParam("id_ticket") Integer id_ticket,
			@RequestParam("tester") String tester,
			@RequestParam("developer") String developer,
			@RequestParam("status") String status) {
		
		
		ticketDao.saveOrUpdate(ticket);				
		
		/* Redirect to the ticketsList URL using the received parameters */
		return "redirect:ticketsList?project="+project+"&tag="+tag+"&id_release="+id_release;
	}
	
	
	//Delete info
		@RequestMapping(value = "/deleteTicket", method = RequestMethod.POST)
			public String deleteTicket(@RequestParam("delete_id_ticket") Long id) {
			
				ticketDao.delete(id);
			
			return "redirect:ticketsList";
		}
	/** Map the URL back on GET method.
	 * If the user hits the back button on the end of the page, the session will be erased,
	 * so this way the attributes Project and Tag don't pass to another page.
	 * After the session become empty, the user gets redirect to the page before this one. */
	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public String back(SessionStatus status){
		
		//Empty the session attributes
		status.setComplete();
		
		//Redirect to the previous page
		return "redirect:getList";
	}
}
