package com.scaffy.product;

public class NoBeansFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NoBeansFoundException(String packages) {
		
		super("Could not find any beans under: " + packages);
	}
}
