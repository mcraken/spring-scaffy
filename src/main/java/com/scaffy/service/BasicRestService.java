package com.scaffy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import com.scaffy.controller.MultipartRequest;
import com.scaffy.dao.RESTDao;
import com.scaffy.dao.bean.BeanTraversalException;

public class BasicRestService implements RestService {

	private Class<?> modelClass;

	@Autowired
	private GsonHttpMessageConverter httpMessageConverter;

	@Autowired
	private RESTDao restDao;
	
	@Autowired(required = false)
	private AttachmentService attachmentService;

	public void setModelClass(String modelName) throws ClassNotFoundException {
		
		this.modelClass = Class.forName(modelName);
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

	public Class<?> modelType() {
		
		return modelClass;
	}

}
