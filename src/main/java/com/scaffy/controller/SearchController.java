package com.scaffy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.exception.InvalidCriteriaSyntaxException;
import com.scaffy.acquisition.key.RestSearchKey;
import com.scaffy.service.ModelUnmarshaller;
import com.scaffy.service.NoDataFoundException;
import com.scaffy.service.SearchService;
import com.scaffy.service.ServiceBroker;
import com.scaffy.service.ServiceNotFoundException;

public class SearchController  {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ModelUnmarshaller modelUnmarshaller;

	@Autowired
	private ServiceBroker serviceBroker;

	private RestSearchKey processKey(String searchKey) throws BindException,
	InvalidCriteriaSyntaxException {

		RestSearchKey restSearchKey = modelUnmarshaller.bind(searchKey, RestSearchKey.class);

		modelUnmarshaller.validate("Search-Key", restSearchKey);

		restSearchKey.parseAllCriterias();

		return restSearchKey;
	}

	private List<RestSearchKey> processKeys(String searchKey) throws BindException,
	InvalidCriteriaSyntaxException {

		List<RestSearchKey> restSearchKeys = modelUnmarshaller.binds(searchKey, RestSearchKey.class);

		for(RestSearchKey restSearchKey : restSearchKeys){

			modelUnmarshaller.validate("Search-Key", restSearchKey);

			restSearchKey.parseAllCriterias();
		}

		return restSearchKeys;
	}

	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<SuccessResponse> query(
			@RequestHeader("Search-Key") String searchKey
			) throws InvalidCriteriaException, InvalidCriteriaSyntaxException, NoDataFoundException, BindException, ServiceNotFoundException {

		RestSearchKey restSearchKey = processKey(searchKey);
		
		SearchService searchService = serviceBroker.findService(restSearchKey.getResourceName(), SearchService.class);

		List<?> result = searchService.db(restSearchKey); 
		
		return new ResponseEntity<SuccessResponse>(new SuccessResponse(result), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/queries", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<SuccessResponse> queries(
			@RequestHeader("Search-Key") String searchKey
			) throws InvalidCriteriaException, InvalidCriteriaSyntaxException, NoDataFoundException, BindException, ServiceNotFoundException {

		List<RestSearchKey> restSearchKeys = processKeys(searchKey);
		
		Map<String, List<?>> results = new HashMap<String, List<?>>();
		
		SearchService searchService = null;
		
		for(RestSearchKey restSearchKey : restSearchKeys){
			
			searchService = serviceBroker.findService(restSearchKey.getResourceName(), SearchService.class);

			results.put(restSearchKey.getResourceName(), searchService.db(restSearchKey));
		}
		
		return new ResponseEntity<SuccessResponse>(new SuccessResponse(results), HttpStatus.OK);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<SuccessResponse> search(
			@RequestHeader("Search-Key") String searchKey
			) throws InvalidCriteriaException, InvalidCriteriaSyntaxException, NoDataFoundException, BindException, ServiceNotFoundException {

		RestSearchKey restSearchKey = processKey(searchKey);
		
		SearchService searchService = serviceBroker.findService(restSearchKey.getResourceName(), SearchService.class);

		List<?> result = searchService.fullText(restSearchKey);
		
		return new ResponseEntity<SuccessResponse>(new SuccessResponse(result), HttpStatus.OK);
	}

}
