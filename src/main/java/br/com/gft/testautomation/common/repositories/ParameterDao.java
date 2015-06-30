package br.com.gft.testautomation.common.repositories;

import br.com.gft.testautomation.common.model.Parameter;

public interface ParameterDao {
	Parameter findParameterById(Integer id);
}
