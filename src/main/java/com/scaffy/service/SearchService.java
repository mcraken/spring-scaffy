package com.scaffy.service;

import java.util.List;

import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.key.RestSearchKey;

public interface SearchService {
	
	public List<?> db(
			RestSearchKey restSearchKey
			) throws InvalidCriteriaException, NoDataFoundException, ServiceNotFoundException;
	
	public List<?> fullText(
			RestSearchKey restSearchKey
			) throws InvalidCriteriaException, NoDataFoundException, ServiceNotFoundException;
}
