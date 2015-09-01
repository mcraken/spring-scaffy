package com.scaffy.service;

import java.util.List;

import com.scaffy.query.exception.InvalidCriteriaException;
import com.scaffy.query.exception.InvalidCriteriaSyntaxException;
import com.scaffy.query.key.RestSearchKey;

public interface RestService {
	
	public List<?> query(
			RestSearchKey restSearchKey
			) throws InvalidCriteriaException, InvalidCriteriaSyntaxException, NoDataFoundException;
	
	public <T> void save(T model);
	
	public <T> void update(T model);
	
	public <T> void delete(T model);
	
	public Class<?> getModelClass();
}
