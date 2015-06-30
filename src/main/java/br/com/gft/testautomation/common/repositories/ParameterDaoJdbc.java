package br.com.gft.testautomation.common.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import br.com.gft.testautomation.common.model.Parameter;

public class ParameterDaoJdbc implements ParameterDao{
	
	private JdbcTemplate jdbcTemplate;
	 
    public ParameterDaoJdbc() {}

    @Autowired
	public ParameterDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	@Override
	public Parameter findParameterById(Integer idParam){
		
		System.out.println("IdParam = " + idParam);
		
		String sql = "SELECT * FROM parameters WHERE id_parameter = ?";
		
		return jdbcTemplate.query(sql, new Object[] {idParam}, new ResultSetExtractor<Parameter>() {
	
			@Override
			public Parameter extractData(ResultSet rs) throws SQLException,
					DataAccessException {
					
				if(rs.next()){
					Parameter param = new Parameter();
					param.setId_parameter(rs.getInt("id_parameter"));
					param.setProject_name(rs.getString("project_name"));
					param.setImportJIRAxmlButton(rs.getInt("importJIRAxmlButton"));
					return param;
				}
				return null;
			}
		});

	}

}
