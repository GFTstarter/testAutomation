package br.com.gft.testautomation.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** Model class TestCases that corresponds to the Test_cases entity in the database*/
@Document(collection = "testcases")
public class TestCases {

	/* Table id. Integer, primary key, autoincrement */
	@Id
	private long id_testcase;
	
	/* Task id. Text, not null. Not primary key as it is repeatable. */
	private int id_task;
	
	/* Test case status. Text, could be null */
	private String status;
	
	/* Who tested. Text, could be null */
	private String tested_by;
	
	/* Where it was tested. Text, could be null */
	private String tested_on;
	
	/* Test case pre-requisite. Text,  could be null */
	private String pre_requisite;
	
	/* Test case description. It has this name to not conflict with Ticket description. 
	 * Text, not null */
	private String testcase_description;
	
	/* Test case expected results. Text, could be null */
	private String results;
	
	/* Any comments. Text, could be null */
	private String comments;
	
	/* Store the Ticket Id as a foreign key to reference another document.
	 * Long, not null */
	private long id_ticket;
	
	//Getters and Setters
	public int getTask_id() {
		return id_task;
	}
	public void setTask_id(int task_id) {
		this.id_task = task_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTested_by() {
		return tested_by;
	}
	public void setTested_by(String tested_by) {
		this.tested_by = tested_by;
	}
	public String getTested_on() {
		return tested_on;
	}
	public void setTested_on(String tested_on) {
		this.tested_on = tested_on;
	}
	public String getPre_requisite() {
		return pre_requisite;
	}
	public void setPre_requisite(String pre_requisite) {
		this.pre_requisite = pre_requisite;
	}

	public String getResults() {
		return results;
	}
	public void setResults(String results) {
		this.results = results;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getTestcase_description() {
		return testcase_description;
	}
	public void setTestcase_description(String testcase_description) {
		this.testcase_description = testcase_description;
	}
	public long getTestcase_id() {
		return id_testcase;
	}
	public void setTestcase_id(long testcase_id) {
		this.id_testcase = testcase_id;
	}
	public long getId_ticket() {
		return id_ticket;
	}
	public void setId_ticket(long id_ticket) {
		this.id_ticket = id_ticket;
	}
}
