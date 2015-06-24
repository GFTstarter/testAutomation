package br.com.gft.testautomation.common.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import br.com.gft.testautomation.common.model.Release;

public class ReleaseDaoJdbc implements ReleaseDao{

	private JdbcTemplate jdbcTemplate;
	 
    public ReleaseDaoJdbc() {}

    @Autowired
	public ReleaseDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
	@Override
	public List<Release> findAll() {

		String sql = "SELECT * FROM releases";
		List<Release> listReleases = jdbcTemplate.query(sql, new RowMapper<Release>() {
			
			@Override
			public Release mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				Release aRelease = new Release();
				
				aRelease.setId_release(rs.getInt("id_release"));
				aRelease.setName(rs.getString("name"));
				aRelease.setProject(rs.getString("project"));
				aRelease.setProjecttag(rs.getString("projecttag"));
				aRelease.setTag(rs.getString("tag"));
				aRelease.setTarget_date(rs.getString("target_date"));
				
				return aRelease;
			}
			
		});
		
		return listReleases;
	}

	@Override
	public void saveOrUpdate(Release release) {
		if(release.getId_release() != 0){
			//update
			String sql = "UPDATE releases SET name = ?, project = ?, projecttag = ?, tag = ?, target_date = ?"
						+ " WHERE id_release = ?";
			
			jdbcTemplate.update(sql, release.getName(), release.getProject(), release.getProjecttag(), 
						release.getTag(), release.getTarget_date(), release.getId_release());
		}
		else{
			//insert
			String sql = "INSERT INTO releases (id_release, project, tag, name, target_date, projecttag) VALUES (null, ?, ?, ?, ?, ?)";
			
			jdbcTemplate.update(sql, release.getProject(), release.getTag(), release.getName(), 
						release.getTarget_date(), release.getProjecttag());
		}
	}


	@Override
	public void delete(Integer id) {
		String sql = "DELETE FROM releases WHERE id_release = ?";
		
		jdbcTemplate.update(sql, id);
		
	}

}
