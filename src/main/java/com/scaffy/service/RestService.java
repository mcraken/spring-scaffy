package com.scaffy.service;

import org.springframework.validation.BindException;


public interface RestService {
	
	public Object save(String body) throws BindException;
	
	public Object update(String body) throws BindException;
	
	public Object delete(String body) throws BindException;
}
