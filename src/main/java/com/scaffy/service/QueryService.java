package com.scaffy.service;

import java.util.List;

import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.key.RestSearchKey;

public interface QueryService {
	
	public List<?> query(
			RestSearchKey restSearchKey
			) throws InvalidCriteriaException, NoDataFoundException;

}
