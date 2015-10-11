/**
 * 
 */
package com.scaffy.service.bean;

/**
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 */
public class BeanTraversalException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BeanTraversalException(Throwable e) {
		super("Error while traversing bean graph", e);
	}
}
