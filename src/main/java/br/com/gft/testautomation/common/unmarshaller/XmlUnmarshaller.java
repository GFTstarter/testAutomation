package br.com.gft.testautomation.common.unmarshaller;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.gft.testautomation.common.model.Rss;
import br.com.gft.testautomation.common.model.Rss.Channel.Item;
import br.com.gft.testautomation.controller.PopulateTicket;

/** Class responsible for unmarshalling the uploaded XML into a readable Ticket object */
@Component
public class XmlUnmarshaller {

	/* Autowire the PopulateTicket class */
	@Autowired
	PopulateTicket populateTicket;
	
	/** Method responsible for unmarshalling the uploaded XML. Receives a xml in File format, 
	 * and the release id for foreign key purposes */
	public void convertToXml(File xml, int id){
		try{
			/* Create a new instance of JAXBContext with the Rss class 
			 * (the class consists of attributes made of XML fields) */
			JAXBContext jaxbContext = JAXBContext.newInstance(Rss.class);
			
			/* Create a new instance of the Unmarshaller */
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			/* Unmarshall the XML file into a Rss object */
			Rss rss = (Rss) jaxbUnmarshaller.unmarshal(xml);
			
			/* Create a list of Items from the Rss object */
			ArrayList<Rss.Channel.Item> items = (ArrayList<Item>) rss.getChannel().getItem();
			System.out.println("ID_release: " + id);
			/* For each Item in the Items list, execute the method populateTicket */
			for(int i = 0; i < items.size(); i++){
				/* Method that creates a new Ticket object for each Item in the Items list. 
				 * Receives the Rss object, the index of the items list, the items list and 
				 * the release id */
				populateTicket.populateTicket(rss, i, items, id);
			}			

		}catch (JAXBException e){
			e.printStackTrace();
		}
	}
}
