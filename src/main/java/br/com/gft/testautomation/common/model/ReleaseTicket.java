package br.com.gft.testautomation.common.model;

import java.util.List;

/** Model wrapped class ReleaseTicket. Contains a list of Ticket objects and a list of 
 * Release objects. Used to populate a page that needs these two lists. */
public class ReleaseTicket {

	/* List of Ticket objects */
	private List<Ticket> ticketList;
	/* List of Release objects */
	private List<Release> releaseList;
	
	//Getters and Setters
	public List<Release> getReleaseList() {
		return releaseList;
	}
	public void setReleaseList(List<Release> releaseList) {
		this.releaseList = releaseList;
	}
	public List<Ticket> getTicketList() {
		return ticketList;
	}
	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}	
}
