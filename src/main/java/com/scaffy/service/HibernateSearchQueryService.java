package com.scaffy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.key.RestSearchKey;

public class HibernateSearchQueryService extends BasicQueryService {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	protected <T> List<T> read(RestSearchKey key, Class<T> typeClass)
			throws InvalidCriteriaException {
		return null;
	}

}
