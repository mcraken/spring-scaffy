package com.scaffy.controller;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.exception.InvalidCriteriaSyntaxException;
import com.scaffy.service.NoDataFoundException;
import com.scaffy.service.QueryService;

public class QueryController extends AcquisitionController {
	
	private Map<String, QueryService> queryServices;
	
	@PostConstruct
	public void init() {

		queryServices = buildServicesMap(QueryService.class);
		
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<SuccessResponse> query(
			@RequestHeader("Search-Key") String searchKey
			) throws InvalidCriteriaException, InvalidCriteriaSyntaxException, NoDataFoundException, BindException {
		
		return executeAcquireRequest(searchKey, queryServices);
	}
	
}
