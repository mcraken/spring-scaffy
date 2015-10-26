package com.scaffy.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.scaffy.service.ModelUnmarshaller;
import com.scaffy.service.RestService;
import com.scaffy.service.ServiceBroker;
import com.scaffy.service.ServiceNotFoundException;
import com.scaffy.service.bean.BeanTraversalException;

public class MasterRestController {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private ModelUnmarshaller modelUnmarshaller;
	
	@Autowired
	private ServiceBroker serviceBroker;
	
	@RequestMapping(value = "/{modelName}", method = RequestMethod.POST, 
			consumes = "application/json;charset=utf-8", produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<SuccessResponse> post(
			@RequestBody String body,
			@PathVariable("modelName") String modelName
			) throws BindException, BeanTraversalException, ServiceNotFoundException {

		RestService restService = serviceBroker.findService(modelName, RestService.class);
		
		Object model = modelUnmarshaller.bind(body, restService.modelType());
		
		modelUnmarshaller.validate(modelName, model);
		
		restService.save(model);

		ResponseEntity<SuccessResponse> responseEntity = 
				new ResponseEntity<SuccessResponse>(new SuccessResponse(model), HttpStatus.CREATED);

		return responseEntity;
	}
	
	@RequestMapping(value = "/multi/{modelName}", method = RequestMethod.POST, 
			consumes = "application/json;charset=utf-8", produces = "application/json;charset=utf-8")
	@ResponseBody
	public ResponseEntity<SuccessResponse> multipost(
			@ModelAttribute("model") String body,
			@PathVariable("modelName") String modelName,
			MultipartHttpServletRequest request
			) throws BindException, BeanTraversalException, IOException, ServiceNotFoundException {

		RestService restService = serviceBroker.findService(modelName, RestService.class);
		
		Object model = modelUnmarshaller.bind(body, restService.modelType());
		
		modelUnmarshaller.validate(modelName, model);
		
		MultipartRequest multipartRequest = new MultipartRequest(model, request);
		
		restService.saveWithAttachments(multipartRequest);

		ResponseEntity<SuccessResponse> responseEntity = 
				new ResponseEntity<SuccessResponse>(new SuccessResponse(model), HttpStatus.CREATED);

		return responseEntity;
	}

	@RequestMapping(value = "/{modelName}", method = RequestMethod.PUT,
			consumes = "application/json;charset=utf-8", produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<SuccessResponse> put(
			@RequestBody String body,
			@PathVariable("modelName") String modelName
			) throws BindException, BeanTraversalException, ServiceNotFoundException {

		RestService restService = serviceBroker.findService(modelName, RestService.class);

		Object model = modelUnmarshaller.bind(body, restService.modelType());
		
		modelUnmarshaller.validate(modelName, model);
		
		restService.update(model);

		ResponseEntity<SuccessResponse> responseEntity = 
				new ResponseEntity<SuccessResponse>(new SuccessResponse(model), HttpStatus.OK);

		return responseEntity;
	}

	@RequestMapping(value = "/{modelName}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
	public @ResponseBody ResponseEntity<SuccessResponse> delete(
			@RequestBody String body,
			@PathVariable("modelName") String modelName
			) throws BindException, BeanTraversalException, ServiceNotFoundException {

		RestService restService = serviceBroker.findService(modelName, RestService.class);

		Object model = modelUnmarshaller.bind(body, restService.modelType());
		
		modelUnmarshaller.validate(modelName, model);
		
		restService.delete(model);

		ResponseEntity<SuccessResponse> responseEntity = 
				new ResponseEntity<SuccessResponse>(new SuccessResponse(model), HttpStatus.OK);

		return responseEntity;
	}

}
