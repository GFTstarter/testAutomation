package br.com.gft.testautomation.common.repositories;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import br.com.gft.testautomation.common.model.Login;

public class LoginDaoJdbc implements LoginDao{

	private JdbcTemplate jdbcTemplate;
	 
	public LoginDaoJdbc(){}
	
	@Autowired
    public LoginDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
	@Override
	public void save(Login login) {
		
		String sqlUsers = "INSERT INTO users (id_user, username, password, enabled)"
                + " VALUES (null, ?, ?, ?)";
		
		String sqlAuthoroties = "INSERT INTO authorities (username, authority) VALUES (?, ?)";
		
		System.out.println("Username: " + login.getUsername() + ", Password: " + login.getPassword());
		
		jdbcTemplate.update(sqlUsers, login.getUsername(), login.getPassword(), 1);
		jdbcTemplate.update(sqlAuthoroties, login.getUsername(), "ROLE_USER");
	}

	@Override
	public void delete(Long id) {
		
		String sql = "DELETE FROM users WHERE id_user = ?";
		
		jdbcTemplate.update(sql, id);
	}

}
