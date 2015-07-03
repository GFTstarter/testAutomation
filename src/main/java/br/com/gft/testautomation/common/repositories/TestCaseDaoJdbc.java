package br.com.gft.testautomation.common.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import br.com.gft.testautomation.common.model.TestCases;

public class TestCaseDaoJdbc implements TestCaseDao {

	private JdbcTemplate jdbcTemplate;
	 
    public TestCaseDaoJdbc() {}

    @Autowired
	public TestCaseDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	@Override
	public void updateSort(TestCases testCase) {
		
		System.out.println("UPDATE SORT");
		String sql = "UPDATE testcases SET id_task = ? WHERE id_testcase = ?";
		
		System.out.println("DAO_JDBC - idTask: " + testCase.getTask_id() + " - idTestCase: " + testCase.getTestcase_id());
		
		jdbcTemplate.update(sql, testCase.getTask_id(), testCase.getTestcase_id());
		
	}

	@Override
	public void saveOrUpdate(TestCases testCase) {
		if(testCase.getTestcase_id() != 0){
			//update
			System.out.println("UPDATE");
			String sql = "UPDATE testcases SET status = ?, tested_by = ?, tested_on = ?, pre_requisite = ?,"
					+ "testcase_description = ?, results = ?, comments = ? WHERE id_testcase = ?";
			
			System.out.println("DAO_JDBC - Status: " + testCase.getStatus() 
					+ " - tested_by: " + testCase.getTested_by() +
					" - tested_on: " + testCase.getTested_on() +
					" - pre_requisite: " + testCase.getPre_requisite() +
					" - testcase_description: " + testCase.getTestcase_description() +
					" - results: " + testCase.getResults() +
					" - comments: " + testCase.getComments());
			
			jdbcTemplate.update(sql, testCase.getStatus(), testCase.getTested_by(), testCase.getTested_on(), testCase.getPre_requisite(),
						testCase.getTestcase_description(), testCase.getResults(), testCase.getComments(), testCase.getTestcase_id());
		}
		else{
			//insert
			System.out.println("INSERT");
			String sql = "INSERT INTO testcases (id_testcase, id_ticket, id_task, status, tested_by, tested_on, pre_requisite, "
					+ "testcase_description, results, comments) VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			jdbcTemplate.update(sql, testCase.getId_ticket(), testCase.getTask_id(), testCase.getStatus(), testCase.getTested_by(), testCase.getTested_on(), testCase.getPre_requisite(),
					testCase.getTestcase_description(), testCase.getResults(), testCase.getComments());
		}
	}

	@Override
	public List<TestCases> findAllByTicketId(Long idTicket) {

		String sql = "SELECT * FROM testcases WHERE id_ticket = ?";
		List<TestCases> listTestCases = jdbcTemplate.query(sql, new Object[] {idTicket}, new RowMapper<TestCases>() {
			
			@Override
			public TestCases mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				TestCases aTestCase = new TestCases();
				
				aTestCase.setTestcase_id(rs.getInt("id_testcase"));
				aTestCase.setId_ticket(rs.getInt("id_ticket"));
				aTestCase.setTask_id(rs.getInt("id_task"));
				aTestCase.setStatus(rs.getString("status"));
				aTestCase.setTested_by(rs.getString("tested_by"));
				aTestCase.setTested_on(rs.getString("tested_on"));
				aTestCase.setPre_requisite(rs.getString("pre_requisite"));
				aTestCase.setTestcase_description(rs.getString("testcase_description"));
				aTestCase.setResults(rs.getString("results"));
				aTestCase.setComments(rs.getString("comments"));
				
				return aTestCase;
			}
			
		});
		
		return listTestCases;
	}

	@Override
	public void updateColumnValue(final Long id, final String column, final String value) {
			
		String sql = "UPDATE testcases SET "+ column + " = ? WHERE id_testcase = ?";
		
		jdbcTemplate.update(sql, value, id);
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM testcases WHERE id_testcase = ?";
		
		jdbcTemplate.update(sql, id);
	}

	@Override
	public void updateReset(String status, String tested_by, String tested_on, String comments) {
			
		String sql = "UPDATE testcases SET status = ?, tested_by = ?, tested_on = ?,"
				+ "comments = ? WHERE id_testcase = ?";
		
		jdbcTemplate.update(sql, status, tested_by, tested_on, comments);
		
	}

	@Override
	public void updateTwoColumnValue(Long id_testcase, String column,
			String value, String column2, String value2) {

		String sql = "UPDATE testcases SET "+ column + " = ?, " + column2+" =?" 
		+" WHERE id_testcase = ?";
		
		jdbcTemplate.update(sql, value, value2, id_testcase);
	}
	
	
	
	

}
