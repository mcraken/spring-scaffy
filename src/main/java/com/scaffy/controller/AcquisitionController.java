package com.scaffy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.exception.InvalidCriteriaSyntaxException;
import com.scaffy.acquisition.key.RestSearchKey;
import com.scaffy.service.NoDataFoundException;
import com.scaffy.service.QueryService;

public class AcquisitionController {
	
	@Autowired
	private GsonHttpMessageConverter httpMessageConverter;

	@Autowired
	private Validator validator;
	
	private HashMap<String, QueryService> queryServices;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	private QueryService findQueryService(String resourceName) throws NoDataFoundException {
		
		QueryService queryService = queryServices.get(resourceName);
		
		if(queryService == null)
			throw new NoDataFoundException("Could not find resource " + resourceName);
		
		return queryService;
	}
	
	private RestSearchKey bindAndValidate(String jsonBody)
			throws BindException {
		
		RestSearchKey key = httpMessageConverter.getGson().fromJson(jsonBody, RestSearchKey.class);
		
		BindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(), "Search-Key"); 
		
		validator.validate(key, bindingResult);
		
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		
		return key;
	}
	
	@PostConstruct
	public void init() {

		Map<String, QueryService> definedServices = applicationContext.getBeansOfType(QueryService.class);

		queryServices = new HashMap<String, QueryService>();

		for(String ser : definedServices.keySet().toArray(new String[]{})){
			queryServices.put(
					ser.substring(ser.lastIndexOf("_") + 1).toLowerCase(),
					definedServices.get(ser)
					);
		}

	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<SuccessResponse> query(
			@RequestHeader("Search-Key") String searchKey
			) throws InvalidCriteriaException, InvalidCriteriaSyntaxException, NoDataFoundException, BindException {
		
		RestSearchKey restSearchKey = bindAndValidate(searchKey);
		
		restSearchKey.parseAllCriterias();
		
		String resourceName = restSearchKey.getResourceName();
		
		QueryService queyService = findQueryService(resourceName);
		
		List<?> result = queyService.query(restSearchKey);
		
		return new ResponseEntity<SuccessResponse>(new SuccessResponse(result), HttpStatus.OK);
	}
}
