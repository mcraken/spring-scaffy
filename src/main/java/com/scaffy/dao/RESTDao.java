package com.scaffy.dao;

import com.scaffy.controller.MultipartResponse;
import com.scaffy.dao.bean.BeanTraversalException;


public interface RESTDao {

	public Object read(Object key, Class<?> type) throws DaoOperationException;
	
	public void create(Object model) throws BeanTraversalException, DaoOperationException;

	public void createWithAttachments(MultipartResponse request) throws BeanTraversalException, DaoOperationException;
	
	public void update(Object model) throws BeanTraversalException, DaoOperationException;

	public void delete(Object model) throws BeanTraversalException, DaoOperationException;

}