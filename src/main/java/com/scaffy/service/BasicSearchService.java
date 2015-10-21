package com.scaffy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.key.RestSearchKey;
import com.scaffy.dao.QueryDao;

public class BasicSearchService implements SearchService {

	private Class<?> modelClass;

	@Autowired
	private QueryDao queryDao;

	public void setModelClass(String modelName) throws ClassNotFoundException {
		this.modelClass = Class.forName(modelName);
	}

	public List<?> acquire(RestSearchKey restSearchKey)
			throws InvalidCriteriaException, NoDataFoundException {
		
		List<?> result = queryDao.read(restSearchKey, modelClass); 

		if(result == null || result.size() == 0)
			throw new NoDataFoundException("Nothing matches your key");

		return result; 
	}

}
