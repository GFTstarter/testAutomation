package br.com.gft.testautomation.common.model;


/** Model class Sequence that corresponds to the Sequence collection in the database. 
 * Responsible for creating an autoincrement sequence to each of collections beside this one. */
public class Sequence {

	/* Document Id. Stores the collection name */
	private String id;
	
	/* Long number that stores the sequence itself */
	private long seq;

	//Getters and Setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
}
