package br.com.gft.testautomation.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** Model class Sequence that corresponds to the Sequence collection in the database. 
 * Responsible for creating an autoincrement sequence to each of collections beside this one. */
@Document(collection = "sequence")
public class Sequence {

	/* Document Id. Stores the collection name */
	@Id
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
