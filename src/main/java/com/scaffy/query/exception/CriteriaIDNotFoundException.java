package com.scaffy.query.exception;

public class CriteriaIDNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public CriteriaIDNotFoundException(String id) {
		super("Crtiteria identified with: " + id + " not found.");
	}
}
