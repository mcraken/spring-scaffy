package com.scaffy.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scaffy.service.NoDataFoundException;
import com.scaffy.service.RestService;

public class MasterRestController {

	@Autowired
	private ApplicationContext applicationContext;

	private HashMap<String, RestService> restServices;

	private RestService findRestService(String resourceName) throws NoDataFoundException {

		RestService restService = restServices.get(resourceName);

		if(restService == null)
			throw new NoDataFoundException("Could not find resource " + resourceName);

		return restService;
	}

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

	@RequestMapping(value = "/{modelName}", method = RequestMethod.POST, 
			consumes = "application/json;charset=utf-8", produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<SuccessResponse> post(
			@RequestBody String body,
			@PathVariable("modelName") String modelName
			) throws BindException, NoDataFoundException {

		RestService restService = findRestService(modelName);

		Object model= restService.save(body);

		ResponseEntity<SuccessResponse> responseEntity = 
				new ResponseEntity<SuccessResponse>(new SuccessResponse(model), HttpStatus.CREATED);

		return responseEntity;
	}

	@RequestMapping(value = "/{modelName}", method = RequestMethod.PUT,
			consumes = "application/json;charset=utf-8", produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<SuccessResponse> put(
			@RequestBody String body,
			@PathVariable("modelName") String modelName
			) throws BindException, NoDataFoundException {

		RestService restService = findRestService(modelName);

		Object model = restService.update(body);

		ResponseEntity<SuccessResponse> responseEntity = 
				new ResponseEntity<SuccessResponse>(new SuccessResponse(model), HttpStatus.OK);

		return responseEntity;
	}

	@RequestMapping(value = "/{modelName}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<SuccessResponse> delete(
			@RequestBody String body,
			@PathVariable("modelName") String modelName
			) throws BindException, NoDataFoundException {

		RestService restService = findRestService(modelName);

		Object model = restService.delete(body);

		ResponseEntity<SuccessResponse> responseEntity = 
				new ResponseEntity<SuccessResponse>(new SuccessResponse(model), HttpStatus.OK);

		return responseEntity;
	}

}
