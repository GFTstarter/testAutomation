package br.com.gft.testautomation.common.model;

/** Model class Login that corresponds to the Users entity in the database*/

public class Login {
	
	private long login_id;
	/* Table id, contains the username (email) in text. Primary key */
	private String username;
	/* Username password. Text, not null */
	private String password;
	
	private String role;	
	
	
	public long getLogin_id() {
		return login_id;
	}
	public void setLogin_id(long login_id) {
		this.login_id = login_id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	//Getters and Setters
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
