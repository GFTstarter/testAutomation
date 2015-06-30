package br.com.gft.testautomation.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import br.com.gft.testautomation.common.model.Rss;
import br.com.gft.testautomation.common.model.Ticket;
import br.com.gft.testautomation.common.repositories.TicketDao;

/** Class responsible for creating Ticket objects to insert on the Tickets table */
@Component
public class PopulateTicket {

	/* Autowire the TicketDaoImpl bean to save objects into the database */
	@Autowired
	TicketDao ticketDao;
	
	/** Method responsible for creating a new Ticket object for each item of the items list.
	 * Receives the Rss object, the index of the items list, the items list itself 
	 * and the release id. */
	public void populateTicket(Rss rss, int i, ArrayList<Rss.Channel.Item> items, int id_release){
		
		/* Create a new instance of Ticket */
		Ticket ticket = new Ticket();		
		
		/* Create a new array of Customfields using the first place of the items list */
		ArrayList<Rss.Channel.Item.Customfields.Customfield> customfields = (ArrayList<Rss.Channel.Item.Customfields.Customfield>) items.get(i).getCustomfields().getCustomfieldList();
		
		/* Get the required fields of the items list and the customfields list and
		 * set into the Ticket object. These are the only needed values to populate
		 * the Ticket object, and, consequently, the only values needed by the user. */
		ticket.setJira(items.get(i).getKey().getValue());
		ticket.setDescription(items.get(i).getSummary());
		ticket.setEnvironment(items.get(i).getEnvironment());
		ticket.setStatus(items.get(i).getStatus().getValue());
		ticket.setId_release(id_release);
		ticket.setRun_time("");
		ticket.setTestcase_status("");
	
		for (int j = 0; j < customfields.size(); j++){
			if(customfields.get(j).getCustomfieldname().equals("Developer")){
				System.out.println("Developer: " + customfields.get(j).getCustomfieldvalues().getCustomfieldvalue().getValue());
				ticket.setDeveloper(customfields.get(j).getCustomfieldvalues().getCustomfieldvalue().getValue());
			}
			if(customfields.get(j).getCustomfieldname().equals("Tester")){
				System.out.println("Tester: " + customfields.get(j).getCustomfieldvalues().getCustomfieldvalue().getValue());
				ticket.setTester(customfields.get(j).getCustomfieldvalues().getCustomfieldvalue().getValue());
			}
		}

		//System.out.println("IMPORT: Jira: " + ticket.getJira() + " - Description: " + ticket.getEnvironment()
		//		+ " - Status: " + ticket.getStatus() + " - id_Release: " + ticket.getId_ticket());
		
		String jsonData = new Gson().toJson(ticket, Ticket.class);
		System.out.println("Json: " + jsonData);
		
		//Verify if XML that is being read has register of developer and tester
		//If not, is needed to set empty, because these filed are not null and will raise exception
		if(ticket.getDeveloper() == null){
			ticket.setDeveloper("");
		}
		if(ticket.getTester() == null){
			ticket.setTester("");
		}
		
		/** Search if the ticket being added already exists to avoid duplication **/
		Ticket ticketVerify = ticketDao.getTicketByJira(ticket.getJira());

		if(ticketVerify != null){
			//update
			System.out.println("update");
			ticket.setId_ticket(ticketVerify.getId_ticket());
			ticketDao.saveOrUpdate(ticket);
		}else{
			//save
			System.out.println("save");
			
			try {
				ticketDao.saveOrUpdate(ticket);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}