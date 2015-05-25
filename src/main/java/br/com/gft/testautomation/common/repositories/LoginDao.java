package br.com.gft.testautomation.common.repositories;

import br.com.gft.testautomation.common.model.Login;



/** Interface of the model class Login to access the entity Users and User_Roles in the database */
public interface LoginDao{	
	
	void save(Login obj);
	void delete(Long id);
}
