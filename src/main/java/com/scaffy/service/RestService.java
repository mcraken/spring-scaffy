package com.scaffy.service;

import org.springframework.validation.BindException;

import com.scaffy.controller.MultipartRequest;
import com.scaffy.service.bean.BeanTraversalException;


public interface RestService {
	
	public Object bindAndValidate(String body) throws BindException;
	
	public void save(Object model) throws BeanTraversalException;
	
	public void saveWithAttachments(MultipartRequest request) throws BeanTraversalException;
	
	public void update(Object model) throws BeanTraversalException;
	
	public void delete(Object model) throws BeanTraversalException;
}
