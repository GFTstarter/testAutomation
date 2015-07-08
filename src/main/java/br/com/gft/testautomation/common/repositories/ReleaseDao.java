package br.com.gft.testautomation.common.repositories;

import java.util.List;

import br.com.gft.testautomation.common.model.Release;

public interface ReleaseDao{	
	
	List<Release> findAll();
	void saveOrUpdate(Release obj);
	void delete(Long id);
}
