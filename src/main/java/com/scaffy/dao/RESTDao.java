package com.scaffy.dao;

import com.scaffy.dao.bean.BeanTraversalException;


public interface RESTDao {

	public void create(Object model) throws BeanTraversalException;

	public void update(Object model) throws BeanTraversalException;

	public void delete(Object model) throws BeanTraversalException;

}