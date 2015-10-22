package com.scaffy.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

import com.scaffy.controller.MultipartRequest;
import com.scaffy.dao.RESTDao;
import com.scaffy.service.bean.BeanTraversalException;

public class BasicRestService implements RestService {

	private Class<?> modelClass;

	@Autowired
	private GsonHttpMessageConverter httpMessageConverter;

	@Autowired
	private Validator validator;

	@Autowired
	private RESTDao restDao;
	
	@Autowired(required = false)
	private AttachmentService attachmentService;

	public void setModelClass(String modelName) throws ClassNotFoundException {
		this.modelClass = Class.forName(modelName);
	}

	public Object bindAndValidate(String jsonBody)
			throws BindException {

		Object model = httpMessageConverter.getGson().fromJson(jsonBody, modelClass);

		BindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(), modelClass.getSimpleName()); 

		validator.validate(model, bindingResult);

		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);

		return model;
	}

	public void save(Object model) throws BeanTraversalException {

		restDao.create(model);
		
	}
	
	public void saveWithAttachments(MultipartRequest request) throws BeanTraversalException {
		
		restDao.create(request.getModel());
		
		attachmentService.createAttachments(request);
		
	}

	public void update(Object model) throws BeanTraversalException {

		restDao.update(model);
		
	}

	public void delete(Object model) throws BeanTraversalException {

		restDao.delete(model);

	}

}
