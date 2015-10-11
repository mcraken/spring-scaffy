package com.scaffy.service;

import org.springframework.validation.BindException;

import com.scaffy.service.bean.BeanTraversalException;


public interface RestService {
	
	public Object bindAndValidate(String body) throws BindException;
	
	public Object save(Object model) throws BeanTraversalException;
	
	public Object update(Object model) throws BeanTraversalException;
	
	public Object delete(Object model) throws BeanTraversalException;
}
