package br.com.gft.testautomation.common.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
			String sql = "INSERT INTO tickets (id_tickets, id_release, jira, description, environment, developer,"
						+ " tester, status, run_time, testcase_status) VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			jdbcTemplate.update(sql);
		}
		
	}

	@Override
	public Ticket getTicketByJira(String jira) {
		// TODO Auto-generated method stub
		return null;
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
	public void delete(Long id) {
		String sql = "DELETE FROM tickets WHERE id_ticket = ?";
		
		jdbcTemplate.update(sql, id);
	}

	
		
}
