package com.scaffy.controller;

/**
 * <p>ResponseObject class.</p>
 *
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 * @param <T>. A reponse object that wraps any type.
 * @version $Id: $Id
 */
public class SuccessResponse {

	private Object result;

	/**
	 * <p>Constructor for ResponseObject.</p>
	 */
	public SuccessResponse() {
		
	}
	
	/**
	 * <p>Constructor for ResponseObject.</p>
	 *
	 * @param result a T object.
	 */
	public SuccessResponse(Object result) {
		this.result = result;
	}
	
	/**
	 * <p>Getter for the field <code>result</code>.</p>
	 *
	 * @return a T object.
	 */
	public Object getResult() {
		return result;
	}

}
