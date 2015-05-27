package br.com.gft.testautomation.common.model;


/** Model class Release that corresponds to the Releases collection in the database*/
public class Release {
	
	/* Document id. Integer, primary key, auto_increment by the Sequence class*/
	private long id_release;
	
	/* Project alias. Text, not null */
	private String project;
	/* Project tag. Text, not null */
	private String tag;
	/* Project name. Text, not null*/
	private String name;
	/* Release target date. Text, not null. */ 	
	private String target_date;
	/* String with project alias concatenate with project tag. */
	@SuppressWarnings("unused")
	private String projecttag;

	//Getters and Setters	
	public String getProjecttag() {
		return project + " " + tag;
	}
	public void setProjecttag(String projecttag) {
		this.projecttag = projecttag;
	}	
	public long getId_release() {
		return id_release;
	}
	public void setId_release(long id_release) {
		this.id_release = id_release;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getTarget_date() {
		return target_date;
	}
	public void setTarget_date(String target_date) {
		this.target_date = target_date;
	}
}
