package com.scaffy.service;

import com.scaffy.service.bean.BeanTraversalException;

public interface EntityService {

	public void create(Object model) throws BeanTraversalException;

	public void update(Object model) throws BeanTraversalException;

	public void delete(Object model) throws BeanTraversalException;

}