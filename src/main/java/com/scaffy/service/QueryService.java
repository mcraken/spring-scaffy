package com.scaffy.service;

import java.util.List;

import com.scaffy.query.exception.InvalidCriteriaException;
import com.scaffy.query.exception.InvalidCriteriaSyntaxException;
import com.scaffy.query.key.RestSearchKey;

public interface QueryService {
	
	public List<?> query(
			RestSearchKey restSearchKey
			) throws InvalidCriteriaException, InvalidCriteriaSyntaxException, NoDataFoundException;

}
