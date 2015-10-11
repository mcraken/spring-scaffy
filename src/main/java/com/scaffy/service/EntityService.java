package com.scaffy.service;

import java.util.List;

import com.scaffy.query.exception.InvalidCriteriaException;
import com.scaffy.query.key.RestSearchKey;
import com.scaffy.service.bean.BeanTraversalException;

public interface EntityService {

	public <T> List<T> read(RestSearchKey key, Class<T> typeClass) throws InvalidCriteriaException;
	
	public void create(Object model) throws BeanTraversalException;

	public void update(Object model) throws BeanTraversalException;

	public void delete(Object model) throws BeanTraversalException;

}