package br.com.gft.testautomation.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** Model class Ticket that corresponds to the Tickets entity in the database.
 * All this data also appears as a Rss object, but the required values are copied 
 * into a Ticket object to be easily manipulated. */
@Document(collection = "tickets")
public class Ticket {
	
	/* Table id. Integer, primary key, autoincrement */
	@Id
	private long id_ticket;
	
	/* Ticket JIRA id. Text, not null */
	private String jira;	
	
	/* Ticket description. Text, not null */
	private String description;
	
	/* Environment where the ticket originated. Text, could be null */
	private String environment;
	
	/* Developer responsible for the ticket. Text, not null */
	private String developer;		
	
	/* Tester responsible for the ticket. Text, not null */
	private String tester;	
	
	/* Ticket status. Text, not null */
	private String status;	
	
	/* Time to run all test cases in this ticket. Text, null */
	private String run_time;
	
	/* Store the status of the test cases that comes from this Ticket object.
	 * If there is a single test case with the Failed status, this status will also be
	 * failed. Text, null */
	private String testcase_status;
	
	/* Store the Release Id as a foreign key to reference another document.
	 * Long, not null */
	private long id_release;
	
	//Getters and Setters
	public String getJira() {
		return jira;
	}	
	public void setJira(String jira) {
		this.jira = jira;
	}	
	public String getDescription() {
		return description;
	}	
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDeveloper() {
		return developer;
	}	
	public void setDeveloper(String developer) {
		this.developer = developer;
	}	
	public String getTester() {
		return tester;
	}
	public void setTester(String tester) {
		this.tester = tester;
	}	
	public String getStatus() {
		return status;
	}	
	public void setStatus(String status) {
		this.status = status;
	}
	public long getId_ticket() {
		return id_ticket;
	}
	public void setId_ticket(long id_ticket) {
		this.id_ticket = id_ticket;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getRun_time() {
		return run_time;
	}
	public void setRun_time(String run_time) {
		this.run_time = run_time;
	}
	public long getId_release() {
		return id_release;
	}
	public void setId_release(long id_release) {
		this.id_release = id_release;
	}
	public String getTestcase_status() {
		return testcase_status;
	}
	public void setTestcase_status(String testcase_status) {
		this.testcase_status = testcase_status;
	}	
}
