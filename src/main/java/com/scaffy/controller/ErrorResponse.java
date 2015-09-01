package com.scaffy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.scaffy.config.web.Exclude;

@Component()
@Scope("prototype")
public class ErrorResponse {

	@Autowired
	@Exclude
	private MessageSource messageSource;
	
	private Integer errorCode;
	
	private Map<String, String> errors;
	
	private String errorMessage;
	
	public ErrorResponse() {
	}

	public ErrorResponse(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ErrorResponse(Map<String, String> errors) {
		this.errors = errors;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public Map<String, String> getErrors() {
		return errors;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void build(BindingResult result) {
		
		List<FieldError> fieldErrors = result.getFieldErrors();

		errors = new HashMap<String, String>();

		String localizedErrorMessage;

		Locale currentLocale =  LocaleContextHolder.getLocale();

		for(FieldError error : fieldErrors) {

			localizedErrorMessage = messageSource.getMessage(error, currentLocale);

			errors.put(error.getField(), localizedErrorMessage);
		}
	}
}
