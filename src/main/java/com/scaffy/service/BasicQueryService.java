package com.scaffy.service;

import java.util.List;

import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.key.RestSearchKey;

public abstract class BasicQueryService implements QueryService {
	
	private Class<?> modelClass;
	
	public void setModelClass(String modelName) throws ClassNotFoundException {
		this.modelClass = Class.forName(modelName);
	}
	
	public List<?> query(
			RestSearchKey restSearchKey
			) throws InvalidCriteriaException,
						NoDataFoundException
	{
		
		List<?> result = read(restSearchKey, modelClass); 
		
		if(result == null || result.size() == 0)
			throw new NoDataFoundException("Nothing matches your key");
		
		return result; 
	}
	
	protected abstract <T> List<T> read(RestSearchKey key, Class<T> typeClass) throws InvalidCriteriaException;
	
}
