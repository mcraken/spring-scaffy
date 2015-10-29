package com.scaffy.service;

import com.scaffy.controller.MultipartResponse;


public interface RestService {
	
	public <T>T read(Object key) throws RestServiceException, NoDataFoundException;
	
	public void save(Object model) throws RestServiceException;
	
	public void saveWithAttachments(MultipartResponse request) throws RestServiceException;
	
	public void update(Object model) throws RestServiceException;
	
	public void delete(Object model) throws RestServiceException;
	
	public Class<?> modelType();
}
