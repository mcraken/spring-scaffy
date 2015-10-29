package com.scaffy.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.scaffy.controller.MultipartResponse;
import com.scaffy.dao.DaoOperationException;
import com.scaffy.dao.RESTDao;

public class BasicRestService implements RestService {

	private Class<?> modelClass;

	@Autowired
	private RESTDao restDao;
	
	public void setModelClass(String modelName) throws ClassNotFoundException {
		
		this.modelClass = Class.forName(modelName);
	}

	public void save(Object model) throws RestServiceException  {

		try {
			
			restDao.create(model);
			
		} catch (Exception e) {
			
			throw new RestServiceException(e);
		}
		
	}
	
	public void update(Object model) throws RestServiceException  {

		try {
			
			restDao.update(model);
			
		} catch (Exception e) {
			
			throw new RestServiceException(e); 
		}
		
	}

	public void delete(Object model) throws RestServiceException {

		try {
			
			restDao.delete(model);
			
		} catch (Exception e) {
			
			throw new RestServiceException(e);
		}

	}

	public Class<?> modelType() {
		
		return modelClass;
	}

	public void saveWithAttachments(MultipartResponse request) throws RestServiceException {
		
		try {
			
			restDao.createWithAttachments(request);
			
		} catch (Exception e) {
			
			throw new RestServiceException(e);
		} 
		
	}

	@SuppressWarnings("unchecked")
	public <T>T read(Object key) throws RestServiceException, NoDataFoundException {
		
		try {
			
			Object model = restDao.read(key, modelClass);
			
			if(model == null)
				throw new NoDataFoundException(
						"Could not find " 
						+ modelClass.getSimpleName()
						+ " using key " + key);
			
			return (T) model;
					
			
		} catch (DaoOperationException e) {
			
			throw new RestServiceException(e);
		}
	}

}
