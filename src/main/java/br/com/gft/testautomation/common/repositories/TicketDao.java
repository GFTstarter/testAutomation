package br.com.gft.testautomation.common.repositories;

import java.util.List;

import br.com.gft.testautomation.common.model.Ticket;

public interface TicketDao{	
	
	void saveOrUpdate(Ticket obj);
	Ticket getTicketByJira(String jira);
	List<Ticket> findAllByReleaseId(Integer id_release);
	void delete(Long id);
	void updateColumnValue (Long id, String column,String value);
}
