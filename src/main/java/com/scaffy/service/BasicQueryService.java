package com.scaffy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.scaffy.query.exception.InvalidCriteriaException;
import com.scaffy.query.key.RestSearchKey;

public class BasicQueryService implements QueryService {
	
	@Autowired
	private EntityService entityService;
	
	private Class<?> modelClass;
	
	public void setModelClass(String modelName) throws ClassNotFoundException {
		this.modelClass = Class.forName(modelName);
	}
	
	public List<?> query(
			RestSearchKey restSearchKey
			) throws InvalidCriteriaException,
						NoDataFoundException
	{
		
		List<?> result = entityService.read(restSearchKey, modelClass); 
		
		if(result == null || result.size() == 0)
			throw new NoDataFoundException("Nothing matches your key");
		
		return result; 
	}
	
}
