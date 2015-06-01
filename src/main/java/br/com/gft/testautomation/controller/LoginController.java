package br.com.gft.testautomation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.gft.testautomation.common.model.Login;
import br.com.gft.testautomation.common.repositories.LoginDao;


/** Controller responsible for the login.jsp, accessdenied,jsp and for the URLs login, logout 
 * and accessdenied. Here, the URLs mapped by Spring Security and redirected into their
 * matched pages. */
@Controller
public class LoginController {

	/* Autowires a LoginDaoImpl bean */
	@Autowired
	private LoginDao loginDao;
	
	/** Map the URL login on GET method.
	 * The login.jsp is already mapped as default index page on Spring Security.
	 * Displays the login.jsp page */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String home(ModelMap model){
		
		//Creates a new Login object to populate the model
		model.addAttribute("login", new Login());	
			
		//Redirect to the .jsp page
		return "login";
	}
	
	/** Map the URL logout on all methods.
	 * All the logout buttons redirect to the login.jsp page, after Spring
	 * Security actually make the logout.
	 * Displays the login.jsp with the message from the model attribute. */
	@RequestMapping(value = "/logout")
	public String logout(ModelMap model){
		
		//Redirect to the .jsp page
		return "redirect:login?lmsg=true";
	}
	
	/** Map the accessdenied URL on GET method. 
	 * If the Spring Security found a error during login, this page will be
	 * displayed.
	 * Displays a error message on the accessdenied.jsp page. */
	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
		
		//Add a message to the page
		model.addAttribute("error", "true");
		
		//Redirect to the .jsp page
		return "accessdenied";
	}
	
	/** Map the addUser URL on POST method, which comes from a modal.
	 * Receives the new Login object from the modal and insert it on the database. */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addNewUser(@ModelAttribute("login") Login login){
		
				
		/* If any of the fields are empty, return to the page exhibing a error message */
		if((login.getUsername().equals("")) || (login.getUsername().equals("")) ){
			return "redirect:login?emsg=true";
		}else{
			/* If they are not empty, insert using the save method of LoginDaoImpl */
			if(login.getPassword().equals(login.getPasswordConfirm())){
				try {
					loginDao.save(login);
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:login?smsg=false";
				}
			}
			else
				return "redirect:login?pcmsg=false";
						
			return "redirect:login?smsg=true";
		}		
	}
}
