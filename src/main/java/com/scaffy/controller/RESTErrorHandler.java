package com.scaffy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.scaffy.service.NoDataFoundException;

/**
 * <p>RESTErrorHandler class.</p>
 *
 * @author 	Sherief Shawky
 * @Email 	sheshawky@informatique-eg.com
 *
 * RESTErrorHandler is an advice controller. It handles argument validation errors,
 * binding errors and finally runtime exceptions
 * @version $Id: $Id
 */
@ControllerAdvice
public class RESTErrorHandler {

	private static Logger logger = LoggerFactory.getLogger(RESTErrorHandler.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private MessageSource messageSource;

	/**
	 * <p>processValidationError.</p>
	 *
	 * @return Error report map.
	 * @param ex a {@link org.springframework.web.bind.MethodArgumentNotValidException} object.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> processValidationError(MethodArgumentNotValidException ex) {

		BindingResult result = ex.getBindingResult();

		ErrorResponse errorResponse = applicationContext.getBean(ErrorResponse.class);
		
		errorResponse.build(result);
		
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * <p>processBindingError.</p>
	 *
	 * @return Error report map.
	 * @param ex a {@link org.springframework.validation.BindException} object.
	 */
	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> processBindingError(BindException ex) {

		BindingResult result = ex.getBindingResult();

		ErrorResponse errorResponse = applicationContext.getBean(ErrorResponse.class);
		
		errorResponse.build(result);
		
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * <p>NoDataError.</p>
	 *
	 * @return a message that determines which resource is not found.
	 * @param ex a {@link java.lang.Exception} object.
	 */
	@ExceptionHandler(NoDataFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<SuccessResponse> NoDataError(Exception ex) {

		return new ResponseEntity<SuccessResponse>(new SuccessResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * <p>processPersistenceError.</p>
	 *
	 * @return a messages that describes the runtime error. Most runtime error should
	 * occur due to database constraint violations.
	 * @param ex a {@link java.lang.RuntimeException} object.
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> processPersistenceError(RuntimeException ex) {

		logger.debug("Runtime Error Occured", ex);
		
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
	}
	

}
