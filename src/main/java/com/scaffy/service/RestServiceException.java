/**
 * 
 */
package com.scaffy.service;

/**
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 */
public class RestServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RestServiceException(Throwable e) {
		
		super("Error while executing rest service", e);
	}
}
