package com.scaffy.service;

import com.scaffy.controller.MultipartRequest;
import com.scaffy.dao.bean.BeanTraversalException;


public interface RestService {
	
	public void save(Object model) throws BeanTraversalException;
	
	public void saveWithAttachments(MultipartRequest request) throws BeanTraversalException;
	
	public void update(Object model) throws BeanTraversalException;
	
	public void delete(Object model) throws BeanTraversalException;
	
	public Class<?> modelType();
}
