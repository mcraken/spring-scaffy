/**
 * 
 */
package com.scaffy.acquisition.exception;

/**
 * @author 	Sherief Shawky
 * @email 	mcrakens@gmail.com
 * @date 	Sep 9, 2014
 */
public class ServerFunctionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	public ServerFunctionException(Throwable e) {
		super("Function execetion failed", e);
	}
}
