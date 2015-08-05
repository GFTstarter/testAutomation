package br.com.gft.testautomation.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import org.springframework.web.servlet.ModelAndView;

import br.com.gft.testautomation.common.login.LoginUtils;
import br.com.gft.testautomation.common.model.TestCases;
import br.com.gft.testautomation.common.model.Ticket;
import br.com.gft.testautomation.common.repositories.TestCaseDao;
import br.com.gft.testautomation.common.repositories.TicketDao;
import br.com.gft.testautomation.common.validator.TestCaseValidator;

import com.google.gson.Gson;

/** Controller responsible for the testcase.jsp and the URLS testCases and updateTestCases.
 * This controller displays the testcases list, the form to add a new test case, and
 * the modal to edit the data. */
@Controller
/** Put on the Session (and later uses it), if not exists, the attributes: 
 * release tag and release id. */
@SessionAttributes({"tag", "id_release", "parameter"})
public class TestCaseController {
	
	/* Autowires the TestCaseDaoImpl bean */
	@Autowired
	TestCaseDao testCaseDao;
	
	@Autowired 
	TicketDao ticketDao;
	
	/* Creates a instance of ReleaseValidator class */
	TestCaseValidator testCaseValidator;
	
	/** Autowire this instance and set this to the controller constructor */
	@Autowired
	public TestCaseController(TestCaseValidator testCaseValidator){
		this.testCaseValidator = testCaseValidator;
	}
	
		
	//AJAX to load data of testCases's DataTable
	@RequestMapping(value="/testCasesAjax", method=RequestMethod.GET,
            		produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String getReleaseList(@RequestBody @RequestParam("ticketId") Long id) {
		List<TestCases> data = testCaseDao.findAllByTicketId(id);
		String jsonData = new Gson().toJson(data);
		System.out.println("Json: " + jsonData);
		
        return jsonData;
    }
	
	@RequestMapping(value = "/testCases", method = RequestMethod.GET)
	public ModelAndView initTestCasesPage(@RequestParam("description") String description,
			@ModelAttribute("tag") String tag,
			@RequestParam("jira") String jira,
			@RequestParam("environment") String environment,
			@RequestParam("developer") String developer,
			@RequestParam("tester") String tester, 
			@RequestParam("id_ticket") Long id_ticket,
			@RequestParam("run_time") String run_time,
			@RequestParam(value = "status", required =false) String status,
			
			@ModelAttribute("testCase") TestCases testCase,
			
			@ModelAttribute("ticket") Ticket ticket,  ModelMap model) {
		
		/* As the pages redirects itself after editing and adding any information, these 
		 * conditions below test if the attribute hasn`t been setted to the model yet. If 
		 * they hasn`t, they will be setted. */
		if(!model.containsAttribute("jira")){
			model.addAttribute("jira", jira);
		}
		if(!model.containsAttribute("description")){
			model.addAttribute("description", description);
		}
		if(!model.containsAttribute("environment")){
			model.addAttribute("environment", environment);
		}
		if(!model.containsAttribute("developer")){
			model.addAttribute("developer", developer);		
		}
		if(!model.containsAttribute("tester")){
			model.addAttribute("tester", tester);
		}
		if(!model.containsAttribute("id_ticket")){
			model.addAttribute("id_ticket", id_ticket);		
		}
		if(!model.containsAttribute("run_time")){
			model.addAttribute("run_time", run_time);		
		}
		if(!model.containsAttribute("status")){
			model.addAttribute("status", status);
		}
		
		/* Add the logged in user to show on the page */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); 
	    String names[];
	    names = LoginUtils.splitEmail(name);
	    model.addAttribute("user", names[1] + ", " + names[0]);
	    
	    model.addAttribute("user_tested_by", names[0] + " " + names[1]);
	    
		/* Set a new TestCases object every time the URL its called */
		model.addAttribute("testCase", new TestCases());		
		
		/* Set a new Ticket object every time the URL its called
		 * This is used to edit the Run_time field */
		model.addAttribute("ticket", new Ticket());		
		
		List<TestCases> testCasesList = testCaseDao.findAllByTicketId(id_ticket);
		
		/* Return new view using the list on the testcase.page */
		return new ModelAndView("testcase", "testCasesList", testCasesList);
	}
	
	//AJAX to add new registry on testCases's DataTable
	@RequestMapping(value="/createTestCase", method=RequestMethod.POST, 
            		produces = MediaType.APPLICATION_JSON_VALUE, 
            		consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addTestCase(@RequestBody TestCases testCase,  BindingResult result) {
		
		System.out.println("TaskID: " + testCase.getTask_id() + ", Status: " +
				testCase.getStatus() + ", TestedBy: " + testCase.getTested_by());
		
		Integer status = 0;
		
		/* Validate the testCase object and return a BindingResult object */
		testCaseValidator.validate(testCase, result);
		
		/* VALIDATION IF REQUIRED - If the BindingResult object has errors: */
		if(result.hasErrors()){
			/**set status variable to 1 and javaScript will get this response 
			 * and show a notification to the user **/
			status = 1;
		}else{
			/** If the object has no errors, insert the object in the database **/
			try {
				testCaseDao.saveOrUpdate(testCase);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}	
		return "{\"status\":"+status+"}";
    }
	
	//AJAX
	@RequestMapping(value="/editTestCaseSort", method=RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String editTestCaseSort(@RequestBody TestCases testCase, BindingResult result) {
		
		System.out.println("CONTROLLER - task_id: " + testCase.getTask_id() +
							"testCase_id: " + testCase.getTestcase_id());
		Integer status = 0;
		
		try {
			testCaseDao.updateSort(testCase);
		} catch (Exception e) {
			e.printStackTrace();
			status = 1;
		}			
		
		return "{\"status\":"+status+"}";
	}
	
	/** Map the editTestCaseAjax on POST method.
	 * Here the controller controls the edit function from the modal.*/
	//AJAX
	@RequestMapping(value="/editTestCaseAjax", method=RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String editTestCaseAjax(@RequestBody TestCases testCase, BindingResult result) {
		
		System.out.println("CONTROLLER  - IdTask: " + testCase.getTask_id() +
				" - Status: " + testCase.getStatus() +
				" - tested_by: " + testCase.getTested_by() +
				" - tested_on: " + testCase.getTested_on() +
				" - pre_requisite: " + testCase.getPre_requisite() +
				" - testcase_description: " + testCase.getTestcase_description() +
				" - results: " + testCase.getResults() +
				" - comments: " + testCase.getComments());
		
		Integer status = 0;
		
		testCaseDao.saveOrUpdate(testCase);
		
		return "{\"status\":"+status+"}";
	}
		
	
	/** Map the updateTime URL on POST method
	 * Here the user is able to update the field "Time to run all tests" using a modal.
	 * Receives the new Time as a request parameter, and the other model attributes are used
	 * to properly redirect the user after the update is done. */
	@RequestMapping(value = "/updateTime", method = RequestMethod.POST)
	public String updateTime(@ModelAttribute("id_ticket") Long id_ticket,
			@ModelAttribute("tag") String tag,
			@RequestParam("run_time") String run_time,
			@ModelAttribute("ticket") Ticket ticket) {
		
		System.out.println("Tag: " + tag
					+ " - Jira: " + ticket.getJira() 
					+ " - Description: " + ticket.getDescription() 
					+ " - Environment: " + ticket.getEnvironment() 
					+ " - Tester: " + ticket.getTester() 
					+ " - Developer: " + ticket.getDeveloper() 
					+ " - Run_time: " + ticket.getRun_time()
					+ " - Status: " + ticket.getStatus());
		
		/* Update the time to run all tests*/
		ticketDao.updateColumnValue(id_ticket, "run_time", run_time);
		
		/* Update ticket fields*/
		ticketDao.saveOrUpdate(ticket);	
		
		/* Redirect to the TestCasesController GET URL. */
		return "redirect:testCases?id_ticket="+id_ticket
									+"&tag="+tag
									+"&jira="+ticket.getJira()
									+"&description="+ ticket.getDescription()
									+"&developer="+ticket.getDeveloper()
									+"&tester="+ticket.getTester()
									+"&environment="+ticket.getEnvironment()
									+"&run_time="+ticket.getRun_time()
									+"&status="+ticket.getStatus();
	}
	
	//AJAX Map the resetTestCase on POST method.
	@RequestMapping(value="/resetAllTestCase", method=RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String resetAllTestCase(@RequestBody TestCases testCase, BindingResult result) {
		
		Integer status = 0;
		
		testCaseDao.saveOrUpdate(testCase);
		
		return "{\"status\":"+status+"}";
	}
	
	
	/*Map the playTestCases on POST method.*/
	@RequestMapping(value = "/playTestCase", method = RequestMethod.POST)
	public String playTestCase(@ModelAttribute("play_testcase_id") Long id,
			@ModelAttribute("id_ticket") Integer id_ticket,
			@ModelAttribute("tag") String tag,
			@ModelAttribute("tg_description") String description,
			@ModelAttribute("tg_developer") String developer,
			@ModelAttribute("tg_tester") String tester,
			@ModelAttribute("tg_environment") String environment,
			@ModelAttribute("tg_run_time") String run_time,
			@ModelAttribute("ticket") Ticket ticket,
			@ModelAttribute("tg_jira") String jira,
			@ModelAttribute("tg_user") String user_tested_by,
			@ModelAttribute("tg_testedOn") String tested_on,
			
			@ModelAttribute("testCase") TestCases testCase
			) {
		
		//Get current system date 
		Calendar calendar = Calendar.getInstance();
		DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");  
		String sData = fmt.format(calendar.getTime());
		
		TestCases playTestCase = new TestCases();
		playTestCase.setTestcase_id(id);
		playTestCase.setStatus("On hold");
		playTestCase.setTested_by(user_tested_by);
		playTestCase.setTested_on(sData);
		playTestCase.setTestcase_description("");
		playTestCase.setComments("");
		playTestCase.setPre_requisite("");
		playTestCase.setResults("");
	
		//Do the reset tests using the remove method of Mongo template
		testCaseDao.saveOrUpdate(playTestCase);

		return "redirect:testCases?id_ticket="+id_ticket+"&tag="+tag+"&jira="+jira+"&description="+description+"&developer="+developer+"&tester="+tester+"&environment="+environment+"&run_time="+run_time;
	}
			
	
	//AJAX DELETE
	@RequestMapping(value="/deleteTestCaseAjax", method=RequestMethod.POST, 
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteReleaseAjax(@RequestBody Long id) {
		Integer status = 0;
		System.out.println("ID: " + id);
		
		testCaseDao.delete(id);
		
        return "{\"status\":"+status+"}";
    }
}