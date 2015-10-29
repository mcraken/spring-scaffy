/**
 * 
 */
package com.scaffy.dao;

/**
 * @author 	Sherief Shawky
 * @Email 	sheshawky@informatique-eg.com
 */
public class DaoOperationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DaoOperationException(Throwable e) {
		super("Error while executing dao operation", e);
	}
}
