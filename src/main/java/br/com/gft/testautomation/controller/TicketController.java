package br.com.gft.testautomation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import br.com.gft.testautomation.common.model.Parameter;
import br.com.gft.testautomation.common.model.TestCases;
import br.com.gft.testautomation.common.model.Ticket;
import br.com.gft.testautomation.common.repositories.ParameterDao;
import br.com.gft.testautomation.common.repositories.TestCaseDao;
import br.com.gft.testautomation.common.repositories.TicketDao;
import br.com.gft.testautomation.common.validator.TicketValidator;

/** Controller responsible for the tickets.jsp and the URLs ticketsList and updateTicket.
 * This controller displays the ticket list and controls the edit ticket function
 * from the modal inside the tickets.jsp page. */
@Controller
/** Put on the Session (and later uses it), if not exists, the attributes: 
 * release project, release tag and release id. */
@SessionAttributes({"project", "tag", "id_release", "parameter"})
public class TicketController {
	
	@Autowired
	private JavaMailSender mailSender;
	
	/* Autowires the TicketDaoImpl bean */
	@Autowired
	private TicketDao ticketDao;
	
	/*Autowires the TestCasesDaoImpl bean*/
	@Autowired
	private TestCaseDao testCaseDao;
	
	/* Autowires the ParameterDaoImpl bean */
	@Autowired
	private ParameterDao parameterDao;
	
	/* Creates a instance of ReleaseValidator class */
	TicketValidator ticketValidator;
	
	/** Autowire this instance and set this to the controller constructor */
	@Autowired
	public TicketController(TicketValidator ticketValidator){
		this.ticketValidator = ticketValidator;
	}
	
	/*Index variable to control which position will be accessed in the List<>*/
	private Integer i;
	
	@RequestMapping(value = "/sendMail",method = RequestMethod.GET)
    public String doSendEmail(@RequestParam("developer") String developer, 
    		@RequestParam("jira") String jira) {
		
        // takes input from e-mail form
        String recipientAddress = "Luan.de-Souza@gft.com";
        String subject = "Email test";
        String message = "Email teste, Develelper: " + developer + ", JIRA: " + jira;
         
        System.out.println("Message: " + message);
        
        // creates a simple e-mail object
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
         
        // sends the e-mail
        mailSender.send(email);
         
        // forwards to the view named "tickets"
        return "tickets";
    }
	
	@RequestMapping(value = "/addTicket", method = RequestMethod.POST)
	public String releaseFormSubmit(@ModelAttribute("ticket") Ticket ticket, BindingResult result,
			@RequestParam("project") String project,
			@RequestParam("tag") String tag, 
			@RequestParam("id_release") Integer id_release){
		
		System.out.println("idRelease: " + id_release);
		
		ticket.setId_release(id_release);
		//TODO: testcase_status being passed as empty but testcase_status must be changed to Null in the database
		ticket.setTestcase_status("");
		
		/* Validate the Release object and return a BindingResult object */
		ticketValidator.validate(ticket, result);
		
		/* If the BindingResult object has errors: */
		if(result.hasErrors()){
			System.out.println("Result hasErrors.");
			/* Get back to the releases.jsp page with a error message displaying */
			return "redirect:ticketsList?project="+project+"&tag="+tag+"&id_release="+id_release+"&msg=true";
		}else{
			/* If the object has no errors, insert the object in the database using 
			 * the save method of ReleaseDaoImpl bean, and redirect to the releases.jsp
			 * page using the GetList URL. */
			try {
				ticketDao.saveOrUpdate(ticket);
			} catch (Exception e) {
				e.printStackTrace();
			}			
			return "redirect:refreshTicket";
		}				
	}
	
	
	//AJAX
	@RequestMapping(value="/createTicket", method=RequestMethod.POST, 
				produces = MediaType.APPLICATION_JSON_VALUE, 
				consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addTicket(@RequestBody Ticket ticket,  BindingResult result) {
		
		System.out.println("Jira: " + ticket.getJira() + ", Description: " +
		ticket.getDescription() + ", Environment: " + ticket.getEnvironment() + ", Developer: " + ticket.getDeveloper()
		+ ", Tester: " + ticket.getTester() + ", Status: " + ticket.getTestcase_status());
		
		Integer status = 0;
		
		/* Validate the Release object and return a BindingResult object */
		ticketValidator.validate(ticket, result);
		
		/* If the BindingResult object has errors: */
		if(result.hasErrors()){
			/*set status variable to 1 and javascript will get this response 
			 * and show a notification to the user */
			status = 1;
		}else{
			/* If the object has no errors, insert the object in the database */
			try {
				ticketDao.saveOrUpdate(ticket);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}	
		return "{\"status\":"+status+"}";
    }
	
	/** Map the ticketsList URL on GET method.
	 * Receive as the parameters from the previous page: release project, release tag and and
	 * id release. These parameters are received to properly show to the user that they are
	 * accessing the correct page. */
	@RequestMapping(value = "/ticketsList", method = RequestMethod.GET)
	public ModelAndView initTicketPage(@RequestParam("project") String project,
				@RequestParam("tag") String tag, 
				@RequestParam("id_release") Long id_release,
				ModelMap model) {
		
		/*These conditions set back the attributes of the session, because they are cleared when the controller mapped 
		 * as /getList from ReleaseController is called and also when the back button is pressed. This way these informations
		 * can still be used throughout the views*/
		if(!model.containsAttribute("project")){
			model.addAttribute("project", project);
		}
		if(!model.containsAttribute("tag")){
			model.addAttribute("tag", tag);
		}
		if(!model.containsAttribute("id_release")){
			model.addAttribute("id_release", id_release);
		}
		if(!model.containsAttribute("parameter")){
			Parameter param = new Parameter();
			param = parameterDao.findParameterById(1);
			model.addAttribute("parameter", param);
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
	
	
	//AJAX to load tickets DataTable
	@RequestMapping(value="/ticketsListAjax", method=RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getReleaseList(@RequestBody @RequestParam("id_release") Long id_release) {
	
		System.out.println("idRelease: " + id_release);	
		List<Ticket> data = ticketDao.findAllByReleaseId(id_release);
		
		String jsonData = new Gson().toJson(data);
		System.out.println("Json: " + jsonData);
		
		return jsonData;
	}

	
	/*Controller to support selection of required row on page startTest. It had to be separate controller for proper control of which row to highlight*/  
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
			
		/*Add the below attributes to the model of this controller, 
		 * each addition will be passed to the view "startTests" called in the first parameter
		 * of  return new ModelAndView()*/
		model.addAttribute("current_task", id_task);
		model.addAttribute("id_testcase", id_testcase);
		model.addAttribute("id_ticket", id_ticket);
		model.addAttribute("status", status);
		model.addAttribute("pre_requisite", pre_requisite);
		model.addAttribute("testcase_description", testcase_description);
		model.addAttribute("results", results);
		model.addAttribute("comments", comments);
		/*The atributte -> ("testCasesList", testCasesList)
		 * could also be added as -> model.addAttribute("testCasesList", testCasesList);
		 * */
		
		
		/*Load list of testCases from idTicket, to reload the page */
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
				
		System.out.println("IdRelease: "+id_release+" - id_task: "+ id_task +" - Id_testcase: " + id_testcase + " - status: " + status+ " - id_testcase: " + id_testcase);
		
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
			//Checks if there is no data to start the page "startTests", if there isn't,
			//the parameter nmsg will be set to 1
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
	
	/*Controller called when the button to PASS or FAIL a test is clicked*/
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
	/*NOT-BEING-USED-TO-BE-DELETED*/
	@RequestMapping(value = "/refreshTicket", method = RequestMethod.GET)
	public String refreshPage(@RequestParam("project") String project, @RequestParam("tag") String tag, 
								@RequestParam("id_release") Long id_release){
		
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
	
	//Controller that receives an AJAX call to EDIT a ticket registry
	@RequestMapping(value="/editTicketAjax", method=RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String editTicketAjax(@RequestBody Ticket ticket, BindingResult result) {
		
		Integer status = 0;
		
		/* Validate the Release object and return a BindingResult object */
		ticketValidator.validate(ticket, result);
		
		/* If the BindingResult object has errors: set status variable to 1 and 
		 * javascript will get this response and show a notification to the user */
		if(result.hasErrors()){
			status = 1;
		}else{
			try {
				ticketDao.saveOrUpdate(ticket);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}	
		return "{\"status\":"+status+"}";
	}
		
		
	//Controller that receives an AJAX call to DELETE a ticket registry
	@RequestMapping(value="/deleteTicketAjax", method=RequestMethod.POST, 
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteTicketAjax(@RequestBody Long id) {
		System.out.println("ID: " + id);
		Integer status = 0;
		
		List<TestCases> listTestCase = testCaseDao.findAllByTicketId(id);
		//Check if the ticket has test cases assigned to it, if so it can not be deleted
		if(listTestCase.size() > 0){
			status = 1;
		}
		else
			ticketDao.delete(id);
		
        return "{\"status\":"+status+"}";
    }
	/** Map the URL back on GET method.
	 * If the user hits the back button on the end of the page, the session will be erased,
	 * so this way the attributes Project and Tag don't pass to another page.
	 * After the session become empty, the user gets redirect to the page before this one. */
	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public String back(SessionStatus status){
		
		//Empty the session attributes
		/* Cleaning operation of the session's attributes removed, to keep application's parameters
		 * throughout the views.*/
		//status.setComplete();
		
		//Redirect to the previous page
		return "redirect:getList";
	}
}
