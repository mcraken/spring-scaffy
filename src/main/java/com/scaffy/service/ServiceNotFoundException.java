package com.scaffy.service;

public class ServiceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ServiceNotFoundException(String name) {
		super("Could not find for " + name);
	}
}
