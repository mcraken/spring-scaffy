package com.scaffy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.key.RestSearchKey;
import com.scaffy.dao.SearchDao;

public class BasicSearchService implements SearchService {

	private Class<?> modelClass;
	
	private String dbSearchType;
	
	private String ftSearchType;
	
	@Autowired
	private ServiceBroker serviceBroker;
	
	public void setModelClass(String modelName) throws ClassNotFoundException {
		this.modelClass = Class.forName(modelName);
	}
	
	public void setDbSearchType(String dbSearchType) {
		this.dbSearchType = dbSearchType;
	}
	
	public void setFtSearchType(String ftSearchType) {
		this.ftSearchType = ftSearchType;
	}
	
	public List<?> db(RestSearchKey restSearchKey)
			throws InvalidCriteriaException, NoDataFoundException, ServiceNotFoundException {
		
		if(dbSearchType == null)
			throw new ServiceNotFoundException("Null DB Search Type");
		
		SearchDao searchDao = serviceBroker.findBean(dbSearchType, SearchDao.class);
		
		List<?> result = searchDao.read(restSearchKey, modelClass); 
		
		if(result == null || result.size() == 0)
			throw new NoDataFoundException("Nothing matches your key");
		
		return result;
	}

	public List<?> fullText(RestSearchKey restSearchKey)
			throws InvalidCriteriaException, NoDataFoundException, ServiceNotFoundException {
		
		if(ftSearchType == null)
			throw new ServiceNotFoundException("Null Full Text Search Type");
		
		SearchDao searchDao = serviceBroker.findBean(ftSearchType, SearchDao.class);
		
		List<?> result = searchDao.read(restSearchKey, modelClass); 
		
		if(result == null || result.size() == 0)
			throw new NoDataFoundException("Nothing matches your key");
		
		return result;
	}

}
