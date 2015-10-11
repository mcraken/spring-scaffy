package com.scaffy.service;


public interface RestService {
	
	public <T> void save(T model);
	
	public <T> void update(T model);
	
	public <T> void delete(T model);
	
	public Class<?> getModelClass();
}
