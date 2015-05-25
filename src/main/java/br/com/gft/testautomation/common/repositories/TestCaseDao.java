package br.com.gft.testautomation.common.repositories;

import java.util.List;

import br.com.gft.testautomation.common.model.TestCases;

public interface TestCaseDao{	
	
	void saveOrUpdate(TestCases obj);
	List<TestCases> findAllByTicketId (Long id);
	void updateColumnValue (Long id, String column,String value);
	void updateReset (String status, String tested_by,String tested_on, String comments);
	void delete(Long id);
	
}
