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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scaffy.query.exception.InvalidCriteriaException;
import com.scaffy.query.exception.InvalidCriteriaSyntaxException;
import com.scaffy.query.key.RestSearchKey;
import com.scaffy.service.NoDataFoundException;
import com.scaffy.service.RestService;

@RestController
@RequestMapping("/app/api/")
public class MasterRestController {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private GsonHttpMessageConverter httpMessageConverter;

	@Autowired
	private Validator validator;
	
	private HashMap<String, RestService> restServices;
	
	@PostConstruct
	public void init() {
		
		Map<String, RestService> definedServices = applicationContext.getBeansOfType(RestService.class);
		
		restServices = new HashMap<String, RestService>();
		
		for(String ser : definedServices.keySet().toArray(new String[]{})){
			restServices.put(
					ser.substring(ser.lastIndexOf("_") + 1).toLowerCase(),
					definedServices.get(ser)
					);
		}
			
	}
	
	public void initBinder(WebDataBinder binder) {
	}
	
	private <T>T bindAndValidate(String modelName, String jsonBody, Class<T> modelClass)
			throws BindException {
		
		T model = httpMessageConverter.getGson().fromJson(jsonBody, modelClass);
		
		BindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(), modelName); 
		
		validator.validate(model, bindingResult);
		
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
		
		return model;
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<SuccessResponse> query(
			@RequestHeader("Search-Key") String searchKey
			) throws InvalidCriteriaException, InvalidCriteriaSyntaxException, NoDataFoundException, BindException {
		
		RestSearchKey restSearchKey = bindAndValidate("Search-Key", searchKey, RestSearchKey.class);
		
		restSearchKey.parseAllCriterias();
		
		RestService restService = restServices.get(restSearchKey.getResourceName());
		
		List<?> result = restService.query(restSearchKey);
		
		return new ResponseEntity<SuccessResponse>(new SuccessResponse(result), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{modelName}", method = RequestMethod.POST, 
			consumes = "application/json;charset=utf-8", produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<SuccessResponse> post(
			@RequestBody String body,
			@PathVariable("modelName") String modelName
			) throws BindException {
		
		RestService restService = restServices.get(modelName);
		
		Object model = bindAndValidate(modelName, body, restService.getModelClass());
		
		restService.save(model);
		
		ResponseEntity<SuccessResponse> responseEntity = 
				new ResponseEntity<SuccessResponse>(new SuccessResponse(model), HttpStatus.CREATED);
		
		return responseEntity;
	}

	@RequestMapping(value = "/{modelName}", method = RequestMethod.PUT,
			consumes = "application/json;charset=utf-8", produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<SuccessResponse> put(
			@RequestBody String body,
			@PathVariable("modelName") String modelName
			) throws BindException {
		
		RestService restService = restServices.get(modelName);
		
		Object model = bindAndValidate(modelName, body, restService.getModelClass());
		
		restService.update(model);
		
		ResponseEntity<SuccessResponse> responseEntity = 
				new ResponseEntity<SuccessResponse>(new SuccessResponse(model), HttpStatus.OK);
		
		return responseEntity;
	}
	
	@RequestMapping(value = "/{modelName}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<SuccessResponse> delete(
			@RequestBody String body,
			@PathVariable("modelName") String modelName
			) throws BindException {
		
		RestService restService = restServices.get(modelName);
		
		Object model = bindAndValidate(modelName, body, restService.getModelClass());
		
		restService.delete(model);
		
		ResponseEntity<SuccessResponse> responseEntity = 
				new ResponseEntity<SuccessResponse>(new SuccessResponse(model), HttpStatus.OK);
		
		return responseEntity;
	}

}
