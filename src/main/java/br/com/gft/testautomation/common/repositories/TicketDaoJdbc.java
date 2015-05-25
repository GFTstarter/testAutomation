package br.com.gft.testautomation.common.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import br.com.gft.testautomation.common.model.Ticket;

public class TicketDaoJdbc implements TicketDao{
	
	private JdbcTemplate jdbcTemplate;
	 
    public TicketDaoJdbc() {}

    @Autowired
	public TicketDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	public void saveOrUpdate(Ticket ticket) {
		if(ticket.getId_ticket() != 0){
			//update
			String sql = "UPDATE tickets SET tester = ?, developer = ?, status = ? "
						+ "WHERE id_ticket = ?";
			
			System.out.println("IdTicket = " + ticket.getId_ticket() + ", Tester = " + 
			ticket.getTester() + ", Developer = " + ticket.getDeveloper() + ", Status = " + ticket.getStatus());
			
			jdbcTemplate.update(sql, ticket.getTester(), ticket.getDeveloper(), ticket.getStatus());
		}
		else{
			//insert
			String sql = "INSERT INTO tickets (id_ticket, id_release, jira, description, environment, developer,"
						+ " tester, status, run_time, testcase_status) VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			jdbcTemplate.update(sql, ticket.getId_release(), ticket.getJira(), ticket.getDescription(), 
					ticket.getEnvironment(), ticket.getDeveloper(), ticket.getTester(), ticket.getStatus(), 
					ticket.getRun_time(), ticket.getTestcase_status());
		}
		
	}

	/*Return ticket register by the jira specified, the query is set directly to 
	*the return statement because it will be only one row
	*/
	@Override
	public Ticket getTicketByJira(String jira) {
		
		System.out.println("jira = " + jira);
		
		String sql = "SELECT * FROM tickets WHERE jira = '"+ jira +"'";
		
		return jdbcTemplate.query(sql, new ResultSetExtractor<Ticket>() {

			@Override
			public Ticket extractData(ResultSet rs) throws SQLException,
					DataAccessException {
					
				if(rs.next()){
					Ticket ticket = new Ticket();
					ticket.setId_ticket(rs.getLong("id_ticket"));
					ticket.setId_release(rs.getLong("id_release"));
					ticket.setJira(rs.getString("jira"));
					ticket.setDescription(rs.getString("description"));
					ticket.setEnvironment(rs.getString("environment"));
					ticket.setDeveloper(rs.getString("developer"));
					ticket.setTester(rs.getString("tester"));
					ticket.setStatus(rs.getString("status"));
					ticket.setRun_time(rs.getString("run_time"));
					ticket.setTestcase_status(rs.getString("testcase_status"));
					return ticket;
				}
				return null;
			}
		});
	}

	
	@Override
	public List<Ticket> findAllByReleaseId(Integer id) {
		String sql = "SELECT * FROM tickets WHERE id_release = ?";
		
		List<Ticket> listTickets = jdbcTemplate.query(sql, new Object[] {id}, new RowMapper<Ticket>() {
			
			@Override
			public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				Ticket aTicket = new Ticket();
				
				aTicket.setId_ticket(rs.getLong("id_ticket"));
				aTicket.setId_release(rs.getLong("id_release"));
				aTicket.setJira(rs.getString("jira"));
				aTicket.setDescription(rs.getString("description"));
				aTicket.setEnvironment(rs.getString("environment"));
				aTicket.setDeveloper(rs.getString("developer"));
				aTicket.setTester(rs.getString("tester"));
				aTicket.setStatus(rs.getString("status"));
				aTicket.setRun_time(rs.getString("run_time"));
				aTicket.setTestcase_status(rs.getString("testcase_status"));
				
				return aTicket;
			}
		});
		
		return listTickets;
	}

	
	@Override
	public void updateColumnValue(Long id, String column, String value) {
		
		String sql = "UPDATE tickets SET "+ column + " = ? WHERE id_ticket = ?";
		
		jdbcTemplate.update(sql, value, id);
	}
	

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM tickets WHERE id_ticket = ?";
		
		jdbcTemplate.update(sql, id);
	}

	
		
}
