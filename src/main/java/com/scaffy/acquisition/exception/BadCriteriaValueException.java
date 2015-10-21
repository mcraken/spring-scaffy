/**
 * 
 */
package com.scaffy.acquisition.exception;

/**
 * @author Raken
 *
 */
public class BadCriteriaValueException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BadCriteriaValueException(String value){
		super("Bad Criteria Value: " + value);
	}

}
