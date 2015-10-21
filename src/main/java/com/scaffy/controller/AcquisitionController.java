package com.scaffy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.exception.InvalidCriteriaSyntaxException;
import com.scaffy.acquisition.key.RestSearchKey;
import com.scaffy.service.AcquisitionService;
import com.scaffy.service.NoDataFoundException;

public abstract class AcquisitionController {
	
	@Autowired
	private GsonHttpMessageConverter httpMessageConverter;

	@Autowired
	private Validator validator;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	protected <T extends AcquisitionService> AcquisitionService findAcquisitionService(String resourceName, Map<String, T> services) throws NoDataFoundException {
		
		T service = services.get(resourceName);
		
		if(service == null)
			throw new NoDataFoundException("Could not find resource " + resourceName);
		
		return service;
	}
	
	protected <T> Map<String, T> buildServicesMap(Class<T> acquisitionType) {
		
		Map<String, T> definedServices = applicationContext.getBeansOfType(acquisitionType);

		Map<String, T> services = new HashMap<String, T>();

		for(String ser : definedServices.keySet().toArray(new String[]{})){
			services.put(
					ser.substring(ser.lastIndexOf("_") + 1).toLowerCase(),
					definedServices.get(ser)
					);
		}
		
		return services;
	}
	
	protected RestSearchKey bindAndValidate(String jsonBody)
			throws BindException {
		
		RestSearchKey key = httpMessageConverter.getGson().fromJson(jsonBody, RestSearchKey.class);
		
		BindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(), "Search-Key"); 
		
		validator.validate(key, bindingResult);
		
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		
		return key;
	}
	
	protected <T extends AcquisitionService>ResponseEntity<SuccessResponse> executeAcquireRequest(
			String searchKey, Map<String, T> services) throws BindException,
			InvalidCriteriaSyntaxException, NoDataFoundException,
			InvalidCriteriaException {
		
		RestSearchKey restSearchKey = bindAndValidate(searchKey);
		
		restSearchKey.parseAllCriterias();
		
		String resourceName = restSearchKey.getResourceName();
		
		AcquisitionService queyService = findAcquisitionService(resourceName, services);
		
		List<?> result = queyService.acquire(restSearchKey);
		
		return new ResponseEntity<SuccessResponse>(new SuccessResponse(result), HttpStatus.OK);
	}

}
